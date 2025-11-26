<script lang="ts">
  import { onMount } from 'svelte';
  import { ContaFinanceiraAPI } from "$lib/api/contaFinanceira";
  import { ContaInvestimentoAPI, type ContaInvestimento } from "$lib/api/contaInvestimento";
  import { get } from 'svelte/store';
  import { StoreUser } from "$lib/stores/userStore";
  import { protectRoute } from '$lib/utils/auth';
  import { TagAPI, type Tag } from '$lib/api/tag';

  export const load = async () => {
    protectRoute();
    return {};
  };

  // -------------------- ESTADOS --------------------
  let accounts: any[] = [];
  let contaPrincipal: any = null;
  let saldoAtual: number = 0;

  let reservation: number | null = null;
  let leisure: number | null = null;
  let creditLimit: number | null = null;

  let error = "";

  // modais
  let showsaldoAtualModal = false;
  let showOrganizationModal = false;
  let showAlertsModal = false;
  let showCreditModal = false;
  let showDeclareActionModal = false;
  let showSellActionModal = false;

  // editar saldo
  let newsaldoAtual: number | null = null;

  // alerts
  let alerts: string[] = [];
  let newAlertMsg = "";

  // investimentos
  let investments: ContaInvestimento[] = [];
  let errorInvestments = "";
  let declareName = "";
  let declareValue: number | null = null;
  let declareQuantity: number | null = null;

  // venda
  let sellSelectedId: number | null = null;
  let sellQuantity: number | null = null;
  let sellError = "";

  // tags
  let tags: Tag[] = [];
  let selectedTag: Tag | null = null;

  // -------------------- FORMATADOR GLOBAL --------------------
  function formatCurrency(value: any) {
    const v = Number(value ?? 0);
    return v.toLocaleString("pt-BR", {
      style: "currency",
      currency: "BRL"
    });
  }

  // -------------------- ON MOUNT --------------------
  onMount(async () => {
    await loadFinanceData();
    await loadInvestments();
    await loadTags();
  });

  // -------------------- TAGS --------------------
  async function loadTags() {
    try {
      tags = await TagAPI.getAll();
    } catch (e) {
      console.error("Erro ao carregar tags:", e);
    }
  }

  // -------------------- FINANCEIRO --------------------
  async function loadFinanceData() {
    try {
      const currentUser = get(StoreUser);
      if (!currentUser?.id) {
        error = "Usuário não encontrado.";
        return;
      }

      const userId = Number(currentUser.id);
      const result = await ContaFinanceiraAPI.getByUsuario(userId);

      if (Array.isArray(result)) {
        accounts = result;
        contaPrincipal = result[0] ?? null;
      } else if (result && typeof result === "object") {
        accounts = Object.keys(result).length > 0 ? [result] : [];
        contaPrincipal = accounts[0] ?? null;
      } else {
        accounts = [];
        contaPrincipal = null;
      }

      saldoAtual = Number(contaPrincipal?.saldoAtual ?? 0);

    } catch (e) {
      console.error(e);
      error = "Erro ao carregar contas.";
    }
  }

  // -------------------- INVESTIMENTOS --------------------
  async function loadInvestments() {
    try {
      const currentUser = get(StoreUser);
      if (!currentUser?.id) return;

      const res = await ContaInvestimentoAPI.getByUsuarioId(Number(currentUser.id));
      investments = Array.isArray(res) ? res : [];

    } catch (err) {
      console.error("loadInvestments:", err);
      errorInvestments = "Erro ao carregar investimentos.";
      investments = [];
    }
  }

  // -------------------- EDITAR SALDO --------------------
  async function updateSaldo() {
    try {
      if (!contaPrincipal) {
        error = "Nenhuma conta selecionada.";
        return;
      }
      if (newsaldoAtual === null || isNaN(Number(newsaldoAtual))) {
        error = "Informe um valor válido.";
        return;
      }

      await ContaFinanceiraAPI.updateSaldo(contaPrincipal.id, Number(newsaldoAtual));
      await loadFinanceData();
      showsaldoAtualModal = false;
      newsaldoAtual = null;

    } catch (err) {
      console.error("updateSaldo:", err);
      error = "Erro ao atualizar saldo.";
    }
  }

  // -------------------- ALERTAS --------------------
  function openAddAlert() {
    newAlertMsg = "";
    showAlertsModal = true;
  }

  function addAlert() {
    if (!newAlertMsg.trim()) return;
    alerts.unshift(newAlertMsg.trim());
    newAlertMsg = "";
    showAlertsModal = false;
  }

  function removeAlert(i: number) {
    alerts.splice(i, 1);
  }

  // -------------------- DECLARAR AÇÃO --------------------
  async function declareAction() {
    try {
      if (!declareName.trim() || declareValue == null || declareQuantity == null || !selectedTag) {
        errorInvestments = "Preencha todos os campos.";
        return;
      }

      const currentUser = get(StoreUser);
      if (!currentUser?.id) return;

      const payload: ContaInvestimento = {
        actionName: declareName.trim(),
        value: Number(declareValue),
        quantity: Number(declareQuantity),
        tag: selectedTag,   // <<<<<< TAG SELECIONADA
        usuarioId: Number(currentUser.id)
      };

      await ContaInvestimentoAPI.create(payload);

      await loadInvestments();
      showDeclareActionModal = false;

      declareName = "";
      declareValue = null;
      declareQuantity = null;
      selectedTag = null;

    } catch (e) {
      console.error(e);
      errorInvestments = "Erro ao declarar ação.";
    }
  }

  // -------------------- VENDER AÇÃO --------------------
  function openSellModal(id?: number) {
    sellSelectedId = id ?? null;
    sellQuantity = null;
    sellError = "";
    showSellActionModal = true;
  }

  async function sellAction() {
    try {
      if (!sellSelectedId) {
        sellError = "Selecione uma ação.";
        return;
      }
      if (!sellQuantity || sellQuantity <= 0) {
        sellError = "Quantidade inválida.";
        return;
      }

      const inv = investments.find(i => Number(i.id) === Number(sellSelectedId));
      if (!inv) {
        sellError = "Ação não encontrada.";
        return;
      }

      const qtdAtual = Number(inv.quantity);
      const qtdVenda = Number(sellQuantity);

      if (qtdVenda > qtdAtual) {
        sellError = "Quantidade maior que a disponível.";
        return;
      }

      const total = Number(inv.value) * qtdVenda;

      const novoSaldo = Number(contaPrincipal.saldoAtual) + total;
      await ContaFinanceiraAPI.updateSaldo(contaPrincipal.id, novoSaldo);

      if (qtdVenda === qtdAtual) {
        await ContaInvestimentoAPI.deleteById(inv.id!);
      } else {
        await ContaInvestimentoAPI.update(inv.id!, {
          ...inv,
          quantity: qtdAtual - qtdVenda
        });
      }

      await loadFinanceData();
      await loadInvestments();

      showSellActionModal = false;

    } catch (e) {
      console.error(e);
      sellError = "Erro ao vender.";
    }
  }
</script>


<!-- ===================== HTML / UI ===================== -->
<div class="flex flex-col items-center p-6">
  <div class="w-full max-w-7xl bg-slate-800/60 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 p-6 space-y-5 text-white">

    <!-- HEADER Renda + gráfico -->
    <section class="flex flex-col lg:flex-row gap-6">
      <div class="flex flex-col items-center">
        <h2 class="text-white font-semibold text-lg mb-2">Renda</h2>
        <div class="w-44 h-44 bg-slate-900/40 rounded-full flex items-center justify-center text-gray-300">
          (Gráfico)
        </div>
      </div>

      <!-- CARDS -->
      <div class="flex-1 grid grid-cols-1 md:grid-cols-2 gap-4">

        <!-- SALDO -->
        <button class="bg-yellow-400/80 text-black p-4 rounded-2xl shadow-md flex flex-col items-start"
                on:click={() => showsaldoAtualModal = true}>
          <div class="text-sm font-semibold">Saldo em conta</div>
          <div class="mt-2 text-2xl font-bold">
            {#if typeof saldoAtual === 'number'}
              {formatCurrency(saldoAtual)}
            {:else}
              {formatCurrency(0)}
            {/if}
          </div>
          <div class="mt-1 text-xs text-gray-800">Conta principal: {contaPrincipal?.nome ?? "—"}</div>
        </button>

        <!-- ORGANIZAÇÃO -->
        <button class="bg-cyan-400/80 text-black p-4 rounded-2xl shadow-md flex flex-col items-start"
                on:click={() => showOrganizationModal = true}>
          <div class="text-sm font-semibold">Organização mensal</div>
          <div class="mt-2 text-sm">
            {reservation != null && leisure != null
              ? `Reserva: ${formatCurrency(reservation)} · Lazer: ${formatCurrency(leisure)}`
              : 'Configurar reserva e lazer'}
          </div>
        </button>

        <!-- ALERTAS -->
        <div class="flex flex-col">
          <button class="bg-purple-500/80 text-white p-4 rounded-2xl shadow-md flex-1 text-left"
                  on:click={() => openAddAlert()}>
            <div class="text-sm font-semibold">Alertas</div>
            <div class="mt-2 text-sm text-gray-200">{alerts.length} registro(s)</div>
          </button>
          <!-- lista rápida de alertas -->
          <div class="mt-2 bg-slate-900/40 rounded p-2 max-h-36 overflow-auto">
            {#if alerts.length === 0}
              <div class="text-xs text-gray-400">Nenhum alerta</div>
            {:else}
              {#each alerts as a, i}
                <div class="flex justify-between items-center text-sm py-1">
                  <div>{a}</div>
                  <button class="text-xs text-red-300 ml-2" on:click={() => removeAlert(i)}>Remover</button>
                </div>
              {/each}
            {/if}
          </div>
        </div>

        <!-- CARTÃO DE CRÉDITO -->
        <button class="bg-cyan-500/80 text-black p-4 rounded-2xl shadow-md text-left"
                on:click={() => showCreditModal = true}>
          <div class="text-sm font-semibold">Cartão de crédito</div>
          <div class="mt-2 text-sm">{creditLimit != null ? `Limite: ${formatCurrency(creditLimit)}` : 'Sem cartão'}</div>
        </button>

      </div>
    </section>

    <!-- INVESTIMENTOS: DECLARAR / VENDER -->
    <section class="mt-8">
      <h3 class="text-white font-semibold mb-5">Investimentos</h3>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">

        <!-- card: declarar ação (cria) -->
        <div class="bg-slate-900/40 p-4 rounded-2xl shadow-md">
          <div class="font-semibold mb-2">Declarar nova ação</div>
          <div class="text-sm text-gray-300 mb-3">Registre uma ação que você possui (cria no banco).</div>
          <div class="flex flex-col gap-2">
            <input placeholder="Nome da ação" bind:value={declareName} class="p-2 rounded bg-white/5" />
            <input type="number" placeholder="Valor unitário" bind:value={declareValue} class="p-2 rounded bg-white/5" />
            <input type="number" placeholder="Quantidade" bind:value={declareQuantity} class="p-2 rounded bg-white/5" />
            {#if errorInvestments}
              <div class="text-red-400 text-sm">{errorInvestments}</div>
            {/if}
            <div class="flex gap-2 mt-2">
              <button class="bg-green-600 px-3 py-1 rounded" on:click={declareAction}>Registrar</button>
              <button class="bg-gray-600 px-3 py-1 rounded" on:click={() => { declareName=''; declareValue=null; declareQuantity=null; }}>Limpar</button>
            </div>
          </div>
        </div>

        <!-- card: vender ação (abre modal com seleção) -->
        <div class="bg-slate-900/40 p-4 rounded-2xl shadow-md">
          <div class="font-semibold mb-2">Vender ação</div>
          <div class="text-sm text-gray-300 mb-3">Venda total ou parcial — valor somado automaticamente ao saldo.</div>

          {#if investments.length === 0}
            <div class="text-sm text-gray-400">Nenhuma ação cadastrada</div>
            <div class="mt-3">
              <button class="bg-cyan-500 px-3 py-1 rounded" on:click={() => showDeclareActionModal = true}>Cadastrar Ação</button>
            </div>
          {:else}
            <div class="space-y-2">
              {#each investments as inv}
                <div class="flex items-center justify-between bg-white/5 p-2 rounded">
                  <div>
                    <div class="font-medium">{inv.actionName}</div>
                    <div class="text-xs text-gray-300">{inv.quantity} × {formatCurrency(inv.value)}</div>
                  </div>
                  <div class="flex gap-2">
                    <button class="bg-yellow-500 px-2 py-1 rounded text-sm" on:click={() => openSellModal(inv.id)}>Vender</button>
                    <button class="bg-gray-600 px-2 py-1 rounded text-sm" on:click={() => {
                      // prefill declare modal (quick copy)
                      declareName = inv.actionName;
                      declareValue = inv.value;
                      declareQuantity = inv.quantity;
                      showDeclareActionModal = true;
                    }}>Editar</button>
                  </div>
                </div>
              {/each}
            </div>
          {/if}

        </div>

        <!-- resumo / ação rápida -->
        <div class="bg-slate-900/40 p-4 rounded-2xl shadow-md">
          <div class="font-semibold mb-2">Resumo</div>
          <div class="text-sm text-gray-300 mb-2">Saldo atual: <span class="font-bold">{formatCurrency(saldoAtual)}</span></div>
          <div class="text-sm text-gray-300">Ações cadastradas: <span class="font-bold">{investments.length}</span></div>
          <div class="mt-3">
            <button class="bg-blue-600 px-3 py-1 rounded" on:click={() => { loadFinanceData(); loadInvestments(); }}>Recarregar</button>
          </div>
        </div>

      </div>
    </section>

  </div>

  <!-- ================= MODAIS ================= -->

  <!-- editar saldo -->
  {#if showsaldoAtualModal}
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-96 text-black">
        <h3 class="text-lg font-semibold mb-4">Editar saldo da conta</h3>
        <div class="mb-2 text-sm">Conta: {contaPrincipal?.nome ?? '—'}</div>
        <input type="number" bind:value={newsaldoAtual} placeholder={String(saldoAtual)} class="w-full p-2 border rounded mb-4" />
        <div class="flex justify-end gap-2">
          <button class="bg-gray-300 px-3 py-1 rounded" on:click={() => { showsaldoAtualModal = false; newsaldoAtual = null; }}>Cancelar</button>
          <button class="bg-blue-600 text-white px-3 py-1 rounded" on:click={updateSaldo}>Salvar</button>
        </div>
      </div>
    </div>
  {/if}

  <!-- organization modal -->
{#if showOrganizationModal}
  <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
    <div class="bg-white rounded-xl p-6 w-96 text-black">

      <h3 class="text-lg font-semibold mb-4">Organização mensal</h3>

      <!-- TAGS -->
      <label class="text-sm font-semibold">Categoria</label>

      {#if tags.length === 0}
        <p class="text-gray-600 text-sm mb-3">Carregando categorias...</p>
      {:else}
        <select bind:value={selectedTag}
                class="w-full p-2 border rounded mb-4 bg-gray-50">
          <option disabled selected value={null}>Selecione uma categoria...</option>
          {#each tags as t}
            <option value={t}>{t}</option>
          {/each}
        </select>
      {/if}

      <!-- CAMPOS RESERVA E LAZER -->
      <label class="text-sm">Reserva</label>
      <input type="number" bind:value={reservation}
             class="w-full p-2 border rounded mb-2" />

      <label class="text-sm">Lazer</label>
      <input type="number" bind:value={leisure}
             class="w-full p-2 border rounded mb-4" />

      <!-- BOTÕES -->
      <div class="flex justify-end gap-2">
        <button class="bg-gray-300 px-3 py-1 rounded"
                on:click={() => showOrganizationModal = false}>
          Fechar
        </button>

        <button class="bg-blue-600 text-white px-3 py-1 rounded"
                on:click={() => showOrganizationModal = false}>
          Salvar
        </button>
      </div>

    </div>
  </div>
{/if}


  <!-- alerts modal -->
  {#if showAlertsModal}
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-96 text-black">
        <h3 class="text-lg font-semibold mb-4">Adicionar alerta</h3>
        <textarea bind:value={newAlertMsg} rows={4} class="w-full p-2 border rounded mb-4" placeholder="Mensagem do alerta..."></textarea>
        <div class="flex justify-end gap-2">
          <button class="bg-gray-300 px-3 py-1 rounded" on:click={() => { showAlertsModal=false; newAlertMsg=''; }}>Cancelar</button>
          <button class="bg-blue-600 text-white px-3 py-1 rounded" on:click={addAlert}>Adicionar</button>
        </div>
      </div>
    </div>
  {/if}

  <!-- declarar ação modal (reaproveitado) -->
  {#if showDeclareActionModal}
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-96 text-black">
        <h3 class="text-lg font-semibold mb-4">Declarar nova ação</h3>
        <input placeholder="Nome da ação" bind:value={declareName} class="w-full p-2 border rounded mb-2" />
        <input type="number" placeholder="Valor unitário" bind:value={declareValue} class="w-full p-2 border rounded mb-2" />
        <input type="number" placeholder="Quantidade" bind:value={declareQuantity} class="w-full p-2 border rounded mb-4" />
        {#if errorInvestments}
          <div class="text-red-500 mb-2">{errorInvestments}</div>
        {/if}
        <div class="flex justify-end gap-2">
          <button class="bg-gray-300 px-3 py-1 rounded" on:click={() => { showDeclareActionModal=false; declareName=''; declareValue=null; declareQuantity=null; }}>Cancelar</button>
          <button class="bg-green-600 text-white px-3 py-1 rounded" on:click={declareAction}>Registrar</button>
        </div>
      </div>
    </div>
  {/if}

  <!-- vender ação modal -->
  {#if showSellActionModal}
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl p-6 w-96 text-black">
        <h3 class="text-lg font-semibold mb-4">Registrar venda</h3>

        <label class="text-sm">Selecione ação</label>
        <select bind:value={sellSelectedId} class="w-full p-2 border rounded mb-2">
          <option value={null}>-- selecione --</option>
          {#each investments as inv}
            <option value={inv.id}>
              {inv.actionName} — {inv.quantity} unidades — {formatCurrency(inv.value)}
            </option>
          {/each}
        </select>

        <label class="text-sm">Quantidade a vender</label>
        <input type="number" bind:value={sellQuantity} min="1" class="w-full p-2 border rounded mb-2" />

        {#if sellError}
          <div class="text-red-500 mb-2">{sellError}</div>
        {/if}

        <div class="flex justify-end gap-2">
          <button class="bg-gray-300 px-3 py-1 rounded" on:click={() => { showSellActionModal=false; sellSelectedId=null; sellQuantity=null; sellError=''; }}>Cancelar</button>
          <button class="bg-blue-600 text-white px-3 py-1 rounded" on:click={sellAction}>Vender</button>
        </div>
      </div>
    </div>
  {/if}

</div>

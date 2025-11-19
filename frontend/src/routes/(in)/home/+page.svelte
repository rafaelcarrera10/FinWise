<script lang="ts">
  import { onMount } from 'svelte'
  import { AccountAPI } from "$lib/api/account"
  import { get } from 'svelte/store'
  import { StoreUser } from "$lib/stores/userStore"

  // -------------------- VARIÁVEIS --------------------
  let accounts: { id: number; balance: number }[] = []
  let balance: number | null = null
  let reservation: number | null = null
  let leisure: number | null = null
  let creditLimit: number | null = null
  let error = ""

  // -------------------- ESTADOS DE MODAIS --------------------
  let showBalanceModal = false
  let showOrganizationModal = false
  let showAlertsModal = false
  let showCreditModal = false

  // -------------------- VALORES DE EDIÇÃO --------------------
  let newBalance: number | null = null
  let newReservation: number | null = null
  let newLeisure: number | null = null
  let newCredit: number | null = null

  // -------------------- FUNÇÃO: FORMATAR MOEDA --------------------
  const formatCurrency = (value: number) =>
    value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })

  // -------------------- CARREGAR CONTAS E SALDO TOTAL --------------------
  onMount(async () => {
    try {
      const currentUser = get(StoreUser)
      if (!currentUser?.id) {
        error = "Usuário não encontrado. Faça login novamente."
        return
      }

      const userId = typeof currentUser.id === 'string' ? parseInt(currentUser.id, 10) : currentUser.id

      // Busca todas as contas do usuário
      const result = await AccountAPI.getByUserId(userId)

      if (result && Array.isArray(result)) {
        (accounts as any) = result
        // Soma de todos os saldos
        balance = result.reduce((acc, c) => acc + (c.balance ?? 0), 0)
      } else {
        balance = 0
      }
    } catch (err) {
      console.error(err)
      error = "Erro ao carregar dados financeiros."
    }
  })

  // -------------------- ATUALIZAR SALDO TOTAL --------------------
  async function updateBalance() {
    try {
      const currentUser = get(StoreUser)
      if (!currentUser?.id || newBalance === null || isNaN(newBalance)) return
      const userId = typeof currentUser.id === 'string' ? parseInt(currentUser.id, 10) : currentUser.id

      // Recarrega as contas atuais
      const result = await AccountAPI.getByUserId(userId)
      if (!result || result.length === 0) {
        error = "Nenhuma conta encontrada."
        return
      }

      // Soma atual
      const currentTotal = result.reduce((acc, c) => acc + (c.balance ?? 0), 0)
      const difference = newBalance - currentTotal

      // -------------------- CASO 1: ADIÇÃO DE SALDO --------------------
      if (difference > 0) {
        const last = result[result.length - 1]
        const updatedBalance = last.balance + difference
        await AccountAPI.updateBalance((last.id as any), updatedBalance)
      }

      // -------------------- CASO 2: RETIRADA DE SALDO --------------------
      else if (difference < 0) {
        let remaining = Math.abs(difference)
        // Começa da última conta
        for (let i = result.length - 1; i >= 0 && remaining > 0; i--) {
          const acc = result[i]
          const newAccBalance = acc.balance - remaining
          if (newAccBalance >= 0) {
            await AccountAPI.updateBalance((acc.id as any), newAccBalance)
            remaining = 0
          } else {
            await AccountAPI.updateBalance((acc.id as any), 0)
            remaining = Math.abs(newAccBalance)
          }
        }
      }

      // Atualiza exibição
      const updatedAccounts = await AccountAPI.getByUserId(userId)
      accounts = (updatedAccounts ?? [] as any)
      balance = accounts.reduce((acc, c) => acc + (c.balance ?? 0), 0)
      showBalanceModal = false
      newBalance = null
      error = ""
    } catch (err) {
      console.error(err)
      error = "Erro ao atualizar saldo."
    }
  }
</script>


<!-- ===================== Layout principal ===================== -->
<div class="flex flex-col items-center">
  <div class="w-full max-w-7xl bg-slate-800/60 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 p-8 space-y-5 text-white">

    <!-- Seção de renda -->
    <section class="flex flex-col lg:flex-row justify-between items-center gap-8 bg-gradient-to-br from-slate-900/60 to-slate-800/50 rounded-2xl p-6 shadow-inner">
      <div class="flex flex-col items-center">
        <h2 class="text-white font-semibold text-lg mb-2">Renda</h2>
        <div class="w-60 h-60 bg-slate-900/40 rounded-full flex items-center justify-center shadow-lg text-gray-300">(Gráfico)</div>
      </div>

      <div class="grid grid-cols-2 gap-4 text-sm text-gray-900 font-semibold">

        <!-- Saldo -->
        <button
          onclick={() => (showBalanceModal = true)}
          class="bg-yellow-400/80 text-black p-4 rounded-xl shadow-md hover:brightness-95 transition-all"
        >
          Saldo em conta<br />
          <span class="font-normal text-gray-800">
            {#if error}
              <span class="text-red-600">{error}</span>
            {:else if balance === null}
              <span class="text-gray-500 animate-pulse">Carregando...</span>
            {:else}
              {formatCurrency(balance)}
            {/if}
          </span>
        </button>

        <!-- Organização mensal -->
        <button
          onclick={() => (showOrganizationModal = true)}
          class="bg-cyan-400/80 text-black p-4 rounded-xl shadow-md hover:brightness-95 transition-all"
        >
          Organização mensal<br />
          <span class="font-normal">
            {#if reservation !== null && leisure !== null}
              Reserva: {formatCurrency(reservation)}<br />
              Lazer: {formatCurrency(leisure)}
            {:else}
              <span class="text-gray-500 animate-pulse">Carregando...</span>
            {/if}
          </span>
        </button>

        <!-- Alertas -->
        <button
          onclick={() => (showAlertsModal = true)}
          class="bg-purple-500/80 text-white p-4 rounded-xl shadow-md hover:brightness-95 transition-all"
        >
          Alertas<br />
          <span class="font-normal text-gray-200">Nenhum alerta</span>
        </button>

        <!-- Cartão -->
        <button
          onclick={() => (showCreditModal = true)}
          class="bg-cyan-500/80 text-black p-4 rounded-xl shadow-md hover:brightness-95 transition-all"
        >
          Cartão de crédito<br />
          <span class="font-normal">
            {#if creditLimit === null}
              <span class="text-gray-500 animate-pulse">Carregando...</span>
            {:else}
              Limite: {formatCurrency(creditLimit)}
            {/if}
          </span>
        </button>
      </div>
    </section>

    <!-- Seção de investimentos -->
    <section class="flex flex-col lg:flex-row justify-between items-center gap-8 bg-gradient-to-br from-slate-900/60 to-slate-800/50 rounded-2xl p-6 shadow-inner">
      <div class="flex flex-col items-center">
        <h2 class="text-white font-semibold text-lg mb-2">Investimentos</h2>
        <div class="w-60 h-60 bg-slate-900/40 rounded-full flex items-center justify-center shadow-lg text-gray-300">(Gráfico)</div>
      </div>

      <div class="grid grid-cols-2 gap-4 text-sm text-gray-900 font-semibold">
        <button class="bg-yellow-400/80 text-black p-4 rounded-xl shadow-md hover:brightness-95 transition-all">
          Alertas<br />
          <span class="font-normal text-gray-800">Sem alertas agendados</span>
        </button>
        <button class="bg-cyan-400/80 text-black p-4 rounded-xl shadow-md hover:brightness-95 transition-all">
          Compras programadas<br />
          <span class="font-normal">LIGT3: R$500<br />IFCM3: R$800</span>
        </button>
        <button class="bg-fuchsia-500/80 text-white p-4 rounded-xl shadow-md hover:brightness-95 transition-all">
          Ações<br />
          <span class="font-normal text-gray-200">BBAS3, PETR4...</span>
        </button>
        <button class="bg-cyan-500/80 text-black p-4 rounded-xl shadow-md hover:brightness-95 transition-all">
          Vendas programadas<br />
          <span class="font-normal">VALE3: R$500</span>
        </button>
      </div>
    </section>
  </div>

  <!-- ===================== Modais ===================== -->

  <!-- Editar Saldo -->
  {#if showBalanceModal}
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl shadow-xl p-6 w-80 text-black">
        <h3 class="text-lg font-semibold mb-4">Editar saldo</h3>
        <input
          type="number"
          bind:value={newBalance}
          placeholder="Digite o novo saldo"
          class="w-full border border-gray-300 rounded-lg p-2 mb-4 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <div class="flex justify-end gap-3">
          <button class="bg-gray-300 px-3 py-1 rounded-lg hover:bg-gray-400" onclick={() => (showBalanceModal = false)}>Cancelar</button>
          <button class="bg-blue-600 text-white px-3 py-1 rounded-lg hover:bg-blue-700" onclick={updateBalance}>Salvar</button>
        </div>
      </div>
    </div>
  {/if}

  <!-- Editar Organização (Reserva/Lazer) -->
  {#if showOrganizationModal}
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl shadow-xl p-6 w-96 text-black">
        <h3 class="text-lg font-semibold mb-4">Editar Organização Mensal</h3>
        <div class="flex flex-col gap-3">
          <!-- svelte-ignore a11y_label_has_associated_control -->
          <label>Reserva:</label>
          <input type="number" bind:value={newReservation} placeholder="Novo valor da reserva" class="border border-gray-300 rounded-lg p-2" />
          <!-- svelte-ignore a11y_label_has_associated_control -->
          <label>Lazer:</label>
          <input type="number" bind:value={newLeisure} placeholder="Novo valor de lazer" class="border border-gray-300 rounded-lg p-2" />
        </div>
        <div class="flex justify-end gap-3 mt-4">
          <button class="bg-gray-300 px-3 py-1 rounded-lg hover:bg-gray-400" onclick={() => (showOrganizationModal = false)}>Cancelar</button>
          <button class="bg-blue-600 text-white px-3 py-1 rounded-lg hover:bg-blue-700">Salvar</button>
        </div>
      </div>
    </div>
  {/if}

  <!-- Editar Avisos -->
  {#if showAlertsModal}
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl shadow-xl p-6 w-96 text-black">
        <h3 class="text-lg font-semibold mb-4">Gerenciar Alertas</h3>
        <textarea placeholder="Escreva um novo alerta..." class="w-full border border-gray-300 rounded-lg p-2 h-24"></textarea>
        <div class="flex justify-end gap-3 mt-4">
          <button class="bg-gray-300 px-3 py-1 rounded-lg hover:bg-gray-400" onclick={() => (showAlertsModal = false)}>Fechar</button>
          <button class="bg-blue-600 text-white px-3 py-1 rounded-lg hover:bg-blue-700">Salvar</button>
        </div>
      </div>
    </div>
  {/if}

  <!-- Editar Cartão -->
  {#if showCreditModal}
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">
      <div class="bg-white rounded-xl shadow-xl p-6 w-80 text-black">
        <h3 class="text-lg font-semibold mb-4">Editar Limite de Crédito</h3>
        <input
          type="number"
          bind:value={newCredit}
          placeholder="Digite o novo limite"
          class="w-full border border-gray-300 rounded-lg p-2 mb-4"
        />
        <div class="flex justify-end gap-3">
          <button class="bg-gray-300 px-3 py-1 rounded-lg hover:bg-gray-400" onclick={() => (showCreditModal = false)}>Cancelar</button>
          <button class="bg-blue-600 text-white px-3 py-1 rounded-lg hover:bg-blue-700">Salvar</button>
        </div>
      </div>
    </div>
  {/if}
</div>

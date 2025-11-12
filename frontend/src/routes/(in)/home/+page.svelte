<script lang="ts">
  import { onMount } from 'svelte'
  import { AccountAPI } from "$lib/api/account"
  import { get } from 'svelte/store'
  import { StoreUser } from "$lib/stores/userStore"

  let balance: number | null = null
  let error = ""
  let showModal = false
  let newBalance: number | null = null

  // busca o saldo do usuário ao carregar a página
  onMount(async () => {
    try {
      const currentUser = get(StoreUser) as { id?: string | number } | null

      if (currentUser?.id) {
        const userId = typeof currentUser.id === 'string' ? parseInt(currentUser.id, 10) : currentUser.id
        const result = await AccountAPI.getTotalBalance(userId)
        balance = result ?? 0
      } else {
        error = "Usuário não encontrado. Faça login novamente."
      }
    } catch (err) {
      console.error(err)
      error = "Erro ao buscar saldo da conta."
    }
  })

  // formata o valor para o formato de moeda brasileira
  const formatCurrency = (value: number) =>
    value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })

  // atualiza o saldo manualmente
  async function updateBalance() {
    try {
      const currentUser = get(StoreUser)
      if (!currentUser?.id) return

      const userId = typeof currentUser.id === 'string' ? parseInt(currentUser.id, 10) : currentUser.id
      if (newBalance === null || isNaN(newBalance)) return

      await AccountAPI.updateBalance(userId, newBalance)
      balance = newBalance
      showModal = false
    } catch (err) {
      console.error(err)
      error = "Erro ao atualizar saldo."
    }
  }
</script>

<div class="flex flex-col items-center">
  <div class="w-full max-w-7xl bg-slate-800/60 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 p-8 space-y-5 text-white">

    <!-- seção de renda -->
    <section class="flex flex-col lg:flex-row justify-between items-center gap-8 bg-gradient-to-br from-slate-900/60 to-slate-800/50 rounded-2xl p-6 shadow-inner">
      <div class="flex flex-col items-center">
        <h2 class="text-white font-semibold text-lg mb-2">Renda</h2>
        <div class="w-60 h-60 bg-slate-900/40 rounded-full flex items-center justify-center shadow-lg text-gray-300">(Gráfico)</div>
      </div>

      <div class="grid grid-cols-2 gap-4 text-sm text-gray-900 font-semibold">
        <!-- saldo em conta -->
        <button
          on:click={() => (showModal = true)}
          class="bg-yellow-400/80 text-black p-4 rounded-xl shadow-md block hover:brightness-95 transition-all"
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

        <!-- organização mensal -->
        <a href="/organizacao" class="bg-cyan-400/80 text-black p-4 rounded-xl shadow-md block hover:brightness-95 transition-all">
          Organização mensal<br />
          <span class="font-normal">Reserva: R$500<br />Lazer: R$800</span>
        </a>

        <!-- alertas -->
        <a href="/alertas" class="bg-purple-500/80 text-white p-4 rounded-xl shadow-md block hover:brightness-95 transition-all">
          Alertas<br />
          <span class="font-normal text-gray-200">Nenhum alerta</span>
        </a>

        <!-- cartão de crédito -->
        <a href="/cartao" class="bg-cyan-500/80 text-black p-4 rounded-xl shadow-md block hover:brightness-95 transition-all">
          Cartão de crédito<br />
          <span class="font-normal">Limite: R$500</span>
        </a>
      </div>
    </section>

    <!-- seção de investimentos -->
    <section class="flex flex-col lg:flex-row justify-between items-center gap-8 bg-gradient-to-br from-slate-900/60 to-slate-800/50 rounded-2xl p-6 shadow-inner">
      <div class="flex flex-col items-center">
        <h2 class="text-white font-semibold text-lg mb-2">Investimentos</h2>
        <div class="w-60 h-60 bg-slate-900/40 rounded-full flex items-center justify-center shadow-lg text-gray-300">(Gráfico)</div>
      </div>

      <div class="grid grid-cols-2 gap-4 text-sm text-gray-900 font-semibold">
        <a href="/investimentos/alertas" class="bg-yellow-400/80 text-black p-4 rounded-xl shadow-md block hover:brightness-95 transition-all">
          Alertas<br />
          <span class="font-normal text-gray-800">Sem alertas agendados</span>
        </a>
        <a href="/investimentos/compras" class="bg-cyan-400/80 text-black p-4 rounded-xl shadow-md block hover:brightness-95 transition-all">
          Compras programadas<br />
          <span class="font-normal">LIGT3: R$500<br />IFCM3: R$800</span>
        </a>
        <a href="/investimentos/acoes" class="bg-fuchsia-500/80 text-white p-4 rounded-xl shadow-md block hover:brightness-95 transition-all">
          Ações<br />
          <span class="font-normal text-gray-200">BBAS3, PETR4...</span>
        </a>
        <a href="/investimentos/vendas" class="bg-cyan-500/80 text-black p-4 rounded-xl shadow-md block hover:brightness-95 transition-all">
          Vendas programadas<br />
          <span class="font-normal">VALE3: R$500</span>
        </a>
      </div>
    </section>
  </div>

  <!-- modal de edição de saldo -->
  {#if showModal}
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
          <button
            class="bg-gray-300 px-3 py-1 rounded-lg hover:bg-gray-400 transition"
            on:click={() => (showModal = false)}
          >
            Cancelar
          </button>
          <button
            class="bg-blue-600 text-white px-3 py-1 rounded-lg hover:bg-blue-700 transition"
            on:click={updateBalance}
          >
            Salvar
          </button>
        </div>
      </div>
    </div>
  {/if}
</div>

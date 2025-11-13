<script lang="ts">
  import { onMount } from 'svelte'
  import { AccountAPI } from '$lib/api/account'
  import { get } from 'svelte/store'
  import { StoreUser } from '$lib/stores/userStore'

  // -------------------- Tipagem do modelo --------------------
  interface Account {
    id?: number
    number?: string
    balance?: number
  }

  // -------------------- Variáveis de estado --------------------
  let accounts: Account[] = [] // Lista de contas do usuário
  let error = ''               // Mensagem de erro
  let creating = false         // Controle de exibição do formulário de criação

  // -------------------- Campos do formulário --------------------
  let number = ''              // Número da nova conta
  let balance: number | null = null // Saldo inicial da nova conta

  // -------------------- Busca todas as contas do usuário logado --------------------
  onMount(async () => {
    try {
      const currentUser = get(StoreUser)
      if (!currentUser?.id) {
        error = 'Usuário não encontrado. Faça login novamente.'
        return
      }

      const userId =
        typeof currentUser.id === 'string'
          ? parseInt(currentUser.id, 10)
          : currentUser.id

      const result = await AccountAPI.getByUserId(userId)
      if (Array.isArray(result)) accounts = result
    } catch (err) {
      console.error(err)
      error = 'Erro ao carregar contas do usuário.'
    }
  })

  // -------------------- Formata valores em reais (R$) --------------------
  const formatCurrency = (value: number) =>
    value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })

  // -------------------- Cria uma nova conta --------------------
  async function createAccount() {
    try {
      const currentUser = get(StoreUser)
      if (!currentUser?.id) return

      const userId =
        typeof currentUser.id === 'string'
          ? parseInt(currentUser.id, 10)
          : currentUser.id

      // Validação dos campos
      if (!number.trim() || balance === null || balance < 0) {
        error = 'Preencha todos os campos corretamente.'
        return
      }

      // Dados a serem enviados à API

    const data = {
      number,
      balance,
      user: { id: currentUser.id, role: currentUser.role } 
    };

      const created: Account | null = await AccountAPI.create(data)

      if (created) {
        accounts = [...accounts, created]
        number = ''
        balance = null
        creating = false
        error = ''
      }
    } catch (err) {
      console.error(err)
      error = 'Erro ao criar conta.'
    }
  }
</script>

<!-- -------------------- Estrutura da Página -------------------- -->
<div class="min-h-screen flex flex-col items-center justify-center p-6 text-white">
  <div
    class="w-full max-w-6xl bg-slate-800/60 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 p-8 space-y-6"
  >

    <!-- -------------------- Cabeçalho -------------------- -->
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-semibold">Minhas Contas</h1>
      <button
        on:click={() => (creating = !creating)}
        class="bg-blue-600 hover:bg-blue-700 px-4 py-2 rounded-lg font-medium transition"
      >
        {creating ? 'Cancelar' : 'Adicionar Conta'}
      </button>
    </div>

    <!-- -------------------- Mensagem de Erro -------------------- -->
    {#if error}
      <p class="text-red-400 text-sm">{error}</p>
    {/if}

    <!-- -------------------- Formulário de Criação de Conta -------------------- -->
    {#if creating}
      <div class="bg-slate-900/40 p-6 rounded-xl shadow-inner grid grid-cols-1 md:grid-cols-3 gap-4">
        <input
          type="text"
          placeholder="Número da Conta"
          bind:value={number}
          class="w-full border border-gray-300 rounded-lg p-2 text-black focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
          type="number"
          placeholder="Saldo Inicial"
          bind:value={balance}
          min="0"
          step="0.01"
          class="w-full border border-gray-300 rounded-lg p-2 text-black focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <button
          on:click={createAccount}
          class="bg-green-600 hover:bg-green-700 text-white px-4 py-2 rounded-lg transition"
        >
          Salvar Conta
        </button>
      </div>
    {/if}

    <!-- -------------------- Lista de Contas -------------------- -->
    {#if accounts.length > 0}
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-6">
        {#each accounts as account}
          <div class="bg-slate-900/40 p-6 rounded-xl shadow-inner space-y-2">
            <p class="text-gray-300 text-sm">Número da Conta</p>
            <p class="text-lg font-semibold">{account.number}</p>

            <p class="text-gray-300 text-sm mt-2">Saldo Atual</p>
            <p class="text-2xl font-bold text-yellow-400">
              {account.balance !== undefined && account.balance !== null
                ? formatCurrency(account.balance)
                : 'R$ 0,00'}
            </p>
          </div>
        {/each}
      </div>
    {:else}
      <p class="text-gray-400 text-center mt-6">Nenhuma conta cadastrada.</p>
    {/if}
  </div>
</div>

<script lang="ts">
  import { onMount } from 'svelte';
  import { get } from 'svelte/store';
  import { ContaFinanceiraAPI, type ContaFinanceira } from '$lib/api/contaFinanceira';
  import { StoreUser } from '$lib/stores/userStore';
  import { protectRoute } from '$lib/utils/auth';

  export const load = async () => {
    protectRoute();
    return {};
  };

  interface Account {
    id?: number;
    nome: string;
    saldoAtual: number;
  }

  let accounts: Account[] = [];
  let error = '';
  let creating = false;
  let nome = '';
  let saldoAtual: number | null = null;

  // Formata valores monetários
  const formatCurrency = (value: number) =>
    value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

  onMount(async () => {
    try {
      const currentUser = get(StoreUser);
      if (!currentUser?.id) {
        error = 'Usuário não encontrado. Faça login novamente.';
        return;
      }

      const userId =
        typeof currentUser.id === 'string'
          ? parseInt(currentUser.id, 10)
          : currentUser.id;

      try {
        const result = await ContaFinanceiraAPI.getByUsuario(userId);
        // Normaliza resultado em array
        accounts = result ? (Array.isArray(result) ? result : [result]) : [];
      } catch (err: any) {
        if (err.message.includes('404')) {
          // Usuário não tem conta → inicializamos array vazio
          accounts = [];
        } else {
          throw err;
        }
      }
    } catch (err) {
      console.error(err);
      error = 'Erro ao carregar contas do usuário.';
    }
  });

  async function createAccount() {
    try {
      const currentUser = get(StoreUser);
      if (!currentUser?.id) return;

      const userId =
        typeof currentUser.id === 'string'
          ? parseInt(currentUser.id, 10)
          : currentUser.id;

      if (!nome.trim() || saldoAtual === null || saldoAtual < 0) {
        error = 'Preencha todos os campos corretamente.';
        return;
      }

      const data: Omit<ContaFinanceira, 'id'> = {
        nome,
        saldoAtual,
        usuarioId: userId
      };

      const created = await ContaFinanceiraAPI.create(userId, data as ContaFinanceira);

      accounts = [...accounts, created];
      nome = '';
      saldoAtual = null;
      creating = false;
      error = '';
    } catch (err) {
      console.error(err);
      error = 'Erro ao criar conta.';
    }
  }
</script>

<div class="min-h-screen flex flex-col items-center justify-center p-6 text-white">
  <div class="w-full max-w-6xl bg-slate-800/60 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 p-8 space-y-6">

    <!-- Cabeçalho -->
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-semibold">Minhas Contas</h1>
      <button
        on:click={() => (creating = !creating)}
        class="bg-blue-600 hover:bg-blue-700 px-4 py-2 rounded-lg font-medium transition"
      >
        {creating ? 'Cancelar' : 'Adicionar Conta'}
      </button>
    </div>

    <!-- Mensagem de Erro -->
    {#if error}
      <p class="text-red-400 text-sm">{error}</p>
    {/if}

    <!-- Formulário de Criação de Conta -->
    {#if creating}
      <div class="bg-slate-900/40 p-6 rounded-xl shadow-inner grid grid-cols-1 md:grid-cols-3 gap-4">
        <input
          type="text"
          placeholder="Nome da Conta"
          bind:value={nome}
          class="w-full border border-gray-300 rounded-lg p-2 text-black focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
        <input
          type="number"
          placeholder="Saldo Inicial"
          bind:value={saldoAtual}
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

    <!-- Lista de Contas -->
    {#if accounts.length > 0}
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-6">
        {#each accounts as account}
          <div class="bg-slate-900/40 p-6 rounded-xl shadow-inner space-y-2">
            <p class="text-gray-300 text-sm">Conta</p>
            <p class="text-xl font-semibold">{account.nome}</p>
            <p class="text-gray-300 text-sm mt-2">Saldo Atual</p>
            <p class="text-2xl font-bold text-yellow-400">
              {account.saldoAtual !== undefined && account.saldoAtual !== null
                ? formatCurrency(account.saldoAtual)
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

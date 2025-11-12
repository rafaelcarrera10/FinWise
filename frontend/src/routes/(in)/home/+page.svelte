<script lang="ts">
  import { onMount } from 'svelte';
  import {AccountAPI} from "$lib/api/account"
  import { get } from 'svelte/store';
  import { StoreUser } from "$lib/stores/userStore"
  
  let balance: number | null = null;
  let error = "";

 onMount(async () => {
    try {
      const currentUser = get(StoreUser) as { id?: string | number } | null; 

      if (currentUser?.id) {
        const userId = typeof currentUser.id === 'string' ? parseInt(currentUser.id, 10) : currentUser.id;
        const result = await AccountAPI.getTotalBalance(userId);
        balance = result ?? 0; // salva o valor na variável reativa
      } else {
        error = "Usuário não encontrado. Faça login novamente.";
      }
    } catch (err) {
      console.error(err);
      error = "Erro ao buscar saldo da conta.";
    }
  });
  const formatCurrency = (value: number) =>
    value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

</script>


<div class="min-h-screen bg-gradient-to-br from-gray-900 via-slate-600 to-gray-400 flex flex-col items-center p-6">
  <div class="w-full max-w-7xl bg-slate-800/60 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 p-8 space-y-5 text-white">
    <section class="flex flex-col lg:flex-row justify-between items-center gap-8 bg-gradient-to-br from-slate-900/60 to-slate-800/50 rounded-2xl p-6 shadow-inner">
      <div class="flex flex-col items-center">
        <h2 class="text-white font-semibold text-lg mb-2">Renda</h2>
        <div class="w-60 h-60 bg-slate-900/40 rounded-full flex items-center justify-center shadow-lg text-gray-300">(Gráfico)</div>
      </div>

      <div class="grid grid-cols-2 gap-4 text-sm text-gray-900 font-semibold">
          <div class="bg-yellow-400/80 text-black p-4 rounded-xl shadow-md">
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
          </div>

  <div class="bg-cyan-400/80 text-black p-4 rounded-xl shadow-md">
    Organização mensal<br />
    <span class="font-normal">Reserva: R$500<br />Lazer: R$800</span>
  </div>

  <div class="bg-purple-500/80 text-white p-4 rounded-xl shadow-md">
    Alertas<br />
    <span class="font-normal text-gray-200">Nenhum alerta</span>
  </div>

  <div class="bg-cyan-500/80 text-black p-4 rounded-xl shadow-md">
    Cartão de crédito<br />
    <span class="font-normal">Limite: R$500</span>
  </div>
</div>

    </section>

    <section class="flex flex-col lg:flex-row justify-between items-center gap-8 bg-gradient-to-br from-slate-900/60 to-slate-800/50 rounded-2xl p-6 shadow-inner">
      <div class="flex flex-col items-center">
        <h2 class="text-white font-semibold text-lg mb-2">Investimentos</h2>
        <div class="w-60 h-60 bg-slate-900/40 rounded-full flex items-center justify-center shadow-lg text-gray-300">(Gráfico)</div>
      </div>

      <div class="grid grid-cols-2 gap-4 text-sm text-gray-900 font-semibold">
        <div class="bg-yellow-400/80 text-black p-4 rounded-xl shadow-md">
          Alertas<br />
          <span class="font-normal text-gray-800">Sem alertas agendados</span>
        </div>
        <div class="bg-cyan-400/80 text-black p-4 rounded-xl shadow-md">
          Compras programadas<br />
          <span class="font-normal">LIGT3: R$500<br />IFCM3: R$800</span>
        </div>
        <div class="bg-fuchsia-500/80 text-white p-4 rounded-xl shadow-md">
          Ações<br />
          <span class="font-normal text-gray-200">BBAS3, PETR4...</span>
        </div>
        <div class="bg-cyan-500/80 text-black p-4 rounded-xl shadow-md">
          Vendas programadas<br />
          <span class="font-normal">VALE3: R$500</span>
        </div>
      </div>
    </section>
  </div>
</div>

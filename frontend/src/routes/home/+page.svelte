<script>
  let isCollapsed = false;

  function toggleSidebar() {
    isCollapsed = !isCollapsed;
  }
</script>

<div class="flex h-screen overflow-hidden">
  <!-- Sidebar -->
  <aside
    class="fixed h-full bg-gray-950 text-white flex flex-col transition-all duration-300 ease-in-out shadow-lg overflow-hidden"
    class:w-64={!isCollapsed}
    class:w-20={isCollapsed}
  >
    <!-- Cabeçalho -->
    <div class="flex items-center justify-between p-4 text-xl font-bold border-b border-gray-700">
      <a href="/home" class="flex items-center space-x-2">
        <span class="text-lg font-semibold" class:hidden={isCollapsed}>FinWise</span>
      </a>
      <button
        on:click={toggleSidebar}
        class="text-gray-400 hover:text-white text-sm focus:outline-none"
        aria-label="Alternar barra"
      >
        {#if isCollapsed}
          {">"}
        {:else}
          {"<"}
        {/if}
      </button>
    </div>

    <!-- Campo de pesquisa (oculto no estado colapsado) -->
    {#if !isCollapsed}
      <div class="relative px-2 py-2">
        <span class="absolute inset-y-0 left-6 flex items-center text-gray-400">🔍</span>
        <input
          type="text"
          placeholder="Pesquisar..."
          class="w-full bg-gray-700 text-gray-100 placeholder-gray-400 pl-10 pr-3 py-1.5 text-sm rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
      </div>
    {/if}

    <!-- Menu -->
    <nav class="flex-1 p-2 space-y-4 overflow-y-hidden">
      <!-- Geral -->
      <div>
        {#if !isCollapsed}
          <h1 class="text-gray-400 text-xs uppercase mb-1">Geral</h1>
        {/if}
        <a href="/" class="block px-4 py-2 rounded hover:bg-gray-700 ">
          {#if isCollapsed}
            🏠
          {:else}
            <span>Home</span>
          {/if}
        </a>
        <a href="/" class="block px-4 py-2 rounded hover:bg-gray-700">
          {#if isCollapsed}
            👤
          {:else}
            <span>Perfil</span>
          {/if}
        </a>
      </div>

      <!-- Financeiro -->
      <div>
        {#if !isCollapsed}
          <h1 class="text-gray-400 text-xs uppercase mb-1">Financeiro</h1>
        {/if}
        <a href="/" class="block px-4 py-2 rounded hover:bg-gray-700">
          {#if isCollapsed}
            💰
          {:else}
            <span>Investimentos</span>
          {/if}
        </a>
        <a href="/" class="block px-4 py-2 rounded hover:bg-gray-700">
          {#if isCollapsed}
            📈
          {:else}
            <span>Renda</span>
          {/if}
        </a>
        <a href="/" class="block px-4 py-2 rounded hover:bg-gray-700">
          {#if isCollapsed}
            💼
          {:else}
            <span>Carteira</span>
          {/if}
        </a>
      </div>

      <!-- Área de estudo -->
      <div>
        {#if !isCollapsed}
          <h1 class="text-gray-400 text-xs uppercase mb-1">Área de Estudo</h1>
        {/if}
        <a href="/" class="block px-4 py-2 rounded hover:bg-gray-700">
          {#if isCollapsed}
            📚
          {:else}
            <span>Módulos</span>
          {/if}
        </a>
        <a href="/" class="block px-4 py-2 rounded hover:bg-gray-700">
          {#if isCollapsed}
            ⭐
          {:else}
            <span>Favoritados</span>
          {/if}
        </a>
      </div>
    </nav>

    <!-- Rodapé -->
    <div class="p-2 border-t border-gray-700">
      <button class=" text-left px-2 py-1 rounded hover:bg-gray-700">
        {#if isCollapsed}
          🚪
        {:else}
          <span>Sair</span>
        {/if}
      </button>
    </div>
  </aside>

  <!-- Conteúdo principal -->
  <main
    class="flex-1 transition-all duration-300 ease-in-out overflow-y-auto"
    class:ml-64={!isCollapsed}
    class:ml-20={isCollapsed}
  >
    <div class="min-h-screen bg-gradient-to-br from-gray-900 via-slate-600 to-gray-400 flex flex-col items-center p-6">
      <div class="w-full max-w-7xl bg-slate-800/60 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 p-8 space-y-5 text-white">
        <section class="flex flex-col lg:flex-row justify-between items-center gap-8 bg-gradient-to-br from-slate-900/60 to-slate-800/50 rounded-2xl p-6 shadow-inner">
          <div class="flex flex-col items-center">
            <h2 class="text-white font-semibold text-lg mb-2">Renda</h2>
            <div class="w-60 h-60 bg-slate-900/40 rounded-full flex items-center justify-center shadow-lg text-gray-300">(Gráfico)</div>
          </div>

          <div class="grid grid-cols-2 gap-4 text-sm text-gray-900 font-semibold">
            <div class="bg-yellow-400/80 text-black p-4 rounded-xl shadow-md">Alertas<br /><span class="font-normal text-gray-800">Sem alertas agendados</span></div>
            <div class="bg-cyan-400/80 text-black p-4 rounded-xl shadow-md">Organização mensal<br /><span class="font-normal">Reserva: R$500<br />Lazer: R$800</span></div>
            <div class="bg-purple-500/80 text-white p-4 rounded-xl shadow-md">Alertas<br /><span class="font-normal text-gray-200">Nenhum alerta</span></div>
            <div class="bg-cyan-500/80 text-black p-4 rounded-xl shadow-md">Cartão de crédito<br /><span class="font-normal">Limite: R$500</span></div>
          </div>
        </section>

        <section class="flex flex-col lg:flex-row justify-between items-center gap-8 bg-gradient-to-br from-slate-900/60 to-slate-800/50 rounded-2xl p-6 shadow-inner">
          <div class="flex flex-col items-center">
            <h2 class="text-white font-semibold text-lg mb-2">Investimentos</h2>
            <div class="w-60 h-60 bg-slate-900/40 rounded-full flex items-center justify-center shadow-lg text-gray-300">(Gráfico)</div>
          </div>

          <div class="grid grid-cols-2 gap-4 text-sm text-gray-900 font-semibold">
            <div class="bg-yellow-400/80 text-black p-4 rounded-xl shadow-md">Alertas<br /><span class="font-normal text-gray-800">Sem alertas agendados</span></div>
            <div class="bg-cyan-400/80 text-black p-4 rounded-xl shadow-md">Compras programadas<br /><span class="font-normal">LIGT3: R$500<br />IFCM3: R$800</span></div>
            <div class="bg-fuchsia-500/80 text-white p-4 rounded-xl shadow-md">Ações<br /><span class="font-normal text-gray-200">BBAS3, PETR4...</span></div>
            <div class="bg-cyan-500/80 text-black p-4 rounded-xl shadow-md">Vendas programadas<br /><span class="font-normal">VALE3: R$500</span></div>
          </div>
        </section>
      </div>
    </div>
  </main>
</div>

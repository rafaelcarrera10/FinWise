<script lang="ts">
  import '../../app.css'
  import favicon from '$lib/assets/favicon.svg'
  let isCollapsed = false

  // alterna o estado da barra lateral
  function toggleSidebar() {
    isCollapsed = !isCollapsed
  }
</script>

<svelte:head>
  <link rel="icon" href={favicon} />
</svelte:head>

<div class="flex h-screen overflow-hidden bg-gradient-to-br from-gray-900 via-slate-600 to-gray-400">
  <!-- barra lateral -->
  <aside
    class="fixed h-full bg-gray-950 text-white flex flex-col transition-all duration-300 ease-in-out shadow-lg overflow-hidden"
    class:w-64={!isCollapsed}
    class:w-20={isCollapsed}
  >
    <div class="flex items-center justify-between p-4 text-xl font-bold border-b border-gray-700">
      <a href="/home" class="flex items-center space-x-2">
        <span class="text-lg font-semibold" class:hidden={isCollapsed}>FinWise</span>
      </a>
      <button on:click={toggleSidebar} class="text-gray-400 hover:text-white text-sm focus:outline-none">
        {#if isCollapsed}
          {">"}
        {:else}
          {"<"}
        {/if}
      </button>
    </div>

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

    <nav class="flex-1 p-2 space-y-4 overflow-y-hidden">
      <div>
        {#if !isCollapsed}
          <h1 class="text-gray-400 text-xs uppercase mb-1">Geral</h1>
        {/if}
        <a href="/home" class="block px-4 py-2 rounded hover:bg-gray-700">{#if isCollapsed}🏠{:else}<span>Home</span>{/if}</a>
        <a href="/perfil" class="block px-4 py-2 rounded hover:bg-gray-700">{#if isCollapsed}👤{:else}<span>Perfil</span>{/if}</a>
      </div>

      <div>
        {#if !isCollapsed}
          <h1 class="text-gray-400 text-xs uppercase mb-1">Financeiro</h1>
        {/if}
        <a href="/conta" class="block px-4 py-2 rounded hover:bg-gray-700">{#if isCollapsed}💼{:else}<span>Conta</span>{/if}</a>
        <a href="/investimentos" class="block px-4 py-2 rounded hover:bg-gray-700">{#if isCollapsed}📈{:else}<span>Investimentos</span>{/if}</a>
      </div>

      <div>
        {#if !isCollapsed}
          <h1 class="text-gray-400 text-xs uppercase mb-1">Área de estudo</h1>
        {/if}
        <a href="/modulos" class="block px-4 py-2 rounded hover:bg-gray-700">{#if isCollapsed}📚{:else}<span>Módulos</span>{/if}</a>
        <a href="/favoritos" class="block px-4 py-2 rounded hover:bg-gray-700">{#if isCollapsed}⭐{:else}<span>Favoritados</span>{/if}</a>
      </div>
    </nav>

    <div class="p-2 border-t border-gray-700">
      <button class="text-left px-2 py-1 rounded hover:bg-gray-700">{#if isCollapsed}🚪{:else}<span>Sair</span>{/if}</button>
    </div>
  </aside>

  <!-- conteúdo principal -->
  <main
    class="flex-1 transition-all duration-300 ease-in-out overflow-y-auto p-6"
    class:ml-64={!isCollapsed}
    class:ml-20={isCollapsed}
  >
    <slot />
  </main>
</div>

<script lang="ts">
  import '../../app.css';
  import favicon from '$lib/assets/favicon.svg';
  import logo from '$lib/assets/logo.svg';

  import Home from "lucide-svelte/icons/home";
  import User from "lucide-svelte/icons/user";
  import Briefcase from "lucide-svelte/icons/briefcase";
  import BookOpen from "lucide-svelte/icons/book-open";
  import Star from "lucide-svelte/icons/star";
  import Search from "lucide-svelte/icons/search";
  import ChartLine from "lucide-svelte/icons/chart-line";
  import LogOut from "lucide-svelte/icons/log-out";
  import ChevronLeft from "lucide-svelte/icons/chevron-left";
  import ChevronRight from "lucide-svelte/icons/chevron-right";

  import { goto } from '$app/navigation';
  import { StoreUser } from '$lib/stores/userStore'; // seu store de usuário

  let isCollapsed = true;
  function toggleSidebar() {
    isCollapsed = !isCollapsed;
  }

  // Função de logout
  function logout() {
    StoreUser.set(null);
    localStorage.removeItem('token');
    goto('/signin');
  }

</script>

<svelte:head>
  <link rel="icon" href={favicon} />
</svelte:head>

<div class="flex h-screen bg-gradient-to-br from-gray-900 via-slate-600 to-gray-400">
  <aside
    class="fixed h-full bg-gray-950 text-white flex flex-col transition-all duration-300 ease-in-out shadow-lg relative z-20"
    class:w-64={!isCollapsed}
    class:w-20={isCollapsed}
  >
    <!-- LOGO -->
    <div class="p-4 border-b border-gray-700">
      {#if isCollapsed}
        <div class="flex items-center justify-center">
          <img src={logo} alt="FinWise" class="w-10 h-10" />
        </div>
      {:else}
        <a href="/home" class="flex items-center gap-3">
          <img src={logo} alt="FinWise" class="w-10 h-10" />
          <span class="text-lg font-semibold">FinWise</span>
        </a>
      {/if}
    </div>

    <!-- Botão collapse -->
    <button
      on:click={toggleSidebar}
      class="absolute right-0 top-1/2 -translate-y-1/2 translate-x-1/2
             bg-gray-800 text-gray-200 border border-gray-600
             w-9 h-9 rounded-full flex items-center justify-center
             shadow-lg hover:bg-gray-700 transition z-30"
    >
      {#if isCollapsed}
        <ChevronRight class="w-6 h-6" />
      {:else}
        <ChevronLeft class="w-6 h-6" />
      {/if}
    </button>

    <!-- Campo de pesquisa (apenas aberto) -->
    {#if !isCollapsed}
      <div class="relative px-3 py-3">
        <Search class="absolute left-4 top-1/2 -translate-y-1/2 w-5 h-5 text-gray-400" />
        <input
          type="text"
          placeholder="Pesquisar..."
          class="w-full bg-gray-700 text-gray-100 placeholder-gray-400 pl-12 pr-3 py-2 text-sm rounded-md focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
      </div>
    {/if}

    <!-- MENU -->
    <nav class="flex-1 p-3 transition-all duration-300 overflow-y-auto space-y-6">
      {#each [
        { title: "Geral", items: [ { label: "Home", icon: Home, link: "/home" }, { label: "Perfil", icon: User, link: "/perfil" } ] },
        { title: "Financeiro", items: [ { label: "Conta", icon: Briefcase, link: "/conta" }, { label: "Investimentos", icon: ChartLine, link: "/investimentos" } ] },
        { title: "Área de estudo", items: [ { label: "Módulos", icon: BookOpen, link: "/modulos" }, { label: "Favoritados", icon: Star, link: "/favoritos" } ] }
      ] as group}

        <div class="w-full">
          {#if !isCollapsed}
            <h2 class="text-gray-400 text-xs uppercase mb-2 px-2">{group.title}</h2>
          {/if}

          {#each group.items as item}
            <a href={item.link} class="menu-item">
              {#if isCollapsed}
                <div class="flex items-center justify-center w-12 h-12 rounded-md hover:bg-gray-800 transition">
                  <item.icon class="w-6 h-6 text-gray-200" />
                </div>
              {:else}
                <div class="flex items-center gap-4 px-4 py-2 rounded-md hover:bg-gray-800 transition">
                  <div class="flex items-center justify-center w-10 h-10 rounded-md bg-gray-800">
                    <item.icon class="w-6 h-6 text-gray-200" />
                  </div>
                  <span>{item.label}</span>
                </div>
              {/if}
            </a>
          {/each}
        </div>
      {/each}
    </nav>

    <!-- SAIR -->
    <div class="p-3 border-t border-gray-700">
      <button on:click={logout} class="menu-item w-full text-left">
        {#if isCollapsed}
          <div class="flex items-center justify-center w-12 h-12 rounded-md hover:bg-gray-800 transition">
            <LogOut class="w-6 h-6 text-gray-200" />
          </div>
        {:else}
          <div class="flex items-center gap-4 px-4 py-2 rounded-md hover:bg-gray-800 transition">
            <div class="flex items-center justify-center w-10 h-10 rounded-md bg-gray-800">
              <LogOut class="w-6 h-6 text-gray-200" />
            </div>
            <span>Sair</span>
          </div>
        {/if}
      </button>
    </div>
  </aside>

  <!-- CONTEÚDO -->
  <main
    class="flex-1 transition-all duration-300 ease-in-out overflow-y-auto p-6"
    class:ml-64={!isCollapsed}
    class:ml-20={isCollapsed}
  >
    <slot />
  </main>
</div>

<style>
  .menu-item {
    display: flex;
    width: 100%;
    text-decoration: none;
  }
</style>

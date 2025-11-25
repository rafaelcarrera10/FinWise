<script lang="ts">
  import { goto } from '$app/navigation';
  import { UsuarioAPI } from '$lib/api/usuario';
  import Eye from 'lucide-svelte/icons/eye';
  import EyeOff from 'lucide-svelte/icons/eye-off';

  // Campos do formulário
  let name = "";
  let email = "";
  let password = "";
  let role: "aluno" | "professor" = "aluno";
  let error = "";

  // Controle do olho da senha
  let showPassword = false;

  // Criar conta
  async function createAccount() {
    error = "";
    try {
        const newUser = {
            nome: name,
            email,
            senha: password,
            role: role
        };

        const createdUser = await UsuarioAPI.create(newUser); // <-- Correto
        if (createdUser) {
            goto('/signin');
        }
    } catch (err) {
        console.error(err);
        error = "Erro ao criar conta";
    }
  }

  // Navegar para login
  function signin() {
    goto("/signin");
  }
</script>


<div class="absolute z-10 w-screen h-screen flex justify-center items-center">
  <div class="bg-white/90 p-16 rounded-2xl shadow-md w-[400px] flex flex-col justify-center items-center">
    <h1 class="text-2xl mb-10 text-center font-bold">CADASTRO</h1>

    <div class="flex justify-center gap-4 mb-5">
      <h2>Tipo da conta</h2>

      <label class="flex items-center gap-1">
        <input type="radio" name="role" value="aluno" bind:group={role} />
        Aluno
      </label>

      <label class="flex items-center gap-1">
        <input type="radio" name="role" value="professor" bind:group={role} />
        Professor
      </label>
    </div>

    <input 
      type="text" 
      bind:value={name} 
      placeholder="Nome" 
      class="mb-2 w-full p-2 border rounded"
    />

    <input 
      type="email" 
      bind:value={email} 
      placeholder="Email" 
      class="mb-2 w-full p-2 border rounded"
    />

    <!-- Campo de senha com botão de visualizar -->
    <div class="relative w-full mb-8">
      <input
        type={showPassword ? "text" : "password"}
        bind:value={password}
        placeholder="Senha"
        class="w-full p-2 border rounded"
      />

      <button
        type="button"
        class="absolute right-3 top-1/2 -translate-y-1/2"
        onclick={() => (showPassword = !showPassword)}
      >
        {#if showPassword}
          <EyeOff size="18" />
        {:else}
          <Eye size="18" />
        {/if}
      </button>
    </div>

    {#if error}
      <p class="text-red-500 mb-2">{error}</p>
    {/if}

    <div class="flex justify-between gap-4 w-full">
      <button onclick={createAccount} class="bg-green-500 text-white px-4 py-2 rounded-md w-full">
        Cadastrar-se
      </button>

      <button onclick={signin} class="bg-gray-200 px-4 py-2 rounded-md w-full">
        Voltar
      </button>
    </div>
  </div>
</div>

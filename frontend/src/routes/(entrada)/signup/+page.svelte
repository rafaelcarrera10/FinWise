<script lang="ts">
  import { goto } from '$app/navigation';
  import { UsuarioAPI } from '$lib/api/usuario'; 

  let name = "";
  let password = "";
  let role: "aluno" | "professor" = "aluno";
  let error = "";

  // Campos extras para professor
  let biografia = "";
  let fotoPerfil = "";
  const status = true; // ativo por padrão

  async function createAccount() {
    error = '';
    try {
      // Monta o objeto base
      const newUser: any = {
        name,
        password,
        role
      };

      // Adiciona campos extras se for professor
      if (role === "professor") {
        newUser.biografia = biografia;
        newUser.fotoPerfil = fotoPerfil;
        newUser.status = status;
      }

      const createdUser = await UsuarioAPI.create(newUser);
      if (createdUser) goto('/signin');

    } catch (err: any) {
      console.error(err);
      error = err.message || 'Erro desconhecido ao criar usuário';
    }
  }

  function signin() {
    goto("/signin");
  }
</script>

<div class="absolute z-10 w-screen h-screen flex justify-center items-center">
  <div class="bg-white/90 p-16 rounded-2xl shadow-md w-[450px] flex flex-col justify-center items-center">
    <h1 class="text-2xl mb-8 text-center font-bold">CADASTRO</h1>

    <!-- Seleção de tipo de conta -->
    <div class="flex justify-center gap-4 mb-5">
      <label class="flex items-center gap-1">
        <input type="radio" name="role" value="aluno" bind:group={role} /> Aluno
      </label>
      <label class="flex items-center gap-1">
        <input type="radio" name="role" value="professor" bind:group={role} /> Professor
      </label>
    </div>

    <!-- Campos comuns -->
    <input type="text" bind:value={name} placeholder="Nome" class="mb-2 w-full p-2 border rounded" />
    <input type="password" bind:value={password} placeholder="Senha" class="mb-4 w-full p-2 border rounded" />

    <!-- Campos adicionais só para professor -->
    {#if role === "professor"}
      <input type="text" bind:value={biografia} placeholder="Biografia" class="mb-2 w-full p-2 border rounded" />
      <input type="text" bind:value={fotoPerfil} placeholder="URL da Foto de Perfil" class="mb-4 w-full p-2 border rounded" />
    {/if}

    <!-- Mensagem de erro -->
    {#if error}
      <p class="text-red-500 mb-2">{error}</p>
    {/if}

    <div class="flex justify-between w-full">
      <button on:click={createAccount} class="bg-green-500 text-white px-4 py-2 rounded-md">Cadastrar-se</button>
      <button on:click={signin} class="bg-gray-200 px-4 py-2 rounded-md">Voltar</button>
    </div>
  </div>
</div>

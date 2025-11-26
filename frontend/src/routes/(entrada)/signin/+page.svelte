<script lang="ts">
  import { goto } from '$app/navigation';
  import { UsuarioAPI } from '$lib/api/usuario';
  import { StoreUser } from '$lib/stores/userStore';

  let name = "";
  let senha = "";
  let error = "";

  async function handleLogin() {
    error = "";

    if (!name || !senha) {
      error = "Preencha todos os campos";
      return;
    }

    try {
      const user = await UsuarioAPI.login(name, senha);

      if (user) {
        console.log("Usuário logado:", user);
        StoreUser.set(user); // salva no store/localStorage
        goto("/home");
      } else {
        error = "Nome ou senha inválidos";
      }
    } catch (err: any) {
      console.error("Erro ao fazer login:", err);
      error = err.message || "Erro ao conectar ao servidor";
    }
  }

  function signup() {
    goto("/signup");
  }

  function forgetpassword() {
    goto("/forgetpassword");
  }
</script>

<div class="absolute z-10 w-screen h-screen flex justify-center items-center">
  <div class="bg-white/90 p-16 rounded-2xl shadow-md w-[400px] h-[400px] flex flex-col justify-center items-center">
    <h1 class="text-2xl mb-10 text-center font-bold">LOGIN</h1>

    <input type="text" bind:value={name} placeholder="Nome de usuário" class="mb-2 w-full p-2 border rounded" />
    <input type="password" bind:value={senha} placeholder="Senha" class="mb-2 w-full p-2 border rounded" />
    
    {#if error}
      <p class="text-red-500 mb-2">{error}</p>
    {/if}

    <div class="flex justify-between w-full mb-5">
      <button onclick={forgetpassword} class="text-black">Esqueceu a senha</button>
    </div>

    <div class="flex justify-between w-full">
      <button onclick={handleLogin} class="bg-green-500 text-white px-4 py-2 rounded-md">Login</button>
      <button onclick={signup} class="bg-gray-200 px-4 py-2 rounded-md">Cadastrar-se</button>
    </div>
  </div>
</div>

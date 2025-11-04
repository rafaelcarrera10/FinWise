<script lang="ts">
import { UserAPI } from '$lib/api/user'; 

let email = "";
let password = "";
let error = "";

async function handleLogin() {
  error = "";
  try {
    const user = await UserAPI.login({email, password});
    if (user) {
      console.log("Usuário logado:", user);
      localStorage.setItem("user", JSON.stringify(user));
      // Redirecionar ou atualizar a UI
    } else {
      error = "Email ou senha inválidos";
    }
  } catch (err) {
    console.error(err);
    error = "Erro ao conectar ao servidor";
  }
}
</script>

<div class="absolute z-10 w-screen h-screen flex justify-center items-center">
  <div class="bg-white/90 p-8 rounded shadow-md w-96">
    <h1 class="text-2xl mb-4 text-center font-bold">LOGIN</h1>

    <input type="email" bind:value={email} placeholder="Email" class="mb-2 w-full p-2 border rounded" />
    <input type="password" bind:value={password} placeholder="Senha" class="mb-4 w-full p-2 border rounded" />
    
    {#if error}
      <p class="text-red-500 mb-2">{error}</p>
    {/if}

    <div class="flex justify-between">
      <button on:click={handleLogin} class="bg-green-500 text-white px-4 py-2 rounded">Login</button>
      <button class="bg-gray-200 px-4 py-2 rounded">Cadastrar-se</button>
    </div>
  </div>
</div>
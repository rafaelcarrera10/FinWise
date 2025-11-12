<script lang="ts">
import { goto } from '$app/navigation';
import { UserAPI } from '$lib/api/user'; 
import { StoreUser } from '$lib/stores/userStore';


// svelte-ignore non_reactive_update
let email = "";
let password = $state("");
let role = '';
// svelte-ignore non_reactive_update
let error = "";

async function findRoleByEmail() {
  try {
    const response = await UserAPI.getByEmail(email);
    if (response) {
      role = response.role; // exemplo: "aluno" | "professor" | "admin"
    }
  } catch (err) {
    console.error(err);
    error = 'Não foi possível identificar o tipo da conta';
  }
}

async function handleLogin() {
  error = "";
  try {
    if (!role) {
      await findRoleByEmail();
      if (!role) {
        error = "Não foi possível identificar o tipo da conta";
        return;
      }
    }

    const loginData = {
      email,
      password,
      role
    };

    const user = await UserAPI.login(loginData);

    if (user) {
      console.log("Usuário logado:", user);
      localStorage.setItem("user", JSON.stringify(user));
      StoreUser.set(user);
      goto('/home');
      
    } else {
      error = "Email ou senha inválidos";
    }
  } catch (err) {
    console.error(err);
    error = "Erro ao conectar ao servidor";
  }

}

async function signup() {
  goto("/signup")
}
async function forgetpassword() {
  goto("/forgetpassword")
}
 $inspect(password)
</script>

<div class="absolute z-10 w-screen h-screen flex justify-center items-center">
  <div class="bg-white/90 p-16 rounded-2xl shadow-md w-[400px] h-[400px] flex flex-col justify-center items-center">
    <h1 class="text-2xl mb-10 text-center font-bold">LOGIN</h1>

    <input type="email" bind:value={email} placeholder="Email" class="mb-2 w-full p-2 border rounded" />

    <input type="password" bind:value={password} placeholder="Senha" class=" w-full p-2 border rounded" />
    
    {#if error}
      <p class="text-red-500 mb-2">{error}</p>
    {/if}

    <div class="flex justify-between">
      <button onclick={forgetpassword} class="text-black my-5">Esqueceu a senha</button>
      </div>
    <div class="flex justify-between">
      <button onclick={handleLogin} class="bg-green-500 text-white px-4 py-2 rounded-md">Login</button>
      <button onclick={signup} class="bg-gray-200 px-4 py-2">Cadastrar-se</button>
    </div>
  </div>
</div>
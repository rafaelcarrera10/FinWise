<script lang="ts">
  import { goto } from '$app/navigation';
  import { UserAPI } from '$lib/api/user';
  import { StoreUser } from '$lib/stores/userStore';

  let email = "";
  let password = "";
  let role = "";
  let error = "";

  async function findRoleByEmail() {
    try {
      const response = await UserAPI.getByEmail(email);
      if (response?.role) {
        role = response.role; // "aluno", "professor" ou "admin"
      } else {
        error = "Tipo de conta não encontrado";
      }
    } catch (err) {
      console.error("Erro ao buscar tipo de conta:", err);
      error = "Erro ao identificar o tipo da conta";
    }
  }

  async function handleLogin() {
    error = "";

    if (!email || !password) {
      error = "Preencha todos os campos";
      return;
    }

    try {
      if (!role) {
        await findRoleByEmail();
        if (!role) return; // sai se não encontrou o papel do usuário
      }

      const loginData = { email, password, role };
      const user = await UserAPI.login(loginData);

      if (user) {
        console.log("Usuário logado:", user);
        StoreUser.set(user); // já salva no localStorage automaticamente
        goto("/home");
      } else {
        error = "Email ou senha inválidos";
      }

    } catch (err) {
      console.error("Erro ao fazer login:", err);
      error = "Erro ao conectar ao servidor";
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
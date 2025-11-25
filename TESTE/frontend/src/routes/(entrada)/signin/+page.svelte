<script lang="ts">
  import { goto } from '$app/navigation';
  import { UserAPI } from '$lib/api/user';
  import { StoreUser } from '$lib/stores/userStore';
  import Eye from 'lucide-svelte/icons/eye';
  import EyeOff from 'lucide-svelte/icons/eye-off';


  let email = "";
  let password = "";
  let role = "";
  let error = "";

  // controle de exibição da senha
  let showPassword = false;

  async function findRoleByEmail() {
    try {
      const response = await UserAPI.getByEmail(email);
      if (response?.role) {
        role = response.role;
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
        if (!role) return;
      }

      const loginData = { email, password, role };
      const user = await UserAPI.login(loginData);

      if (user) {
        StoreUser.set(user);
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

    <!-- Campo Email -->
    <input
      type="email"
      bind:value={email}
      placeholder="Email"
      class="mb-4 w-full p-2 border rounded"
    />

    <!-- Campo Senha com botão de visualizar -->
    <div class="relative w-full mb-2">
      <input
        type={showPassword ? "text" : "password"}
        bind:value={password}
        placeholder="Senha"
        class="w-full p-2 border rounded pr-10"
      />

      <!-- Botão para visualizar senha -->
      <button
        type="button"
        class="absolute right-3 top-1/2 -translate-y-1/2 text-gray-600"
        onclick={() => (showPassword = !showPassword)}
      >
        {#if showPassword}
          <EyeOff size={20}/>
        {:else}
          <Eye size={20}/>
        {/if}
      </button>
    </div>

    {#if error}
      <p class="text-red-500 mb-2 text-sm">{error}</p>
    {/if}

    <div class="flex justify-between w-full">
      <button onclick={forgetpassword} class="text-black my-5 text-sm">
        Esqueceu a senha?
      </button>
    </div>

    <div class="flex justify-between gap-3 w-full">
      <button onclick={handleLogin} class="bg-green-500 text-white px-4 py-2 rounded-md w-1/2">
        Login
      </button>

      <button onclick={signup} class="bg-gray-200 px-4 py-2 rounded-md w-1/2">
        Cadastrar-se
      </button>
    </div>

  </div>
</div>

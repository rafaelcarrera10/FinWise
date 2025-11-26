<script lang="ts">
  import { onMount } from 'svelte'
  import { get } from 'svelte/store'
  import { StoreUser } from '$lib/stores/userStore'
  import { UsuarioAPI } from '$lib/api/usuario'
  import Eye from 'lucide-svelte/icons/eye';
  import EyeOff from 'lucide-svelte/icons/eye-off';

// Ex: src/routes/home/+page.ts
import { protectRoute } from '$lib/utils/auth';

export const load = async () => {
  protectRoute();
  return {};
};


  // -------------------- Estado --------------------
  let user: any = null

  // Campos editáveis
  let name = ''

  // Modal de editar perfil
  let showEditModal = false

  // Modal de alterar senha
  let showPasswordModal = false

  // Campos de senha
  let oldPassword = ''
  let newPassword = ''
  let confirmPassword = ''

  let passwordError = ''
  let passwordSuccess = ''

  // Estados de visibilidade da senha
  let showOld = false
  let showNew = false
  let showConfirm = false

  // -------------------- Carrega dados --------------------
  onMount(() => {
    const current = get(StoreUser)

    if (current) {
      user = current
      name = current.name ?? ""
    }
  })

  // -------------------- Atualizar nome --------------------
  async function saveChanges() {
    try {
      if (name !== user.name) {
        await UsuarioAPI.updateName(user.id, name)
        user.name = name
      }

      StoreUser.set(user)
      showEditModal = false
    } catch (err) {
      alert("Erro ao atualizar perfil")
    }
  }

  // -------------------- Alterar senha --------------------
  async function changePassword() {
  passwordError = '';
  passwordSuccess = '';

  // Validação de campos vazios
  if (!oldPassword || !newPassword || !confirmPassword) {
    passwordError = "Preencha todos os campos.";
    return;
  }

  // Validação de senhas iguais
  if (newPassword !== confirmPassword) {
    passwordError = "As senhas novas não coincidem.";
    return;
  }

  try {
    // Chamada correta da API (lembre-se: é 'updatepassword', tudo minúsculo)
    const result = await UsuarioAPI.updatepassword(
      user.id,
      oldPassword,
      newPassword
    );

    // Sucesso
    passwordSuccess = "Senha alterada com sucesso!";
    oldPassword = newPassword = confirmPassword = "";

    setTimeout(() => {
      showPasswordModal = false;
      passwordSuccess = '';
    }, 1500);

  } catch (err) {
    // Erro de senha antiga incorreta
    passwordError = "Senha antiga incorreta.";
  }
}

</script>


<!-- ===================== Layout ===================== -->
<div class="min-h-screen flex flex-col items-center p-6 text-white">

  <div class="w-full max-w-4xl bg-slate-800/60 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 p-8 space-y-8">

    <!-- Título -->
    <div class="flex items-center justify-between">
      <h1 class="text-3xl font-semibold">Perfil do Usuário</h1>

      <button
        onclick={() => (showEditModal = true)}
        class="bg-green-600 hover:bg-green-700 px-4 py-2 rounded-lg font-medium transition"
      >
        Editar Perfil
      </button>
    </div>


    <!-- ===================== Dados do usuário ===================== -->
    <div class="space-y-6">

      <!-- Foto e nome -->
      <div class="flex flex-col items-center gap-4">
        <div class="w-28 h-28 rounded-full bg-slate-700 flex items-center justify-center text-5xl shadow-inner">
          {user?.name?.[0] ?? "?"}
        </div>
        <h2 class="text-2xl font-semibold">{user?.name ?? "Usuário"}</h2>
      </div>

      <!-- Informações pessoais -->
      <div class="bg-slate-900/40 p-6 rounded-xl shadow-inner">
        <h3 class="text-xl font-semibold mb-4">Informações Pessoais</h3>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <p class="text-gray-400 text-sm">Nome</p>
            <p class="text-lg font-medium">{user?.name ?? "-"}</p>
          </div>

          <div>
            <p class="text-gray-400 text-sm">Cargo / Função</p>
            <p class="text-lg font-medium">{user?.role ?? "Usuário"}</p>
          </div>
        </div>
      </div>

      <!-- Segurança -->
      <div class="bg-slate-900/40 p-6 rounded-xl shadow-inner">
        <h3 class="text-xl font-semibold mb-4">Segurança</h3>

        <div class="flex flex-col gap-4">
          <button
            class="bg-blue-600 hover:bg-blue-700 w-full py-3 rounded-lg transition"
            onclick={() => (showPasswordModal = true)}
          >
            Alterar Senha
          </button>

          <button class="bg-red-600 hover:bg-red-700 w-full py-3 rounded-lg transition">
            Encerrar Sessões Ativas
          </button>
        </div>
      </div>

    </div>
  </div>


  <!-- ===================== Modal de edição ===================== -->
  {#if showEditModal}
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">

      <div class="bg-white text-black p-6 w-96 rounded-xl shadow-xl space-y-4">

        <h2 class="text-xl font-semibold">Editar Perfil</h2>

        <div class="flex flex-col gap-3">

          <input
            type="text"
            bind:value={name}
            placeholder="Nome completo"
            class="border border-gray-300 p-2 rounded-lg focus:ring-2 focus:ring-green-500"
          />

        </div>

        <div class="flex justify-end gap-3 pt-2">
          <button
            onclick={() => (showEditModal = false)}
            class="px-3 py-1 rounded-lg bg-gray-300 hover:bg-gray-400"
          >
            Cancelar
          </button>

          <button
            onclick={saveChanges}
            class="px-3 py-1 rounded-lg bg-green-600 text-white hover:bg-green-700"
          >
            Salvar
          </button>
        </div>

      </div>
    </div>
  {/if}


  <!-- ===================== Modal Alterar Senha ===================== -->
  {#if showPasswordModal}
    <div class="fixed inset-0 bg-black/60 flex items-center justify-center z-50">

      <div class="bg-white text-black p-6 w-96 rounded-xl shadow-xl space-y-4">

        <h2 class="text-xl font-semibold">Alterar Senha</h2>

        <div class="flex flex-col gap-3">

          <!-- Senha atual -->
          <div class="relative">
            <input
              type={showOld ? "text" : "password"}
              bind:value={oldPassword}
              placeholder="Senha atual"
              class="border border-gray-300 p-2 rounded-lg w-full focus:ring-2 focus:ring-blue-500"
            />
            <button
              type="button"
              class="absolute right-2 top-2"
              onclick={() => showOld = !showOld}
            >
              {#if showOld}
                <EyeOff size="20" />
              {:else}
                <Eye size="20" />
              {/if}
            </button>
          </div>

          <!-- Nova senha -->
          <div class="relative">
            <input
              type={showNew ? "text" : "password"}
              bind:value={newPassword}
              placeholder="Nova senha"
              class="border border-gray-300 p-2 rounded-lg w-full focus:ring-2 focus:ring-blue-500"
            />
            <button
              type="button"
              class="absolute right-2 top-2"
              onclick={() => showNew = !showNew}
            >
              {#if showNew}
                <EyeOff size="20" />
              {:else}
                <Eye size="20" />
              {/if}
            </button>
          </div>

          <!-- Confirmar nova senha -->
          <div class="relative">
            <input
              type={showConfirm ? "text" : "password"}
              bind:value={confirmPassword}
              placeholder="Confirmar nova senha"
              class="border border-gray-300 p-2 rounded-lg w-full focus:ring-2 focus:ring-blue-500"
            />
            <button
              type="button"
              class="absolute right-2 top-2"
              onclick={() => showConfirm = !showConfirm}
            >
              {#if showConfirm}
                <EyeOff size="20" />
              {:else}
                <Eye size="20" />
              {/if}
            </button>
          </div>

          {#if passwordError}
            <p class="text-red-600 text-sm">{passwordError}</p>
          {/if}

          {#if passwordSuccess}
            <p class="text-green-600 text-sm">{passwordSuccess}</p>
          {/if}

        </div>

        <div class="flex justify-end gap-3 pt-2">
          <button
            onclick={() => (showPasswordModal = false)}
            class="px-3 py-1 rounded-lg bg-gray-300 hover:bg-gray-400"
          >
            Cancelar
          </button>

          <button
            onclick={changePassword}
            class="px-3 py-1 rounded-lg bg-blue-600 text-white hover:bg-blue-700"
          >
            Alterar
          </button>
        </div>

      </div>
    </div>
  {/if}
</div>
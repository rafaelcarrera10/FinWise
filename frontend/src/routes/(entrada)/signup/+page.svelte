<script lang="ts">
    
import { goto } from '$app/navigation';
import { UserAPI } from '$lib/api/user'; 
import { writable } from 'svelte/store';

// Estados reativos
let name = "";
let email = "";
let password = "";
let role: "aluno" | "professor" = "aluno";
let error = "";

// Função para criar conta
async function createAccount() {
    error = "";
    try {
        const newUser = {
            role,
            name: name,
            email: email,
            password: password
        };
        const createdUser = await UserAPI.create(newUser);
        if (createdUser) {
            goto('/signin');
        }
    } catch (err) {
        console.error(err);
        console.log(err);
    }
}

async function signin() {
  goto("/signin")
}


</script>


<div class="absolute z-10 w-screen h-screen flex justify-center items-center">
  <div class="bg-white/90 p-16 rounded-2xl shadow-md w-[400px] h-[400px] flex flex-col justify-center items-center">
    <h1 class="text-2xl mb-10 text-center font-bold">CADASTRO</h1>

    <div class="flex justify-center gap-4 mb-5">
      <h2>Tipo da conta</h2>
    <label>
      <input type="radio" name="role" value="aluno" bind:group={role} />
      Aluno
    </label>
    <label>
      <input type="radio" name="role" value="professor" bind:group={role} />
      Professor
    </label>
    </div>

    <input type="name" bind:value={name} placeholder="Nome" class="mb-2 w-full p-2 border rounded" />

    <input type="email" bind:value={email} placeholder="Email" class="mb-2 w-full p-2 border rounded" />

    <input type="password" bind:value={password} placeholder="Senha" class="mb-8 w-full p-2 border rounded" />
    
    {#if error}
      <p class="text-red-500 mb-2">{error}</p>
    {/if}
    <div class="flex justify-between">
      <button onclick={createAccount} class="bg-green-500 text-white px-4 py-2 rounded-md">Cadastrar-se</button>
      <button onclick={signin} class="bg-gray-200 px-4 py-2">Voltar</button>
    </div>
  </div>
</div>
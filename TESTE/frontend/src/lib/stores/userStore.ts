import { writable } from 'svelte/store';
import { browser } from '$app/environment';

// Cria a store de usuário com persistência em sessionStorage
function createUserStore() {
  const storedUser = browser ? sessionStorage.getItem('user') : null;
  const initialUser = storedUser ? JSON.parse(storedUser) : null;

  const { subscribe, set } = writable(initialUser);

  if (browser) {
    subscribe((value) => {
      if (value) {
        sessionStorage.setItem('user', JSON.stringify(value));
      } else {
        sessionStorage.removeItem('user');
      }
    });
  }

  return {
    subscribe,
    set,
    clear: () => set(null)
  };
}

export const StoreUser = createUserStore();


// =============================
// FUNÇÃO LOGIN CORRIGIDA
// =============================

export async function login(email: string, password: string) {
  if (!browser) return null; // evita erros no SSR

  try {
    const response = await fetch("http://localhost:8080/users/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, password }),
    });

    if (!response.ok) {
      throw new Error("Falha ao realizar login");
    }

    const userData = await response.json();
    console.log("Usuário logado:", userData.role);

    // Salva na store (e automaticamente no sessionStorage)
    StoreUser.set(userData);

    return userData;

  } catch (error) {
    console.error("Erro no login:", error);
    throw error;
  }
}

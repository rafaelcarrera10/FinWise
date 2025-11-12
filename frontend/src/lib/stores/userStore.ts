import { writable } from 'svelte/store';
import { browser } from '$app/environment';

function createUserStore() {
  // Só acessa o localStorage se estiver no navegador
  const storedUser = browser ? localStorage.getItem('user') : null;
  const initialUser = storedUser ? JSON.parse(storedUser) : null;

  const { subscribe, set } = writable(initialUser);

  // Atualiza o localStorage apenas no cliente
  if (browser) {
    subscribe((value) => {
      if (value) {
        localStorage.setItem('user', JSON.stringify(value));
      } else {
        localStorage.removeItem('user');
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

import type { Handle } from '@sveltejs/kit';
import { goto } from '$app/navigation';
import { get } from 'svelte/store';
import { StoreUser } from '$lib/stores/userStore';

const publicRoutes = ['/signin', '/signup'];

export const handle: Handle = async ({ event, resolve }) => {
  const path = event.url.pathname;
  const user = get(StoreUser);

  if (!user && !publicRoutes.includes(path)) {
    goto('/signin');
    return new Response(); // impede renderização da rota protegida
  }

  return await resolve(event);
};

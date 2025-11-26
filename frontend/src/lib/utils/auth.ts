import { get } from 'svelte/store';
import { StoreUser } from '$lib/stores/userStore';
import { redirect } from '@sveltejs/kit';

export function protectRoute() {
  const user = get(StoreUser);

  if (!user) {
    throw redirect(302, '/signin');
  }
}

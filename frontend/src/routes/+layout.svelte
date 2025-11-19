{@render children()}
<script lang="ts">
	import '../app.css';
	import favicon from '$lib/assets/favicon.svg';
	import { startSessionWatcher } from "$lib/stores/sessionWatcher";

	const { children } = $props();

  if (typeof window !== "undefined") {
    startSessionWatcher();
  }
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
</svelte:head>




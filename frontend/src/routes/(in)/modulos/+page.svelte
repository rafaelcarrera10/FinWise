<script lang="ts">
  import { onMount } from "svelte";
  import type { Video } from "$lib/api/video";
  import type { PublicacaoDTO } from "$lib/api/publicacao";

  let videos: Video[] = [];
  let publicacoes: PublicacaoDTO[] = [];

  let selectedVideo: Video | null = null;
  let selectedPublicacao: PublicacaoDTO | null = null;

  function toEmbed(url: string) {
    if (!url) return "";
    if (url.includes("embed")) return url;

    const normal = url.match(/v=([^&]+)/);
    if (normal) return `https://www.youtube.com/embed/${normal[1]}`;

    const short = url.match(/youtu\.be\/([^?]+)/);
    if (short) return `https://www.youtube.com/embed/${short[1]}`;

    return url;
  }

  function handleKey(e: KeyboardEvent) {
    if (e.key === "Escape") {
      selectedVideo = null;
      selectedPublicacao = null;
    }
  }

  async function loadData() {
    try {
      const resVideos = await fetch("http://localhost:8080/videos/all");
      videos = await resVideos.json();

      const resPubs = await fetch("http://localhost:8080/publicacoes/all");
      publicacoes = await resPubs.json();
    } catch (err) {
      console.error("Erro ao carregar conteúdos:", err);
    }
  }

  onMount(() => {
    window.addEventListener("keydown", handleKey);
    loadData();

    return () => {
      window.removeEventListener("keydown", handleKey);
    };
  });
</script>

<!-- CONTAINER PRINCIPAL -->
<div class="min-h-screen flex flex-col items-center justify-center p-6 text-white">
  <div class="w-full max-w-6xl bg-slate-800/60 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 p-8 space-y-10">

    <!-- VÍDEOS -->
    <div>
      <h1 class="text-3xl font-bold mb-6">Vídeo Aulas</h1>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <!-- svelte-ignore a11y_click_events_have_key_events -->
        <!-- svelte-ignore a11y_click_events_have_key_events -->
        <!-- svelte-ignore a11y_click_events_have_key_events -->
        {#each videos as v}
          <!-- svelte-ignore a11y_no_static_element_interactions -->
          <div
            class="bg-slate-900/40 p-4 rounded-xl shadow-inner cursor-pointer hover:scale-[1.02] transition space-y-3"
            on:click={() => (selectedVideo = v)}
          >
              <!-- svelte-ignore a11y_missing_attribute -->
            <div class="relative w-full h-48 overflow-hidden rounded-lg">
              <iframe
                src={toEmbed(v.videoUrl) + "?controls=0&disablekb=1&modestbranding=1"}
                class="absolute inset-0 w-full h-full opacity-60 pointer-events-none"
              ></iframe>

              <div class="absolute inset-0 flex items-center justify-center">
                <span class="bg-black/60 text-white text-sm px-3 py-1 rounded-lg">
                  Prévia
                </span>
              </div>
            </div>

            <h2 class="text-xl font-semibold">{v.titulo}</h2>
            <p class="text-blue-300 underline">Acesse aqui para assistir</p>
          </div>
        {/each}

        {#if videos.length === 0}
          <p class="text-gray-300">Nenhum vídeo encontrado.</p>
        {/if}
      </div>
    </div>

    <!-- PUBLICAÇÕES -->
    <div>
      <h1 class="text-3xl font-bold mt-6 mb-6">Publicações</h1>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">

        {#each publicacoes as p}
  <button
    type="button"
    class="bg-slate-900/40 p-4 rounded-xl shadow-inner space-y-3 cursor-pointer 
           hover:scale-[1.02] transition w-full text-left"
    on:click={() => (selectedPublicacao = p)}
  >

    {#if p.fotos && p.fotos.length > 0}
      <img
        src={"data:image/jpeg;base64," + p.fotos[0]}
        class="w-full h-48 object-cover rounded-lg"
        alt="Imagem da publicação"
      />
    {/if}

    <h2 class="text-xl font-semibold">{p.titulo}</h2>

    <p class="text-blue-300 underline">Acessar</p>

  </button>
{/each}

        {#if publicacoes.length === 0}
          <p class="text-gray-300">Nenhuma publicação encontrada.</p>
        {/if}

      </div>
    </div>

  </div>
</div>

<!-- MODAL DO VÍDEO -->
<!-- svelte-ignore a11y_click_events_have_key_events -->
{#if selectedVideo}
  <!-- svelte-ignore a11y_no_static_element_interactions -->
  <div
    class="fixed inset-0 bg-black/70 backdrop-blur-sm flex items-center justify-center z-50"
    on:click={() => (selectedVideo = null)}
  >
    <div
      class="bg-white p-6 rounded-xl w-11/12 max-w-3xl shadow-xl relative"
      on:click|stopPropagation
    >
      <h2 class="text-2xl font-bold mb-4">{selectedVideo.titulo}</h2>

      <!-- svelte-ignore a11y_missing_attribute -->
      <iframe
        src={toEmbed(selectedVideo.videoUrl)}
        class="w-full h-72 rounded-lg mb-4"
        allowfullscreen
      ></iframe>

      <p class="text-gray-600 mb-4">{selectedVideo.descricao}</p>

      <button
        class="absolute top-3 right-4 text-xl font-bold text-gray-500 hover:text-black"
        on:click={() => (selectedVideo = null)}
      > x
      </button>
    </div>
  </div>
{/if}

<!-- MODAL DA PUBLICAÇÃO -->
<!-- svelte-ignore a11y_click_events_have_key_events -->
{#if selectedPublicacao}
  <!-- svelte-ignore a11y_no_static_element_interactions -->
  <div
    class="fixed inset-0 bg-black/70 backdrop-blur-sm flex items-center justify-center z-50"
    on:click={() => (selectedPublicacao = null)}
  >
    <div
      class="bg-white p-6 rounded-xl w-11/12 max-w-3xl shadow-xl relative text-black"
      on:click|stopPropagation
    >
      <h2 class="text-3xl font-bold mb-4">{selectedPublicacao.titulo}</h2>

      <p class="mb-4 whitespace-pre-line">{selectedPublicacao.texto}</p>

      {#if selectedPublicacao.fotos && selectedPublicacao.fotos.length > 0}
        <!-- svelte-ignore a11y_missing_attribute -->
        <img
          src={"data:image/jpeg;base64," + selectedPublicacao.fotos[0]}
          class="w-full rounded-lg mb-4"
        />
      {/if}

      <button
        class="absolute top-3 right-4 text-xl font-bold text-gray-500 hover:text-black"
        on:click={() => (selectedPublicacao = null)}
      >
        ×
      </button>
    </div>
  </div>
{/if}

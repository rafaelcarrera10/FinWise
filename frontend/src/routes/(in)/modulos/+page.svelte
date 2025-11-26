<script lang="ts">
  import { onMount } from "svelte";
  import VideoIcon from "lucide-svelte/icons/video";
  import X from "lucide-svelte/icons/x";

  import { VideoAPI, type Video } from '$lib/api/video';
  import { TagAPI, type Tag } from '$lib/api/tag';

  // Ex: src/routes/home/+page.ts
import { protectRoute } from '$lib/utils/auth';

export const load = async () => {
  protectRoute();
  return {};
};


  // Estrutura da tela
  interface TagComVideos {
    tag: Tag;     
    videos: Video[];
  }

  let tagsComVideos: TagComVideos[] = [];
  let loading = true;
  let selectedVideo: Video | null = null;

  function openVideo(video: Video) {
    selectedVideo = video;
  }

  function closeVideo() {
    selectedVideo = null;
  }

  // Busca categorias + vídeos ao carregar
  onMount(async () => {
    try {
      const categorias: Tag[] = await TagAPI.getAll();

      const carregadas: TagComVideos[] = [];

      for (const cat of categorias) {
        const videos: Video[] = await VideoAPI.getByTag(cat); // cat é string literal
        carregadas.push({ tag: cat, videos });
      }

      tagsComVideos = carregadas;
    } catch (err) {
      console.error("Erro carregando categorias/vídeos:", err);
    } finally {
      loading = false;
    }
  });
</script>

<div class="min-h-screen p-8 text-white flex flex-col items-center">
  <div class="w-full max-w-6xl bg-slate-800/60 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 p-8 space-y-8">

    <h1 class="text-3xl font-semibold text-center">Educação Financeira</h1>

    {#if loading}
      <p class="text-center text-gray-300">Carregando conteúdo...</p>
    {/if}

    {#each tagsComVideos as bloco}
      <section class="space-y-4">

        <h2 class="text-2xl font-bold border-b border-slate-700 pb-2">
          {bloco.tag} <!-- usa a string diretamente -->
        </h2>

        <!-- CARROSSEL -->
        <div class="flex gap-6 overflow-x-auto pb-4" style="scroll-snap-type: x mandatory;">
          
          {#each bloco.videos as video}
            <!-- svelte-ignore a11y_no_static_element_interactions -->
            <!-- svelte-ignore a11y_click_events_have_key_events -->
            <div
              class="min-w-[260px] bg-slate-900/40 p-4 rounded-xl shadow-inner cursor-pointer hover:bg-slate-900/60 transition"
              style="scroll-snap-align: start;"
              on:click={() => openVideo(video)}
            >
              <div class="aspect-video bg-black/40 rounded-lg mb-3 flex items-center justify-center text-slate-400">
                <VideoIcon size="48" />
              </div>

              <h3 class="text-lg font-semibold">{video.titulo}</h3>

              <p class="text-gray-400 text-sm mt-1">
                Clique para abrir a aula
              </p>
            </div>
          {/each}

          <div class="min-w-[20px]"></div>
        </div>
      </section>
    {/each}
  </div>

  <!-- MODAL -->
  {#if selectedVideo}
    <div class="fixed inset-0 bg-black/70 flex items-center justify-center z-50 p-4">
      <div class="bg-white text-black rounded-xl shadow-xl p-6 max-w-3xl w-full space-y-4 relative">

        <button
          on:click={closeVideo}
          class="absolute right-4 top-4 text-black hover:text-red-600"
        >
          <X size="22" />
        </button>

        <h2 class="text-2xl font-bold">{selectedVideo.titulo}</h2>

        <div class="w-full aspect-video">
          <!-- svelte-ignore a11y_missing_attribute -->
          <iframe
            src={selectedVideo.video}
            class="w-full h-full rounded-lg border border-gray-300"
            allowfullscreen
          ></iframe>
        </div>

        <p class="text-gray-700">{selectedVideo.descricao}</p>

        <div class="flex justify-end">
          <button
            on:click={closeVideo}
            class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition"
          >
            Fechar
          </button>
        </div>

      </div>
    </div>
  {/if}
</div>

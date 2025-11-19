<script lang="ts">
  import { Video, X } from 'lucide-svelte'

  interface Lesson {
    id: number
    title: string
    videoUrl: string
    description: string
    extraContent: string
  }

  interface ModuleCategory {
    title: string
    lessons: Lesson[]
  }

  // ------------------ Conteúdo de Exemplo ------------------ //
  let categories: ModuleCategory[] = [
    {
      title: "Fundamentos da Educação Financeira",
      lessons: [
        {
          id: 1,
          title: "O que é Educação Financeira?",
          videoUrl: "https://www.youtube.com/embed/BBz-Jyr23M4",
          description: "Introdução aos conceitos básicos de educação financeira.",
          extraContent: "PDF introdutório, slides e exercícios complementares."
        },
        {
          id: 2,
          title: "Orçamento Pessoal",
          videoUrl: "https://www.youtube.com/embed/mA4jW4E4blA",
          description: "Como organizar seu dinheiro mensalmente.",
          extraContent: "Planilha editável e infográfico."
        }
      ]
    },

    {
      title: "Investimentos",
      lessons: [
        {
          id: 3,
          title: "Introdução aos Investimentos",
          videoUrl: "https://www.youtube.com/embed/zE4k41Z1C2I",
          description: "Principais conceitos e tipos de investimentos.",
          extraContent: "Resumo da aula e materiais extras."
        },
        {
          id: 4,
          title: "Renda Fixa x Variável",
          videoUrl: "https://www.youtube.com/embed/QfQTH6ZbHpk",
          description: "Diferenças e aplicações de cada tipo.",
          extraContent: "Exemplos práticos e simulações."
        }
      ]
    },

    {
      title: "Consumo Consciente",
      lessons: [
        {
          id: 5,
          title: "Como evitar gastos desnecessários",
          videoUrl: "https://www.youtube.com/embed/ZOI4D2PfI7Y",
          description: "Estratégias práticas para gastar melhor.",
          extraContent: "Guia prático para download."
        }
      ]
    }
  ]

  // ------------------ Controle do modal ------------------ //
  let selectedLesson: Lesson | null = null

  function openLesson(lesson: Lesson) {
    selectedLesson = lesson
  }

  function closeLesson() {
    selectedLesson = null
  }
</script>

<div class="min-h-screen p-8 text-white flex flex-col items-center">
  <div class="w-full max-w-6xl bg-slate-800/60 backdrop-blur-md rounded-2xl shadow-2xl border border-slate-700 p-8 space-y-8">

    <h1 class="text-3xl font-semibold text-center">Educação Financeira</h1>

    <!-- ---------------------- LISTA DE CATEGORIAS ---------------------- -->
    {#each categories as category}
      <section class="space-y-4">
        
        <h2 class="text-2xl font-bold border-b border-slate-700 pb-2">
          {category.title}
        </h2>

        <!-- CARROSSEL DE AULAS -->
        <div
          class="flex gap-6 overflow-x-auto pb-4"
          style="scroll-snap-type: x mandatory;"
        >
          {#each category.lessons as lesson}
            <!-- svelte-ignore a11y_click_events_have_key_events -->
            <!-- svelte-ignore a11y_no_static_element_interactions -->
            <div
              class="min-w-[260px] bg-slate-900/40 p-4 rounded-xl shadow-inner cursor-pointer hover:bg-slate-900/60 transition"
              style="scroll-snap-align: start;"
              onclick={() => openLesson(lesson)}
            >
              <div class="aspect-video bg-black/40 rounded-lg mb-3 flex items-center justify-center text-slate-400">
                <Video size="48" />
              </div>

              <h3 class="text-lg font-semibold">{lesson.title}</h3>

              <p class="text-gray-400 text-sm mt-1">
                Clique para abrir a aula
              </p>
            </div>
          {/each}

          <!-- Espaço extra no final para respiro -->
          <div class="min-w-[20px]"></div>
        </div>
      </section>
    {/each}
  </div>

  <!-- ------------------------- MODAL DA AULA ------------------------- -->
  {#if selectedLesson}
    <div class="fixed inset-0 bg-black/70 flex items-center justify-center z-50 p-4">
      
      <div class="bg-white text-black rounded-xl shadow-xl p-6 max-w-3xl w-full space-y-4 relative">

        <!-- Botão fechar -->
        <button
          onclick={closeLesson}
          class="absolute right-4 top-4 text-black hover:text-red-600"
        >
          <X size="22" />
        </button>

        <h2 class="text-2xl font-bold">{selectedLesson.title}</h2>

        <!-- PLAYER DE VÍDEO -->
        <div class="w-full aspect-video">
          <!-- svelte-ignore a11y_missing_attribute -->
          <iframe
            src={selectedLesson.videoUrl}
            class="w-full h-full rounded-lg border border-gray-300"
            allowfullscreen
          ></iframe>
        </div>

        <!-- DESCRIÇÃO -->
        <p class="text-gray-700">{selectedLesson.description}</p>

        <!-- CONTEÚDO EXTRA -->
        <div class="bg-gray-100 p-4 rounded-lg">
          <h3 class="text-lg font-semibold mb-2">Conteúdo Extra</h3>
          <p class="text-gray-800 whitespace-pre-line">
            {selectedLesson.extraContent}
          </p>
        </div>

        <div class="flex justify-end">
          <button
            onclick={closeLesson}
            class="bg-blue-600 text-white px-4 py-2 rounded-lg hover:bg-blue-700 transition"
          >
            Fechar
          </button>
        </div>

      </div>
    </div>
  {/if}
</div>

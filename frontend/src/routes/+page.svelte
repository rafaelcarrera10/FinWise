<script lang="ts">
  let gire = $state(false);
</script>

<div
  class="absolute z-0 w-screen h-screen flex justify-center items-center bg-black overflow-hidden"
>
  <div class="flex w-full h-full p-4 justify-between animate-spin-slow">
    <div class="h-full flex items-end">
      <!-- svelte-ignore element_invalid_self_closing_tag -->
      <div
        class="size-64 blur-2xl bg-gradient-to-r from-green-200 to-green-800 rounded-full"
      />
    </div>
    <div class="h-full flex items-start">
      <!-- svelte-ignore element_invalid_self_closing_tag -->
      <div
        class="size-64 blur-2xl bg-gradient-to-r from-green-200 to-green-800 rounded-full"
      />
    </div>
  </div>
</div>

<div class="w-screen h-screen z-1"></div>

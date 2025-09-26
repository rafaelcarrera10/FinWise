<script>
  import LoginForm from './Login/LoginForm.svelte';
</script>

<style>
  body, html {
    margin: 0;
    padding: 0;
    height: 100%;
    font-family: sans-serif;
    background: radial-gradient(circle at top left, #004d40, #000000);
    overflow: hidden;
  }

  .container {
    position: relative;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>

<div class="container">
  <LoginForm />
</div>
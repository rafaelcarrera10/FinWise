<script>
  import { onMount } from 'svelte';
  let email = '';
  let password = '';
  let balls = [];

  const BALL_COUNT = 6;   
  let radius = 150;     
  let angle = 0;

  function updateBalls() {
    angle += 0.02;
    balls.forEach((ball, index) => {
      const a = angle + (index * (2 * Math.PI / BALL_COUNT));
      ball.x = radius * Math.cos(a);
      ball.y = radius * Math.sin(a);
    });
    requestAnimationFrame(updateBalls);
  }

  onMount(() => {
    // cria bolas com propriedades extras
    for (let i = 0; i < BALL_COUNT; i++) {
      balls.push({
        id: i,
        x: 0,
        y: 0,
        size: 10 + Math.random() * 10,           // tamanho variável
        color: `hsl(${Math.random() * 360}, 100%, 50%)` // cor aleatória
      });
    }

    // define o raio proporcional à tela
    radius = Math.min(window.innerWidth, window.innerHeight) / 4;

    updateBalls();
  });

  async function login() {
    try {
      const response = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password }),
      });
      if (response.ok) alert('Login realizado com sucesso!');
      else alert('Credenciais inválidas.');
    } catch (err) {
      console.error(err);
      alert('Erro de conexão com o servidor.');
    }
  }
</script>

<style>
  :global(body) {
    margin: 0;
    background-color: #000;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    overflow: hidden;
    font-family: sans-serif;
  }

  .login-wrapper {
    position: relative;
    width: 300px;
    padding: 40px;
    background: rgba(0,0,0,0.6);
    border-radius: 12px;
    text-align: center;
    z-index: 1;
  }

  .login-wrapper input {
    display: block;
    width: 100%;
    padding: 10px;
    margin: 10px 0;
    border-radius: 6px;
    border: 1px solid #ccc;
    outline: none;
    background: transparent;
    color: #fff;
  }

  .login-wrapper button {
    width: 100%;
    padding: 10px;
    background: #00e676;
    border: none;
    color: #000;
    font-weight: bold;
    border-radius: 6px;
    cursor: pointer;
    transition: 0.3s;
  }

  .login-wrapper button:hover {
    background: #00c853;
  }

  .login-wrapper a {
    color: #ccc;
    font-size: 0.9rem;
    text-decoration: none;
  }

  .ball {
    position: absolute;
    border-radius: 50%;
    top: 50%;
    left: 50%;
    pointer-events: none;
    box-shadow: 0 0 10px #0f0;
    z-index: 0;
  }
</style>

<div class="login-wrapper">
  <h2>LOGIN</h2>
  <input type="email" placeholder="E-mail" bind:value={email} />
  <input type="password" placeholder="Senha" bind:value={password} />
  <button on:click={login}>Entrar</button>
  <p><a href="$/resetpassword">Esqueci a senha</a></p>
  <p><a href="$/register">Cadastrar-se</a></p>
</div>

{#each balls as ball (ball.id)}
  <div
    class="ball"
    style="width:{ball.size}px; height:{ball.size}px; background:{ball.color}; transform: translate(calc(-50% + {ball.x}px), calc(-50% + {ball.y}px));">
  </div>
{/each}

import { StoreUser } from "./userStore";

let timeout: any;

// tempo de inatividade = 20 minutos
const INACTIVITY_LIMIT = 1000 * 60 * 20;

function logout() {
  StoreUser.set(null);
  window.location.href = "/signin"; // redireciona pro login
}

function resetTimer() {
  clearTimeout(timeout);
  timeout = setTimeout(logout, INACTIVITY_LIMIT);
}

export function startSessionWatcher() {
  // reseta o timer toda vez que o usuário fizer algo
  window.addEventListener("mousemove", resetTimer);
  window.addEventListener("keydown", resetTimer);
  window.addEventListener("click", resetTimer);
  window.addEventListener("scroll", resetTimer);

  resetTimer(); // inicia o timer ao entrar no site
}

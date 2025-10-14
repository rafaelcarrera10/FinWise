const BASE_URL = import.meta.env.VITE_API_BASE_URL + '/accounts';

// Função genérica de requisição
async function request(endpoint, options = {}) {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: { 'Content-Type': 'application/json' },
      ...options
    });

    if (!response.ok) {
      throw new Error(`Erro ${response.status}: ${response.statusText}`);
    }

    // Alguns endpoints podem não ter corpo (204)
    return response.status !== 204 ? await response.json() : null;

  } catch (error) {
    console.error('Erro ao conectar ao backend:', error);
    throw error;
  }
}

export const AccountAPI = {
  // Criar nova conta
  create: (account) =>
    request('/create', {
      method: 'POST',
      body: JSON.stringify(account)
    }),

  // Buscar conta por número
  getByNumber: (account) =>
    request('/by-number', {
      method: 'GET',
      body: JSON.stringify(account)
    }),

  // Buscar contas com saldo maior que X
  getByBalanceGreaterThan: (min) =>
    request('/balance-greater-than', {
      method: 'GET',
      body: JSON.stringify(min)
    }),

  // Buscar contas com saldo entre valores
  getByBalanceBetween: (min, max) =>
    request(`/balance-between?min=${min}&max=${max}`, { method: 'GET' }),

  // Buscar todas ordenadas
  getAllByBalanceDesc: () => request('/order-by-balance-desc', { method: 'GET' }),
  getAllByBalanceAsc: () => request('/order-by-balance-asc', { method: 'GET' }),

  // Contar contas
  count: () => request('', { method: 'GET' }),

  // Atualizações
  updateBalance: (id, newBalance) =>
    request(`/update-balance?id=${id}&newBalance=${newBalance}`, { method: 'POST' }),

  updateNumber: (id, newNumber) =>
    request(`/update-number?id=${id}&newNumber=${newNumber}`, { method: 'POST' }),

  // Deletar
  deleteByNumber: (number) =>
    request(`/delete-by-number?number=${number}`, { method: 'POST' }),

  deleteByZeroBalance: () =>
    request('/delete-by-zero-balance', { method: 'POST' }),

  deleteByNegativeBalance: () =>
    request('/delete-by-negative-balance', { method: 'POST' }),

  // Estatísticas
  getTotalBalance: (userId) =>
    request(`/total-balance?userId=${userId}`, { method: 'GET' }),

  getMaxBalance: (userId) =>
    request(`/max-balance?userId=${userId}`, { method: 'GET' })
};

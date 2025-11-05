// src/api/account.ts
let BASE_URL = 'http://localhost:8080/accounts';

// Tipos
export type Account = {
  id?: number;
  number: string;
  balance: number;
  userId?: number;
  [key: string]: any; // outros campos opcionais
};

// Função genérica de requisição
async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: { 'Content-Type': 'application/json' },
      ...options,
    });

    // Se o status não for OK, lança erro
    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Erro ${response.status}: ${errorText}`);
    }

    // Trata respostas sem corpo (ex: 200 OK ou 204 No Content)
    const text = await response.text();
    return text ? (JSON.parse(text) as T) : null;
  } catch (error) {
    console.error('Erro ao conectar ao backend:', error);
    throw error;
  }
}

export const AccountAPI = {
  // Criar nova conta
  create: (account: Account) =>
    request<Account>('/create', {
      method: 'POST',
      body: JSON.stringify(account),
    }),

  // Buscar por número
  getByNumber: (number: string) =>
    request<Account>(`/by-number?number=${encodeURIComponent(number)}`, { method: 'GET' }),

  // Buscar por saldo maior que X
  getByBalanceGreaterThan: (min: number) =>
    request<Account[]>(`/balance-greater-than?min=${min}`, { method: 'GET' }),

  // Buscar por saldo entre dois valores
  getByBalanceBetween: (min: number, max: number) =>
    request<Account[]>(`/balance-between?min=${min}&max=${max}`, { method: 'GET' }),

  // Buscar todas ordenadas por saldo
  getAllByBalanceDesc: () => request<Account[]>('/order-by-balance-desc', { method: 'GET' }),
  getAllByBalanceAsc: () => request<Account[]>('/order-by-balance-asc', { method: 'GET' }),

  // Contagem total de contas
  count: () => request<number>('', { method: 'GET' }),

  // Atualizar saldo
  updateBalance: (id: number, newBalance: number) =>
    request<void>(`/update-balance?id=${id}&newBalance=${newBalance}`, { method: 'POST' }),

  // Atualizar número (corrigido para usar number)
  updateNumber: (id: number, newNumber: number) =>
    request<void>(`/update-number?id=${id}&newNumber=${newNumber}`, { method: 'POST' }),

  // Adicionar valor ao saldo
  addToBalance: (id: number, amount: number) =>
    request<void>(`/add-to-balance?id=${id}&amount=${amount}`, { method: 'POST' }),

  // Subtrair valor do saldo
  subtractFromBalance: (id: number, amount: number) =>
    request<void>(`/subtract-from-balance?id=${id}&amount=${amount}`, { method: 'POST' }),

  // Remover por número (corrigido para usar number)
  deleteByNumber: (number: number) =>
    request<void>(`/delete-by-number?number=${number}`, { method: 'POST' }),

  // Remover contas com saldo zero
  deleteByZeroBalance: () => request<void>('/delete-by-zero-balance', { method: 'POST' }),

  // Remover contas com saldo negativo
  deleteByNegativeBalance: () => request<void>('/delete-by-negative-balance', { method: 'POST' }),

  // Buscar saldo total de um usuário
  getTotalBalance: (userId: number) =>
    request<number>(`/total-balance?userId=${userId}`, { method: 'GET' }),

  // Buscar maior saldo de um usuário
  getMaxBalance: (userId: number) =>
    request<number>(`/max-balance?userId=${userId}`, { method: 'GET' }),

  // --- OPCIONAL ---
  // Criptografia e descriptografia (caso queira testar os endpoints)
  encrypt: (data: string) =>
    request<string>('/encrypt', { method: 'POST', body: JSON.stringify(data) }),

  decrypt: (data: string) =>
    request<string>('/decrypt', { method: 'POST', body: JSON.stringify(data) }),
};

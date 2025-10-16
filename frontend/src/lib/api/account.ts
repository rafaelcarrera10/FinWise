const BASE_URL = import.meta.env.VITE_API_BASE_URL + '/accounts';

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

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Erro ${response.status}: ${errorText}`);
    }

    return response.status !== 204 ? await response.json() : null;
  } catch (error) {
    console.error('Erro ao conectar ao backend:', error);
    throw error;
  }
}

export const AccountAPI = {
  create: (account: Account) =>
    request<Account>('/create', {
      method: 'POST',
      body: JSON.stringify(account),
    }),

  getByNumber: (number: string) =>
    request<Account>(`/by-number?number=${encodeURIComponent(number)}`, { method: 'GET' }),

  getByBalanceGreaterThan: (min: number) =>
    request<Account[]>(`/balance-greater-than?min=${min}`, { method: 'GET' }),

  getByBalanceBetween: (min: number, max: number) =>
    request<Account[]>(`/balance-between?min=${min}&max=${max}`, { method: 'GET' }),

  getAllByBalanceDesc: () => request<Account[]>('/order-by-balance-desc', { method: 'GET' }),
  getAllByBalanceAsc: () => request<Account[]>('/order-by-balance-asc', { method: 'GET' }),

  count: () => request<number>('', { method: 'GET' }),

  updateBalance: (id: number, newBalance: number) =>
    request<Account>(`/update-balance?id=${id}&newBalance=${newBalance}`, { method: 'POST' }),

  updateNumber: (id: number, newNumber: string) =>
    request<Account>(`/update-number?id=${id}&newNumber=${encodeURIComponent(newNumber)}`, { method: 'POST' }),

  deleteByNumber: (number: string) =>
    request<void>(`/delete-by-number?number=${encodeURIComponent(number)}`, { method: 'POST' }),

  deleteByZeroBalance: () => request<void>('/delete-by-zero-balance', { method: 'POST' }),

  deleteByNegativeBalance: () => request<void>('/delete-by-negative-balance', { method: 'POST' }),

  getTotalBalance: (userId: number) =>
    request<number>(`/total-balance?userId=${userId}`, { method: 'GET' }),

  getMaxBalance: (userId: number) =>
    request<number>(`/max-balance?userId=${userId}`, { method: 'GET' }),
};

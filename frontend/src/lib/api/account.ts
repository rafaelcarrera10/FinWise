let BASE_URL = 'http://localhost:8080/accounts';

export type Account = {
  id?: number;
  number: string;
  balance: number;
  user?: { id: number };
};

async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  const response = await fetch(`${BASE_URL}${endpoint}`, {
    headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
    ...options,
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`Erro ${response.status}: ${errorText}`);
  }

  const text = await response.text();
  return text ? (JSON.parse(text) as T) : null;
}

export const AccountAPI = {
  create: (account: Account) =>
    request<Account>('/create', { method: 'POST', body: JSON.stringify(account) }),

  getAccount: (userId: number) =>
    request<Account[]>(`/by-userid?userId=${userId}`, { method: 'GET' }),

  getByNumber: (number: string) =>
    request<Account>(`/by-number?number=${encodeURIComponent(number)}`, { method: 'GET' }),
};

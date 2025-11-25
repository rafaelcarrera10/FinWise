let BASE_URL = 'http://localhost:8080/accounts'

// -------------------- Modelo de Conta --------------------
export type Account = {
  id?: number
  number: string
  balance: number
  limite?: number
  reservation?: number
  leisure?: number
  user?: { id: number } // Relacionamento com o usuário
}

// -------------------- Função genérica HTTP --------------------
async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: {
        'Content-Type': 'application/json',
        Accept: 'application/json',
      },
      ...options,
    })

    if (!response.ok) {
      const errorText = await response.text()
      throw new Error(`Erro ${response.status}: ${errorText}`)
    }

    // Retorna vazio se não houver corpo
    const text = await response.text()
    if (!text) return null

    try {
      return JSON.parse(text) as T
    } catch {
      return text as unknown as T
    }
  } catch (error) {
    console.error(`Erro na requisição para ${endpoint}:`, error)
    throw error
  }
}

// -------------------- Métodos da AccountAPI --------------------
export const AccountAPI = {
  // Cria uma nova conta
  create: (account: Account) =>
    request<Account>('/create', {
      method: 'POST',
      body: JSON.stringify(account),
    }),

  // Busca todas as contas de um usuário
  getByUserId: (userId: number) =>
    request<Account[]>(`/by-user?userId=${userId}`, { method: 'GET' }),

  // Busca conta por número
  getByNumber: (number: string) =>
    request<Account>(`/by-number?number=${encodeURIComponent(number)}`, { method: 'GET' }),

  // Retorna o saldo total das contas do usuário
  getTotalBalance: (userId: number) =>
    request<number>(`/total-balance?userId=${userId}`, { method: 'GET' }),

  // Retorna o maior saldo do usuário
  getMaxBalance: (userId: number) =>
    request<number>(`/max-balance?userId=${userId}`, { method: 'GET' }),

  // Retorna o menor saldo do usuário
  getMinBalance: (userId: number) =>
    request<number>(`/min-balance?userId=${userId}`, { method: 'GET' }),

  // Retorna a média dos saldos do usuário
  getAverageBalance: (userId: number) =>
    request<number>(`/average-balance?userId=${userId}`, { method: 'GET' }),

  // Conta quantas contas o usuário possui
  countByUser: (userId: number) =>
    request<number>(`/count-by-user?userId=${userId}`, { method: 'GET' }),

  // Conta total de contas (global)
  countAll: () => request<number>('/count', { method: 'GET' }),

  // -------------------- Atualizações --------------------

  // Atualiza o saldo de uma conta pelo id
  updateBalance: (id: number, newBalance: number) =>
    request<number>(`/update/balance?id=${id}&newBalance=${newBalance}`, { method: 'PUT' }),

  // Atualiza o limite de uma conta pelo id
  updateLimite: (id: number, newLimite: number) =>
    request<number>(`/update/limite?id=${id}&newLimite=${newLimite}`, { method: 'PUT' }),

  // Atualiza o valor de reserva de uma conta pelo id
  updateReservation: (id: number, newReservation: number) =>
    request<number>(`/update/reservation?id=${id}&newReservation=${newReservation}`, { method: 'PUT' }),

  // Atualiza o valor de lazer de uma conta pelo id
  updateLeisure: (id: number, newLeisure: number) =>
    request<number>(`/update/leisure?id=${id}&newLeisure=${newLeisure}`, { method: 'PUT' }),

  // -------------------- Exclusões --------------------

  // Exclui uma conta pelo número
  deleteByNumber: (number: string) =>
    request<number>(`/delete/by-number?number=${encodeURIComponent(number)}`, { method: 'DELETE' }),

  // Exclui todas as contas com saldo igual a zero
  deleteZeroBalance: () =>
    request<number>('/delete/zero-balance', { method: 'DELETE' }),

  // Exclui todas as contas com saldo negativo
  deleteNegativeBalance: () =>
    request<number>('/delete/negative-balance', { method: 'DELETE' }),
}

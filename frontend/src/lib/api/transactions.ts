// src/api/transactions.ts
let BASE_URL = 'http://localhost:8080/transactions';

// Tipos
export type TransactionType = "INCOME" | "EXPENSE" | "TRANSFER"; // ajuste conforme seu enum

export type Transaction = {
  id?: number;
  type: TransactionType;
  value: number;
  description: string;
  [key: string]: any;
};

// Função genérica de requisição
async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: { "Content-Type": "application/json" },
      ...options,
    });

    if (!response.ok) {
      const text = await response.text();
      throw new Error(`Erro ${response.status}: ${text}`);
    }

    const text = await response.text();
    return text ? (JSON.parse(text) as T) : null;
  } catch (err) {
    console.error("Erro ao chamar backend:", err);
    throw err;
  }
}

export const TransactionsAPI = {
  // Criptografia
  encrypt: (data: string) =>
    request<string>("/encrypt", { method: "POST", body: JSON.stringify(data) }),

  decrypt: (data: string) =>
    request<string>("/decrypt", { method: "POST", body: JSON.stringify(data) }),

  // Criar
  create: (transaction: Transaction) =>
    request<Transaction>("/create", { method: "POST", body: JSON.stringify(transaction) }),

  // Buscar por ID
  getById: (id: number) =>
    request<Transaction>(`/by-id?id=${id}`, { method: "GET" }),

  // Buscar por tipo
  getByType: (type: TransactionType) =>
    request<Transaction[]>(`/by-type?type=${type}`, { method: "GET" }),

  // Buscar por valor maior que X
  getByValueGreaterThan: (minValue: number) =>
    request<Transaction[]>(`/value-greater-than?minValue=${minValue}`, { method: "GET" }),

  // Buscar por valor menor que X
  getByValueLessThan: (maxValue: number) =>
    request<Transaction[]>(`/value-less-than?maxValue=${maxValue}`, { method: "GET" }),

  // Buscar por valor entre dois
  getByValueBetween: (minValue: number, maxValue: number) =>
    request<Transaction[]>(`/value-between?minValue=${minValue}&maxValue=${maxValue}`, {
      method: "GET",
    }),

  // Buscar por descrição
  getByDescription: (text: string) =>
    request<Transaction[]>(`/by-description?text=${encodeURIComponent(text)}`, {
      method: "GET",
    }),

  // Buscar por tipo e valor mínimo
  getByTypeAndValueGreaterThan: (type: TransactionType, minValue: number) =>
    request<Transaction[]>(`/by-type-and-value-greater-than?type=${type}&minValue=${minValue}`, {
      method: "GET",
    }),

  // Contar por tipo
  countByType: (type: TransactionType) =>
    request<number>(`/count-by-type?type=${type}`, { method: "GET" }),

  // Ordenações
  orderByValueDesc: () => request<Transaction[]>("/order-by-value-desc", { method: "GET" }),
  orderByValueAsc: () => request<Transaction[]>("/order-by-value-asc", { method: "GET" }),
  orderByType: () => request<Transaction[]>("/order-by-type", { method: "GET" }),
  orderById: () => request<Transaction[]>("/order-by-id", { method: "GET" }),

  // Buscar múltiplos tipos
  getByMultipleTypes: (types: TransactionType[]) =>
    request<Transaction[]>(
      `/by-multiple-types?${types.map((t) => `types=${t}`).join("&")}`,
      { method: "GET" }
    ),

  // Atualizar valor e descrição
  updateValue: (id: number, newValue: number, newDescription: string) =>
    request<Transaction>(
      `/update-value?id=${id}&newValue=${newValue}&newDescription=${encodeURIComponent(
        newDescription
      )}`,
      { method: "POST" }
    ),

  // Atualizar tipo
  updateType: (id: number, newType: TransactionType) =>
    request<Transaction>(`/update-type?id=${id}&newType=${newType}`, { method: "POST" }),

  // Atualizar descrição
  updateDescription: (id: number, newDescription: string) =>
    request<Transaction>(
      `/update-description?id=${id}&newDescription=${encodeURIComponent(newDescription)}`,
      { method: "POST" }
    ),

  // Deletar
  deleteById: (id: number) =>
    request<void>(`/delete-by-id?id=${id}`, { method: "DELETE" }),

  // Soma total e por tipo
  sumAll: () => request<number>("/sum-all", { method: "GET" }),
  sumByType: (type: TransactionType) =>
    request<number>(`/sum-by-type?type=${type}`, { method: "GET" }),

  // Paginação
  getByPage: (page: number, size: number) =>
    request<Transaction[]>(`/by-page?page=${page}&size=${size}`, { method: "GET" }),

  // Agrupamento
  getGroupedByType: () =>
    request<Transaction[]>("/grouped-by-type", { method: "GET" }),

  // Últimas transações
  getLatest: (limit: number) =>
    request<Transaction[]>(`/latest?limit=${limit}`, { method: "GET" }),
};

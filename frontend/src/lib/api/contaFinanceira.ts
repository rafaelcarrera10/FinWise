// src/lib/api/contaFinanceira.ts

let BASE_URL = "http://localhost:8080/conta";

export type ContaFinanceira = {
  id?: number;
  nome: string;
  saldoAtual: number;
  saldoPrevisto?: number;
  receitaTotal?: number;
  despesaTotal?: number;
  usuarioId?: number;
  cartao?: any;
  plano?: any;
  listaTransacao?: any[];
};

// --------------------------------------------------------
// Função genérica segura
// --------------------------------------------------------
async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T> {
  const response = await fetch(`${BASE_URL}${endpoint}`, {
    headers: { "Content-Type": "application/json" },
    ...options
  });

  const raw = await response.text();

  if (!response.ok) {
    throw new Error(raw || "Erro na requisição");
  }

  if (!raw || raw.trim().length === 0) {
    return {} as T;
  }

  try {
    return JSON.parse(raw) as T;
  } catch {
    console.error("Erro ao parsear JSON:", raw);
    return {} as T;
  }
}

// --------------------------------------------------------
// API
// --------------------------------------------------------
export const ContaFinanceiraAPI = {
  create: (userId: number, data: ContaFinanceira) =>
    request<ContaFinanceira>(`/usuario/${userId}`, {
      method: "POST",
      body: JSON.stringify(data)
    }),

  getByUsuario: (userId: number) =>
    request<ContaFinanceira>(`/usuario/${userId}`),

  getById: (id: number) =>
    request<ContaFinanceira>(`/${id}`),

  update: (id: number, data: ContaFinanceira) =>
    request<ContaFinanceira>(`/${id}`, {
      method: "PUT",
      body: JSON.stringify(data)
    }),

  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" }),

  updateSaldo: (userId: number, novoSaldo: number) =>
    request<void>(`/update-saldo?userId=${userId}&novoSaldo=${novoSaldo}`, {
      method: "PUT"
    })
};

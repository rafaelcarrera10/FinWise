// src/lib/api/cartaoCredito.ts
let BASE_URL = "http://localhost:8080/cartao";

export type CartaoCredito = {
  id?: number;
  diaVencimento: string; // YYYY-MM-DD
  diaFechamento: string; // YYYY-MM-DD
  descricao: string;
  faturaAtual: number;
  faturaSeguinte: number;
  listaDespesa?: any[]; // criar interface se desejar
  conta?: any; // criar interface se desejar
};

// ---------------- Função genérica ----------------
async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: { "Content-Type": "application/json" },
      ...options
    });

    if (!response.ok) throw new Error(await response.text());

    const text = await response.text();
    return text ? JSON.parse(text) : null;

  } catch (err) {
    console.error("Erro API CARTÃO CRÉDITO:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const CartaoCreditoAPI = {
  create: (data: CartaoCredito) =>
    request<CartaoCredito>("/create", { method: "POST", body: JSON.stringify(data) }),

  getById: (id: number) =>
    request<CartaoCredito>(`/by-id?id=${id}`),

  getAll: () =>
    request<CartaoCredito[]>("/all"),

  update: (data: CartaoCredito) =>
    request<CartaoCredito>("/update", { method: "PUT", body: JSON.stringify(data) }),

  delete: (id: number) =>
    request<void>(`/delete?id=${id}`, { method: "POST" })
};

// src/lib/api/conta.ts
let BASE_URL = "http://localhost:8080/conta";

export type ContaFinanceira = {
  id?: number;
  userId: number;
  saldo: number;
  limiteCredito: number;
  reserva: number;
  lazer: number;
};

// ---------------- Função genérica ----------------
async function request<T>(endpoint: string, options: RequestInit = {}) {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: { "Content-Type": "application/json" },
      ...options
    });

    if (!response.ok) throw new Error(await response.text());
    const text = await response.text();
    return text ? JSON.parse(text) : null;

  } catch (err) {
    console.error("Erro API CONTA:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const ContaAPI = {
  create: (c: ContaFinanceira) =>
    request<ContaFinanceira>("", { method: "POST", body: JSON.stringify(c) }),

  getByUsuario: (userId: number) =>
    request<ContaFinanceira>(`/usuario/${userId}`),

  update: (id: number, c: ContaFinanceira) =>
    request<ContaFinanceira>(`/${id}`, { method: "PUT", body: JSON.stringify(c) }),

  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" })
};

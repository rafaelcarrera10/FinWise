// src/lib/api/transacao.ts
let BASE_URL = "http://localhost:8080/transacao";

export type Transacao = {
  id?: number;
  valor: number;
  descricao: string;
  contaId: number;
  data: string;
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
    console.error("Erro API TRANSACAO:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const TransacaoAPI = {
  create: (t: Transacao) =>
    request<Transacao>("", { method: "POST", body: JSON.stringify(t) }),

  getById: (id: number) =>
    request<Transacao>(`/${id}`),

  getByConta: (contaId: number) =>
    request<Transacao[]>(`/conta/${contaId}`),

  update: (id: number, t: Transacao) =>
    request<Transacao>(`/${id}`, { method: "PUT", body: JSON.stringify(t) }),

  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" })
};

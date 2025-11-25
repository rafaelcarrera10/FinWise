// src/lib/api/despesa.ts
let BASE_URL = "http://localhost:8080/despesa";

export type Despesa = {
  id?: number;
  valor: number;
  descricao: string;
  contaId: number;
  categoriaId: number;
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
    console.error("Erro API DESPESA:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const DespesaAPI = {
  create: (d: Despesa) =>
    request<Despesa>("", { method: "POST", body: JSON.stringify(d) }),

  getById: (id: number) =>
    request<Despesa>(`/${id}`),

  getByConta: (contaId: number) =>
    request<Despesa[]>(`/conta/${contaId}`),

  getByCategoria: (categoriaId: number) =>
    request<Despesa[]>(`/categoria/${categoriaId}`),

  update: (id: number, d: Despesa) =>
    request<Despesa>(`/${id}`, { method: "PUT", body: JSON.stringify(d) }),

  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" })
};

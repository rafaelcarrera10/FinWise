// src/lib/api/receita.ts
let BASE_URL = "http://localhost:8080/receita";

export type Receita = {
  id?: number;
  valor: number;
  dataInicial: string;  // ou Date, depende de como você usa no projeto
  dataFinal?: string;
  descricao: string;
  observacao?: string;
  repeticao: string; // Enum em string
  categoriaId: number;
  contaId: number;
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
    console.error("Erro API RECEITA:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const ReceitaAPI = {
  create: (r: Receita) =>
    request<Receita>("", { method: "POST", body: JSON.stringify(r) }),

  getById: (id: number) =>
    request<Receita>(`/${id}`),

  getByConta: (contaId: number) =>
    request<Receita[]>(`/conta/${contaId}`),

  update: (id: number, r: Receita) =>
    request<Receita>(`/${id}`, { method: "PUT", body: JSON.stringify(r) }),

  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" })
};

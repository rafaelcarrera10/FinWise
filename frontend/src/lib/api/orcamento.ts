// src/lib/api/orcamento.ts
let BASE_URL = "http://localhost:8080/orcamento";

export type Orcamento = {
  id?: number;
  valor: number;
  planoId?: number;
  categoriaId?: number;
};

// Função genérica de request
async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: { "Content-Type": "application/json" },
      ...options,
    });

    if (!response.ok) throw new Error(await response.text());

    const text = await response.text();
    return text ? JSON.parse(text) : null;
  } catch (err) {
    console.error("Erro API Orcamento:", err);
    throw err;
  }
}

// API Orcamento
export const OrcamentoAPI = {
  create: (data: Orcamento) =>
    request<Orcamento>("/create", { method: "POST", body: JSON.stringify(data) }),

  getAll: () =>
    request<Orcamento[]>("/all"),

  getById: (id: number) =>
    request<Orcamento>(`/by-id?id=${id}`),

  update: (data: Orcamento) =>
    request<Orcamento>("/update", { method: "PUT", body: JSON.stringify(data) }),

  delete: (id: number) =>
    request<void>(`/delete?id=${id}`, { method: "POST" }),
};


// src/lib/api/planoOrcamentario.ts
let BASE_URL = "http://localhost:8080/planos";

export type PlanoOrcamentario = {
  id?: number;
  condicao: boolean;
  nome: string;

  valorGastar: number;
  valorGuardar: number;
  valor: number;

  descricao: string;
  dataInicial: string; // yyyy-MM-dd
  dataFinal: string;

  conta?: {
    id: number;
  };

  listaOrcamento?: any[];
};

// --------------- Função genérica de requisição ----------------
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
    console.error("Erro API PLANO:", err);
    throw err;
  }
}

// ---------------------- API ----------------------
export const PlanoOrcamentarioAPI = {
  // CREATE
  create: (p: PlanoOrcamentario) =>
    request<PlanoOrcamentario>("", { method: "POST", body: JSON.stringify(p) }),

  // READ – All
  getAll: () =>
    request<PlanoOrcamentario[]>(""),

  // READ – By ID
  getById: (id: number) =>
    request<PlanoOrcamentario>(`/${id}`),

  // READ – By nome
  getByNome: (nome: string) =>
    request<PlanoOrcamentario[]>(`/nome/${nome}`),

  // READ – By contaFinanceira_id
  getByConta: (contaId: number) =>
    request<PlanoOrcamentario[]>(`/conta/${contaId}`),

  // READ – By condicao
  getByCondicao: (condicao: boolean) =>
    request<PlanoOrcamentario[]>(`/condicao/${condicao}`),

  // UPDATE
  update: (id: number, p: PlanoOrcamentario) =>
    request<PlanoOrcamentario>(`/${id}`, {
      method: "PUT",
      body: JSON.stringify(p)
    }),

  // DELETE
  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" })
};
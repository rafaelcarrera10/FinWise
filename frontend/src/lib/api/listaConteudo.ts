// src/lib/api/listaConteudo.ts
let BASE_URL = "http://localhost:8080/listas-conteudo";

export type ListaConteudo = {
  id?: number;
  nome: string;
  descricao: string;
  tags: string[]; // TagEnum como string
  professorId?: number;
  livrosIds?: number[];
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
    console.error("Erro API ListaConteudo:", err);
    throw err;
  }
}

// API
export const ListaConteudoAPI = {
  create: (data: ListaConteudo) =>
    request<ListaConteudo>("", { method: "POST", body: JSON.stringify(data) }),

  getById: (id: number) =>
    request<ListaConteudo>(`/${id}`),

  getAll: () =>
    request<ListaConteudo[]>(``),

  getByProfessor: (professorId: number) =>
    request<ListaConteudo[]>(`/professor/${professorId}`),

  getByNome: (nome: string) =>
    request<ListaConteudo[]>(`?nome=${encodeURIComponent(nome)}`),

  getByTag: (tag: string) =>
    request<ListaConteudo[]>(`/tag/${encodeURIComponent(tag)}`),

  update: (id: number, data: Partial<ListaConteudo>) =>
    request<ListaConteudo>(`/${id}`, { method: "PUT", body: JSON.stringify(data) }),

  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" }),
};

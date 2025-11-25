// src/lib/api/listaConteudo.ts
let BASE_URL = "http://localhost:8080/lista-conteudo";

export type ListaConteudo = {
  id?: number;
  idProfessor: number;
  titulo: string;
  itens: string[];
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
    console.error("Erro API LISTA CONTEUDO:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const ListaConteudoAPI = {
  create: (data: ListaConteudo) =>
    request<ListaConteudo>("", { method: "POST", body: JSON.stringify(data) }),

  getById: (id: number) =>
    request<ListaConteudo>(`/${id}`),

  getAll: () =>
    request<ListaConteudo[]>(""),

  getByProfessor: (professorId: number) =>
    request<ListaConteudo[]>(`/professor/${professorId}`),

  update: (id: number, data: ListaConteudo) =>
    request<ListaConteudo>(`/${id}`, { method: "PUT", body: JSON.stringify(data) }),

  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" })
};

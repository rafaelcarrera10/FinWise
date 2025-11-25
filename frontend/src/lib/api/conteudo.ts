// src/lib/api/conteudo.ts
let BASE_URL = "http://localhost:8080/conteudos";

export type Conteudo = {
  id?: number;
  titulo: string;
  descricao: string;
  texto: string;
  professorId: number;
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
    console.error("Erro API CONTEUDO:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const ConteudoAPI = {
  create: (data: Conteudo) =>
    request<Conteudo>("/create", { method: "POST", body: JSON.stringify(data) }),

  getAll: () =>
    request<Conteudo[]>("/all"),

  getById: (id: number) =>
    request<Conteudo>(`/by-id?id=${id}`),

  getByTitulo: (titulo: string) =>
    request<Conteudo>(`/by-titulo?titulo=${encodeURIComponent(titulo)}`),

  search: (q: string) =>
    request<Conteudo[]>(`/search?q=${encodeURIComponent(q)}`),

  getByProfessor: (professorId: number) =>
    request<Conteudo[]>(`/by-professor?professorId=${professorId}`),

  delete: (id: number) =>
    request<void>(`/delete?id=${id}`, { method: "POST" })
};

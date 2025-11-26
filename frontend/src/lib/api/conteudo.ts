// src/lib/api/conteudo.ts
let BASE_URL = "http://localhost:8080/conteudos";

// ---------------- Enum ----------------
export enum TagEnum {
  INVESTIMENTO = "INVESTIMENTO",
    ACOES = "ACOES",
    POUPANCA = "POUPANCA",
    ECONOMIA = "ECONOMIA"
}

// ---------------- Interfaces ----------------
export interface ListaConteudo {
  id: number;
  nome: string;
}

export interface UsuarioResumo {
  id: number;
  name: string;
}

export interface Conteudo {
  id?: number;
  titulo: string;
  descricao: string;
  tag: TagEnum; // ✅ agora usa enum
  professorId: number;
  conteudoListas?: ListaConteudo[];
  usuariosFavoritos?: UsuarioResumo[];
}

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
    console.error("Erro API CONTEÚDO:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const ConteudoAPI = {
  create: (data: Conteudo) =>
    request<Conteudo>("", { method: "POST", body: JSON.stringify(data) }),

  getById: (id: number) =>
    request<Conteudo>(`/${id}`),

  getAll: () =>
    request<Conteudo[]>(""),

  update: (id: number, data: Conteudo) =>
    request<Conteudo>(`/${id}`, { method: "PUT", body: JSON.stringify(data) }),

  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" }),

  searchByTitulo: (titulo: string) =>
    request<Conteudo[]>(`/search?titulo=${encodeURIComponent(titulo)}`),

  getByProfessor: (professorId: number) =>
    request<Conteudo[]>(`/professor/${professorId}`)
};

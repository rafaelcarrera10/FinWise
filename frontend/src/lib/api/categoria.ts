// src/lib/api/categoria.ts
let BASE_URL = "http://localhost:8080/categorias";

export type Categoria = {
  id?: number;
  name: string;
  usuarioId?: number; // ID do usuário dono da categoria
  orcamento?: any; // criar interface se desejar
  listaTransacao?: any[]; // criar interface se desejar
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
    console.error("Erro API CATEGORIA:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const CategoriaAPI = {
  create: (userId: number, data: Categoria) =>
    request<Categoria>(`/user/${userId}`, { method: "POST", body: JSON.stringify(data) }),

  getById: (id: number) =>
    request<Categoria>(`/${id}`),

  getAllByUsuario: (userId: number) =>
    request<Categoria[]>(`/user/${userId}`),

  getAllByUsuarioOrdenado: (userId: number) =>
    request<Categoria[]>(`/user/${userId}/ordenado`),

  update: (id: number, data: Categoria) =>
    request<Categoria>(`/${id}`, { method: "PUT", body: JSON.stringify(data) }),

  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" })
};

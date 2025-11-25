// src/lib/api/categoria.ts
let BASE_URL = "http://localhost:8080/categoria";

export type Categoria = {
  id?: number;
  nome: string;
  userId: number;
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
    console.error("Erro API CATEGORIA:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const CategoriaAPI = {
  create: (c: Categoria) =>
    request<Categoria>("", { method: "POST", body: JSON.stringify(c) }),

  getByUsuario: (userId: number) =>
    request<Categoria[]>(`/usuario/${userId}`),

  getById: (id: number) =>
    request<Categoria>(`/${id}`),

  update: (id: number, c: Categoria) =>
    request<Categoria>(`/${id}`, { method: "PUT", body: JSON.stringify(c) }),

  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" })
};

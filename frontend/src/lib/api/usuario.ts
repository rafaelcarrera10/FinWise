// src/lib/api/user.ts
let BASE_URL = "http://localhost:8080/users";

export type User = {
  id?: number;
  name: string;
  email: string;
  password: string;
  photo?: string;
  description?: string;
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
    console.error("Erro API USER:", err);
    throw err;
  }
}

// ---------------- API completa ----------------
export const UsuarioAPI = {
  // Criação de usuário
  create: (user: User) =>
    request<User>("/create", { method: "POST", body: JSON.stringify(user) }),

  // Login
  login: (email: string, password: string) =>
    request<User>("/login", {
      method: "POST",
      body: JSON.stringify({ email, password })
    }),

  // Criptografar string
  encrypt: (data: string) =>
    request<string>("/encrypt", {
      method: "POST",
      body: JSON.stringify(data)
    }),

  // Descriptografar string
  decrypt: (data: string) =>
    request<string>("/decrypt", {
      method: "POST",
      body: JSON.stringify(data)
    }),

  // Buscar usuário por email
  getByEmail: (email: string) =>
    request<User>(`/by-email?email=${encodeURIComponent(email)}`),

  // Busca por nome parcial
  getByNameContaining: (name: string) =>
    request<User[]>(`/name-containing?name=${encodeURIComponent(name)}`),

  // Verifica se email existe
  existsByEmail: (email: string) =>
    request<boolean>(`/exists-by-email?email=${encodeURIComponent(email)}`),

  // Contagem total de usuários
  count: () =>
    request<number>("/count"),

  // Buscar por nome exato
  getByName: (name: string) =>
    request<User[]>(`/by-name?name=${encodeURIComponent(name)}`),

  // Buscar por prefixo de nome
  getByNameStartingWith: (prefix: string) =>
    request<User[]>(`/name-starting-with?prefix=${encodeURIComponent(prefix)}`),

  // Lista todos ordenados por nome
  getAllOrderByNameAsc: () =>
    request<User[]>("/all-order-by-name-asc"),

  // Lista todos ordenados por ID DESC
  getAllOrderByIdDesc: () =>
    request<User[]>("/all-order-by-id-desc"),

  // Busca por lista de IDs
  getByIds: (ids: number[]) =>
    request<User[]>(`/by-ids?${ids.map(id => `ids=${id}`).join("&")}`),

  // Foto por ID
  getPhotoByUserId: (id: number) =>
    request<string>(`/photo-by-user-id?id=${id}`),

  // Descrição por ID
  getDescriptionByUserId: (id: number) =>
    request<string>(`/description-by-user-id?id=${id}`),

  // Atualizar nome
  updateName: (id: number, newName: string) =>
    request<void>("/update-name", {
      method: "POST",
      body: JSON.stringify({ id, newName })
    }),

  // Atualizar email
  updateEmail: (id: number, newEmail: string) =>
    request<void>("/update-email", {
      method: "POST",
      body: JSON.stringify({ id, newEmail })
    }),

  // Atualizar senha
  updatePassword: (userId: number, oldPassword: string, newPassword: string) =>
    request<void>("/update-password", {
      method: "POST",
      body: JSON.stringify({ userId, oldPassword, newPassword })
    }),

  // Atualizar foto
  updatePhoto: (id: number, photo: string) =>
    request<void>("/update-photo", {
      method: "POST",
      body: JSON.stringify({ id, photo })
    }),

  // Atualizar descrição
  updateDescription: (id: number, description: string) =>
    request<void>("/update-description", {
      method: "POST",
      body: JSON.stringify({ id, description })
    }),

  // Deletar usuário por nome
  deleteByName: (name: string) =>
    request<void>(`/delete-by-name?name=${encodeURIComponent(name)}`, {
      method: "POST"
    }),

  // Remover foto
  removePhoto: (id: number) =>
    request<void>(`/remove-photo?id=${id}`, { method: "POST" }),

  // Remover descrição
  removeDescription: (id: number) =>
    request<void>(`/remove-description?id=${id}`, { method: "POST" })
};

// src/lib/api/user.ts
let BASE_URL = 'http://localhost:8080/users';


export type User = {
  role: string;
  id?: number;
  name: string;
  email: string;
  password?: string;
  description?: string;
  photo?: string;
};

// Tipos auxiliares
export type UserLogin = { email: string; password: string };
export type UserCreate = Omit<User, "id">;
export type UserUpdateName = { id: number; newName: string };
export type UserUpdateEmail = { id: number; newEmail: string };
export type UserUpdatePassword = { id: number; newPassword: string };
export type UserUpdatePhoto = { id: number; photo: string };
export type UserUpdateDescription = { id: number; description: string };

// Função utilitária genérica para requisições
async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: { "Content-Type": "application/json" },
      ...options,
    });

    if (!response.ok) {
      const text = await response.text();
      throw new Error(`Erro ${response.status}: ${text}`);
    }

    const text = await response.text();
    return text ? (JSON.parse(text) as T) : null;
  } catch (err) {
    console.error("Erro ao chamar backend:", err);
    throw err;
  }
}

export const UserAPI = {
  // Criptografia
  encrypt: (data: string) => request<string>("/encrypt", { method: "POST", body: JSON.stringify(data) }),
  decrypt: (data: string) => request<string>("/decrypt", { method: "POST", body: JSON.stringify(data) }),

  // Criar usuário
  create: (user: UserCreate) => request<User>("/create", { method: "POST", body: JSON.stringify(user) }),

  // Login
  login: ({ email, password }: UserLogin) =>
    request<User>("/login", { method: "POST", body: JSON.stringify({ email, password }) }),

  // Buscar por e-mail
  getByEmail: (email: string) => request<User>(`/by-email?email=${encodeURIComponent(email)}`, { method: "GET" }),

  // Buscar por nome (parcial)
  getByNameContaining: (name: string) =>
    request<User[]>(`/name-containing?name=${encodeURIComponent(name)}`, { method: "GET" }),

  // Verificar existência por email
  existsByEmail: (email: string) =>
    request<boolean>(`/exists-by-email?email=${encodeURIComponent(email)}`, { method: "GET" }),

  // Contar usuários
  count: () => request<number>("/count", { method: "GET" }),

  // Buscar por nome exato
  getByName: (name: string) => request<User[]>(`/by-name?name=${encodeURIComponent(name)}`, { method: "GET" }),

  // Buscar por prefixo
  getByNameStartingWith: (prefix: string) =>
    request<User[]>(`/name-starting-with?prefix=${encodeURIComponent(prefix)}`, { method: "GET" }),

  // Ordenações
  getAllOrderByNameAsc: () => request<User[]>("/all-order-by-name-asc", { method: "GET" }),
  getAllOrderByIdDesc: () => request<User[]>("/all-order-by-id-desc", { method: "GET" }),

  // Paginação
  getWithPagination: (offset: number, limit: number) =>
    request<User[]>(`/with-pagination?offset=${offset}&limit=${limit}`, { method: "GET" }),

  // Buscar múltiplos IDs
  getByIds: (ids: number[]) =>
    request<User[]>(`/by-ids?${ids.map((id) => `ids=${id}`).join("&")}`, { method: "GET" }),

  // Buscar e-mail parcial
  getByEmailContaining: (emailPart: string) =>
    request<User[]>(`/email-containing?emailPart=${encodeURIComponent(emailPart)}`, { method: "GET" }),

  // Buscar nome ou email
  getByNameOrEmailContaining: (searchText: string) =>
    request<User[]>(`/name-or-email-containing?searchText=${encodeURIComponent(searchText)}`, { method: "GET" }),

  // Buscar foto
  getPhotoByUserId: (id: number) =>
    request<string>(`/photo-by-user-id?id=${id}`, { method: "GET" }),

  // Buscar descrição
  getDescriptionByUserId: (id: number) =>
    request<string>(`/description-by-user-id?id=${id}`, { method: "GET" }),

  // Atualizações
  // Atualizar nome
  updateName: (id: number, newName: string) =>
    request<void>(`/update-name?id=${id}&newName=${encodeURIComponent(newName)}`, { method: "POST" }),

  // Atualizar email
  updateEmail: (id: number, newEmail: string) =>
    request<void>(`/update-email?id=${id}&newEmail=${encodeURIComponent(newEmail)}`, { method: "POST" }),

  // Atualizar senha
  updatePassword: (id: number, newPassword: string) =>
    request<void>(`/update-password?id=${id}&newPassword=${encodeURIComponent(newPassword)}`, { method: "POST" }),

  // Atualizar foto
  updatePhoto: (id: number, photo: string) =>
    request<void>(`/update-photo?id=${id}`, { method: "POST", body: JSON.stringify({ photo }) }),

  // Atualizar descrição
  updateDescription: (id: number, description: string) =>
    request<void>(`/update-description?id=${id}&description=${encodeURIComponent(description)}`, {
      method: "POST",
    }),

  // Deletar
  // Deletar email
  deleteByEmail: (email: string) =>
    request<void>(`/delete-by-email?email=${encodeURIComponent(email)}`, { method: "POST" }),

  // Deletar nome
  deleteByName: (name: string) =>
    request<void>(`/delete-by-name?name=${encodeURIComponent(name)}`, { method: "POST" }),

  // Remover foto e descrição
  removePhoto: (id: number) => request<void>(`/remove-photo?id=${id}`, { method: "POST" }),
  removeDescription: (id: number) => request<void>(`/remove-description?id=${id}`, { method: "POST" }),
};

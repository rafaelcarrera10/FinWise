// src/lib/api/user.ts
let BASE_URL = 'http://localhost:8080/users';

// ---------------------- Tipos ----------------------
export type User = {
  id?: number;
  role: string;
  name: string;
  email: string;
  password?: string;
  description?: string;
  photo?: string;
};

export type UserLogin = { email: string; password: string };
export type UserCreate = Omit<User, "id">;

export type UserUpdateName = { id: number; newName: string };
export type UserUpdateEmail = { id: number; newEmail: string };
export type UserUpdatePassword = { id: number; oldPassword: string; newPassword: string };
export type UserUpdatePhoto = { id: number; photo: string };
export type UserUpdateDescription = { id: number; description: string };

// ---------------------- Função genérica ----------------------
async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: { "Content-Type": "application/json" },
      ...options
    });

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Erro ${response.status}: ${errorText}`);
    }

    const text = await response.text();
    return text ? (JSON.parse(text) as T) : null;

  } catch (err) {
    console.error("Erro ao chamar backend:", err);
    throw err;
  }
}

// ---------------------- API ----------------------
export const UserAPI = {
  // Criptografia
  encrypt: (data: string) =>
    request<string>("/encrypt", { method: "POST", body: JSON.stringify(data) }),

  decrypt: (data: string) =>
    request<string>("/decrypt", { method: "POST", body: JSON.stringify(data) }),

  // Criar usuário
  create: (user: UserCreate) =>
    request<User>("/create", { method: "POST", body: JSON.stringify(user) }),

  // Login
  login: (loginData: UserLogin) =>
    request<User>("/login", { method: "POST", body: JSON.stringify(loginData) }),

  // Buscar por e-mail
  getByEmail: (email: string) =>
    request<User>(`/by-email?email=${encodeURIComponent(email)}`, { method: "GET" }),

  // Buscar nome parcial
  getByNameContaining: (name: string) =>
    request<User[]>(`/name-containing?name=${encodeURIComponent(name)}`, { method: "GET" }),

  // Verificar existência por email
  existsByEmail: (email: string) =>
    request<boolean>(`/exists-by-email?email=${encodeURIComponent(email)}`, { method: "GET" }),

  // Contagem total
  count: () => request<number>("/count", { method: "GET" }),

  // Busca por nome exato
  getByName: (name: string) =>
    request<User[]>(`/by-name?name=${encodeURIComponent(name)}`, { method: "GET" }),

  // Busca por prefixo
  getByNameStartingWith: (prefix: string) =>
    request<User[]>(`/name-starting-with?prefix=${encodeURIComponent(prefix)}`, { method: "GET" }),

  // Ordenações
  getAllOrderByNameAsc: () =>
    request<User[]>("/all-order-by-name-asc", { method: "GET" }),

  getAllOrderByIdDesc: () =>
    request<User[]>("/all-order-by-id-desc", { method: "GET" }),

  // Paginação
  getWithPagination: (offset: number, limit: number) =>
    request<User[]>(`/with-pagination?offset=${offset}&limit=${limit}`, { method: "GET" }),

  // Multi-ID
  getByIds: (ids: number[]) =>
    request<User[]>(`/by-ids?${ids.map((id) => `ids=${id}`).join("&")}`, { method: "GET" }),

  // Buscar email parcial
  getByEmailContaining: (emailPart: string) =>
    request<User[]>(`/email-containing?emailPart=${encodeURIComponent(emailPart)}`, { method: "GET" }),

  // Buscar nome OU email
  getByNameOrEmailContaining: (searchText: string) =>
    request<User[]>(`/name-or-email-containing?searchText=${encodeURIComponent(searchText)}`, { method: "GET" }),

  // Buscar dados adicionais
  getPhotoByUserId: (id: number) =>
    request<string>(`/photo-by-user-id?id=${id}`, { method: "GET" }),

  getDescriptionByUserId: (id: number) =>
    request<string>(`/description-by-user-id?id=${id}`, { method: "GET" }),

  // ---------------------- Atualizações ----------------------

  updateName: (id: number, newName: string) =>
    request<void>(`/update-name`, {
      method: "POST",
      body: JSON.stringify({ id, newName })
    }),

  updateEmail: (id: number, newEmail: string) =>
    request<void>(`/update-email`, {
      method: "POST",
      body: JSON.stringify({ id, newEmail })
    }),

  // Agora inclui senha antiga + nova (melhor prática)
  async updatePassword({ userId, oldPassword, newPassword }: 
  { userId: number; oldPassword: string; newPassword: string }) 
{
  return request(`/update-password`, {
    method: "POST",
    body: JSON.stringify({ userId, oldPassword, newPassword }),
    headers: { "Content-Type": "application/json" }
  });
},


  updatePhoto: (id: number, photo: string) =>
    request<void>(`/update-photo`, {
      method: "POST",
      body: JSON.stringify({ id, photo })
    }),

  updateDescription: (id: number, description: string) =>
    request<void>(`/update-description`, {
      method: "POST",
      body: JSON.stringify({ id, description })
    }),

  // ---------------------- Deletes ----------------------

  deleteByEmail: (email: string) =>
    request<void>(`/delete-by-email?email=${encodeURIComponent(email)}`, { method: "POST" }),

  deleteByName: (name: string) =>
    request<void>(`/delete-by-name?name=${encodeURIComponent(name)}`, { method: "POST" }),

  removePhoto: (id: number) =>
    request<void>(`/remove-photo?id=${id}`, { method: "POST" }),

  removeDescription: (id: number) =>
    request<void>(`/remove-description?id=${id}`, { method: "POST" }),
};

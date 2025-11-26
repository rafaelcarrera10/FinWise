// usuarioApi.ts

export interface Usuario {
  id?: number;
  name: string;
  password: string;
  listaCategoria?: { id: number }[];
  conta?: { id: number };
  listaFavoritos?: { id: number }[];
}

const BASE_URL = "http://localhost:8080/users";

const handleResponse = async (response: Response) => {
  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || "Erro desconhecido");
  }
  return response.status === 204 ? null : response.json();
};

export const UsuarioAPI = {
  // Criptografia / Descriptografia
  encrypt: async (data: string) => {
    const response = await fetch(`${BASE_URL}/encrypt`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });
    return handleResponse(response);
  },

  decrypt: async (data: string) => {
    const response = await fetch(`${BASE_URL}/decrypt`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    });
    return handleResponse(response);
  },

  // Criar usuário
  create: async (user: Usuario) => {
    const response = await fetch(`${BASE_URL}/create`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    });
    return handleResponse(response);
  },

  // Login
  login: async (name: string, password: string) => {
    const response = await fetch(`${BASE_URL}/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name, password }),
    });
    return handleResponse(response);
  },

  // Buscar usuário por ID
  getById: async (id: number) => {
    const response = await fetch(`${BASE_URL}/by-id?id=${id}`);
    return handleResponse(response);
  },

  // Buscar usuário por nome
  getByName: async (name: string) => {
    const response = await fetch(`${BASE_URL}/by-name?name=${encodeURIComponent(name)}`);
    return handleResponse(response);
  },

  // Buscar por fragmento de nome
  searchByFragment: async (q: string) => {
    const response = await fetch(`${BASE_URL}/search?q=${encodeURIComponent(q)}`);
    return handleResponse(response);
  },

  // Atualizar nome
  updateName: async (id: number, newName: string) => {
    const response = await fetch(`${BASE_URL}/update-name`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id, newName }),
    });
    return handleResponse(response);
  },

  // Atualizar password
  updatepassword: async (id: number, oldpassword: string, newpassword: string) => {
    const response = await fetch(`${BASE_URL}/update-password`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id, oldpassword, newpassword }),
    });
    return handleResponse(response);
  },

  // Deletar usuário
  deleteById: async (id: number) => {
    const response = await fetch(`${BASE_URL}/delete-by-id?id=${id}`, {
      method: "POST",
    });
    return handleResponse(response);
  },
};

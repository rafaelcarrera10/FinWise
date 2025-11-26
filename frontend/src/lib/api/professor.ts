// src/api/professorApi.ts

export interface Professor {
  id: number;
  name: string;
  senha: string;
  status: boolean;
  fotoPerfil: string; // Base64
  biografia: string;
}

const BASE_URL = "http://localhost:8080/professores";

export const professorApi = {
  // Criar professor
  create: async (professor: Omit<Professor, "id">): Promise<Professor> => {
    const res = await fetch(`${BASE_URL}/create`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(professor),
    });
    return res.json();
  },

  // Listar todos
  getAll: async (): Promise<Professor[]> => {
    const res = await fetch(`${BASE_URL}/all`);
    return res.json();
  },

  // Buscar por ID
  getById: async (id: number): Promise<Professor | null> => {
    const res = await fetch(`${BASE_URL}/by-id?id=${id}`);
    return res.ok ? res.json() : null;
  },

  // Pesquisar por nome
  searchContaining: async (text: string): Promise<Professor[]> => {
    const res = await fetch(`${BASE_URL}/search?containing=${encodeURIComponent(text)}`);
    return res.json();
  },

  searchExact: async (name: string): Promise<Professor[]> => {
    const res = await fetch(`${BASE_URL}/search?exact=${encodeURIComponent(name)}`);
    return res.json();
  },

  searchPrefix: async (prefix: string): Promise<Professor[]> => {
    const res = await fetch(`${BASE_URL}/search?prefix=${encodeURIComponent(prefix)}`);
    return res.json();
  },

  // Ordenações
  getAllOrderByName: async (): Promise<Professor[]> => {
    const res = await fetch(`${BASE_URL}/ordered/by-name`);
    return res.json();
  },

  getAllOrderByIdDesc: async (): Promise<Professor[]> => {
    const res = await fetch(`${BASE_URL}/ordered/by-id-desc`);
    return res.json();
  },

  // Buscar múltiplos IDs
  getByIds: async (ids: number[]): Promise<Professor[]> => {
    const params = ids.map((id) => `ids=${id}`).join("&");
    const res = await fetch(`${BASE_URL}/by-ids?${params}`);
    return res.json();
  },

  // Atualizações
  updateName: async (id: number, newName: string): Promise<void> => {
    await fetch(`${BASE_URL}/update-name`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id, newName }),
    });
  },

  updatePassword: async (id: number, newPassword: string): Promise<void> => {
    await fetch(`${BASE_URL}/update-password`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id, newPassword }),
    });
  },

  updateBiografia: async (id: number, biografia: string): Promise<void> => {
    await fetch(`${BASE_URL}/update-biografia`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id, biografia }),
    });
  },

  updateFotoPerfil: async (id: number, photoBase64: string): Promise<void> => {
    await fetch(`${BASE_URL}/update-foto-perfil`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ id, photo: photoBase64 }),
    });
  },

  // Remoções
  removeFotoPerfil: async (id: number): Promise<void> => {
    await fetch(`${BASE_URL}/remove-foto-perfil?id=${id}`, { method: "POST" });
  },

  removeBiografia: async (id: number): Promise<void> => {
    await fetch(`${BASE_URL}/remove-biografia?id=${id}`, { method: "POST" });
  },

  deleteById: async (id: number): Promise<void> => {
    await fetch(`${BASE_URL}/delete?id=${id}`, { method: "POST" });
  },

  deleteByName: async (name: string): Promise<void> => {
    await fetch(`${BASE_URL}/delete-by-name?name=${encodeURIComponent(name)}`, { method: "POST" });
  },

  // Consultas específicas
  getFotoPerfilBase64: async (id: number): Promise<string | null> => {
    const res = await fetch(`${BASE_URL}/foto-perfil?id=${id}`);
    return res.ok ? res.text() : null;
  },

  getBiografia: async (id: number): Promise<string | null> => {
    const res = await fetch(`${BASE_URL}/biografia?id=${id}`);
    return res.ok ? res.text() : null;
  },

  getStatusById: async (id: number): Promise<boolean | null> => {
    const res = await fetch(`${BASE_URL}/status?id=${id}`);
    return res.ok ? res.json() : null;
  },
};

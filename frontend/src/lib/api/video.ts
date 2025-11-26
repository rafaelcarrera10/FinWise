import type { Tag } from "./tag";

export interface Video {
  id?: number;
  descricao: string;
  tag: Tag; // usa o type Tag do tagApi
  titulo: string;
  video: string;
  professorId?: number;
  usuariosFavoritosIds?: number[];
}

const BASE_URL = "http://localhost:8080/videos";

const handleResponse = async (response: Response) => {
  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || "Erro desconhecido");
  }
  return response.status === 204 ? null : response.json();
};

export const VideoAPI = {
  create: async (video: Video) => {
    const response = await fetch(`${BASE_URL}/create`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(video),
    });
    return handleResponse(response);
  },

  getAll: async (): Promise<Video[]> => {
    const response = await fetch(`${BASE_URL}/all`);
    return handleResponse(response);
  },

  getById: async (id: number): Promise<Video> => {
    const response = await fetch(`${BASE_URL}/by-id?id=${id}`);
    return handleResponse(response);
  },

  getByTitulo: async (titulo: string): Promise<Video[]> => {
    const response = await fetch(`${BASE_URL}/by-titulo?titulo=${encodeURIComponent(titulo)}`);
    return handleResponse(response);
  },

  searchByTitulo: async (palavraChave: string): Promise<Video[]> => {
    const response = await fetch(`${BASE_URL}/search?q=${encodeURIComponent(palavraChave)}`);
    return handleResponse(response);
  },

  getByTag: async (tag: Tag): Promise<Video[]> => {
    const response = await fetch(`${BASE_URL}/by-tag?tag=${tag}`);
    return handleResponse(response);
  },

  deleteById: async (id: number) => {
    const response = await fetch(`${BASE_URL}/delete?id=${id}`, {
      method: "POST",
    });
    return handleResponse(response);
  },

  getByCategoria: async (categoriaId: number): Promise<Video[]> => {
    const response = await fetch(`${BASE_URL}/by-categoria?categoriaId=${categoriaId}`);
    return handleResponse(response);
  },
};

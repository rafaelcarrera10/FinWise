// src/lib/api/video.ts
let BASE_URL = "http://localhost:8080/videos";

export type Video = {
  id?: number;
  titulo: string;
  descricao: string;
  url: string;
  tag: string; // TagEnum
};

// ---------------- Função genérica ----------------
async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: { "Content-Type": "application/json" },
      ...options
    });

    if (!response.ok) {
      throw new Error(await response.text());
    }

    const text = await response.text();
    return text ? (JSON.parse(text) as T) : null;

  } catch (err) {
    console.error("Erro API VIDEO:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const VideoAPI = {
  create: (video: Video) =>
    request<Video>("/create", { method: "POST", body: JSON.stringify(video) }),

  getAll: () =>
    request<Video[]>("/all", { method: "GET" }),

  getById: (id: number) =>
    request<Video>(`/by-id?id=${id}`, { method: "GET" }),

  getByTitulo: (titulo: string) =>
    request<Video>(`/by-titulo?titulo=${encodeURIComponent(titulo)}`),

  searchByTitulo: (q: string) =>
    request<Video[]>(`/search?q=${encodeURIComponent(q)}`),

  getByTag: (tag: string) =>
    request<Video[]>(`/by-tag?tag=${encodeURIComponent(tag)}`),

  delete: (id: number) =>
    request<void>(`/delete?id=${id}`, { method: "POST" })
};

const BASE_URL = import.meta.env.VITE_API_BASE_URL + '/contents';

// Tipos
export type Content = {
  id?: number;
  title: string;
  url?: string;
  description?: string;
  teacherId?: number;
  [key: string]: any; // campos opcionais
};

// Função genérica de requisição
async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: { 'Content-Type': 'application/json' },
      ...options,
    });

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Erro ${response.status}: ${errorText}`);
    }

    return response.status !== 204 ? await response.json() : null;
  } catch (error) {
    console.error('Erro ao conectar ao backend:', error);
    throw error;
  }
}

export const ContentAPI = {
  create: (content: Content) =>
    request<Content>('/create', {
      method: 'POST',
      body: JSON.stringify(content),
    }),

  getByTitle: (title: string) =>
    request<Content>(`/by-title?title=${encodeURIComponent(title)}`, { method: 'GET' }),

  getByTitleContaining: (text: string) =>
    request<Content[]>(`/by-title-containing?text=${encodeURIComponent(text)}`, { method: 'GET' }),

  getByDescriptionContaining: (text: string) =>
    request<Content[]>(`/by-description-containing?text=${encodeURIComponent(text)}`, { method: 'GET' }),

  existsByTitle: (title: string) =>
    request<boolean>(`/exists-by-title?title=${encodeURIComponent(title)}`, { method: 'GET' }),

  existsByUrl: (url: string) =>
    request<boolean>('/exists-by-url', {
      method: 'POST',
      body: JSON.stringify(url),
    }),

  countContents: () => request<number>('/count', { method: 'GET' }),

  getAllOrderByTitleAsc: () => request<Content[]>('/all-order-by-title-asc', { method: 'GET' }),
  getAllOrderByTitleDesc: () => request<Content[]>('/all-order-by-title-desc', { method: 'GET' }),
  getAllOrderByIdDesc: () => request<Content[]>('/all-order-by-id-desc', { method: 'GET' }),
  getAllOrderByIdAsc: () => request<Content[]>('/all-order-by-id-asc', { method: 'GET' }),

  getByUrls: (urls: string[]) =>
    request<Content[]>('/by-urls', {
      method: 'POST',
      body: JSON.stringify(urls),
    }),

  getByTeacherId: (teacherId: number) =>
    request<Content[]>(`/by-teacher-id?teacherId=${teacherId}`, { method: 'GET' }),

  updateTitle: (id: number, newTitle: string) =>
    request<Content>(`/update-title?id=${id}&newTitle=${encodeURIComponent(newTitle)}`, { method: 'PUT' }),

  updateUrl: (id: number, newUrl: string) =>
    request<Content>(`/update-url?id=${id}&newUrl=${encodeURIComponent(newUrl)}`, { method: 'PUT' }),

  updateDescription: (id: number, newDescription: string) =>
    request<Content>(`/update-description?id=${id}&newDescription=${encodeURIComponent(newDescription)}`, { method: 'PUT' }),

  deleteByTitle: (title: string) =>
    request<void>(`/delete-by-title?title=${encodeURIComponent(title)}`, { method: 'DELETE' }),

  deleteByUrl: (url: string) =>
    request<void>(`/delete-by-url?url=${encodeURIComponent(url)}`, { method: 'DELETE' }),

  searshByMethod: (searchText: string) =>
    request<Content[]>(`/search-by-method?searchText=${encodeURIComponent(searchText)}`, { method: 'GET' }),

  searchByText: (searchText: string) =>
    request<Content[]>(`/search-by-text?searchText=${encodeURIComponent(searchText)}`, { method: 'GET' }),

  getPaginatedContents: (id: number, page: number, size: number) =>
    request<Content[]>(`/paginated?id=${id}&page=${page}&size=${size}`, { method: 'GET' }),

  getLatestContents: (limit: number) =>
    request<Content[]>(`/latest?limit=${limit}`, { method: 'GET' }),
};

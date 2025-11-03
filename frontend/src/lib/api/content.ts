// src/api/content.ts
const BASE_URL = import.meta.env.VITE_API_BASE_URL + '/contents';

// Tipagem do conteúdo
export type Content = {
  id?: number;
  title: string;
  description?: string;
  url?: string;
  userId?: number; // professor ou autor
  [key: string]: any; // outros campos opcionais
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

    // Trata respostas sem corpo (200/204)
    const text = await response.text();
    return text ? (JSON.parse(text) as T) : null;
  } catch (error) {
    console.error('Erro ao conectar ao backend:', error);
    throw error;
  }
}

export const ContentAPI = {
  // Criar conteúdo
  create: (content: Content) =>
    request<Content>('/create', {
      method: 'POST',
      body: JSON.stringify(content),
    }),

  // Buscar por título (case insensitive)
  getByTitle: (title: string) =>
    request<Content[]>(`/by-title?title=${encodeURIComponent(title)}`, { method: 'GET' }),

  // Buscar por parte do título (case insensitive)
  getByTitleContaining: (titlePart: string) =>
    request<Content[]>(`/title-containing?titlePart=${encodeURIComponent(titlePart)}`, {
      method: 'GET',
    }),

  // Buscar por parte da descrição (case insensitive)
  getByDescriptionContaining: (descPart: string) =>
    request<Content[]>(`/description-containing?descriptionPart=${encodeURIComponent(descPart)}`, {
      method: 'GET',
    }),

  // Verifica existência por título
  existsByTitle: (title: string) =>
    request<boolean>(`/exists-by-title?title=${encodeURIComponent(title)}`, { method: 'GET' }),

  // Verifica existência por URL
  existsByUrl: (url: string) =>
    request<boolean>(`/exists-by-url?url=${encodeURIComponent(url)}`, { method: 'GET' }),

  // Contagem total
  count: () => request<number>('/count', { method: 'GET' }),

  // Ordenações
  getAllOrderByTitleAsc: () => request<Content[]>('/all-order-by-title-asc', { method: 'GET' }),
  getAllOrderByTitleDesc: () => request<Content[]>('/all-order-by-title-desc', { method: 'GET' }),
  getAllOrderByIdAsc: () => request<Content[]>('/all-order-by-id-asc', { method: 'GET' }),
  getAllOrderByIdDesc: () => request<Content[]>('/all-order-by-id-desc', { method: 'GET' }),

  // Buscar por múltiplas URLs
  getByUrls: (urls: string[]) =>
    request<Content[]>('/by-urls', {
      method: 'POST',
      body: JSON.stringify(urls),
    }),

  // Buscar por ID do professor
  getByTeacherId: (teacherId: number) =>
    request<Content[]>(`/by-teacher-id?teacherId=${teacherId}`, { method: 'GET' }),

  // Atualizações
  updateTitle: (id: number, newTitle: string) =>
    request<number>(`/update-title?id=${id}&newTitle=${encodeURIComponent(newTitle)}`, {
      method: 'POST',
    }),

  updateUrl: (id: number, newUrl: string) =>
    request<number>(`/update-url?id=${id}&newUrl=${encodeURIComponent(newUrl)}`, {
      method: 'POST',
    }),

  updateDescription: (id: number, newDescription: string) =>
    request<number>(`/update-description?id=${id}&newDescription=${encodeURIComponent(newDescription)}`, {
      method: 'POST',
    }),

  // Remoções
  deleteByTitle: (title: string) =>
    request<number>(`/delete-by-title?title=${encodeURIComponent(title)}`, { method: 'POST' }),

  deleteByUrl: (url: string) =>
    request<number>(`/delete-by-url?url=${encodeURIComponent(url)}`, { method: 'POST' }),

  // Buscas de texto
  searchByMethod: (searchText: string) =>
    request<Content[]>(`/search-by-method?searchText=${encodeURIComponent(searchText)}`, {
      method: 'GET',
    }),

  searchByText: (searchText: string) =>
    request<Content[]>(`/search-by-text?searchText=${encodeURIComponent(searchText)}`, {
      method: 'GET',
    }),

  // Paginação
  getPaginated: (id: number, page: number, size: number) =>
    request<Content[]>(`/paginated?id=${id}&page=${page}&size=${size}`, { method: 'GET' }),

  // Conteúdos mais recentes
  getLatest: (limit: number) =>
    request<Content[]>(`/latest?limit=${limit}`, { method: 'GET' }),

  // --- OPCIONAL ---
  // Criptografia e descriptografia
  encrypt: (data: string) =>
    request<string>('/encrypt', { method: 'POST', body: JSON.stringify(data) }),

  decrypt: (data: string) =>
    request<string>('/decrypt', { method: 'POST', body: JSON.stringify(data) }),
};

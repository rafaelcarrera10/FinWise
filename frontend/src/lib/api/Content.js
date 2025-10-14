const BASE_URL = import.meta.env.VITE_API_BASE_URL + '/contents';

// Função genérica de requisição
async function request(endpoint, options = {}) {
    try {
        const response = await fetch('${BASE_URL}${endpoint}', {
            headers: { 'Content-Type': 'application/json' },
            ...options
        });
        if (!response.ok) {
            throw new Error(`Erro ${response.status}: ${response.statusText}`);
        }

        // Alguns endpoints podem não ter corpo (204)
            return response.status !== 204 ? await response.json() : null;

        } catch (error) {
            console.error('Erro ao conectar ao backend:', error);
            throw error;
        }
    }

export const ContentAPI = {
    // Criar novo conteúdo
    create: (content) =>
        request('/create', {
            method: 'POST',
            body: JSON.stringify(content)
        }),
    
    // Buscar conteúdo por título
    getByTitle: (title) =>
        request(`/by-title?title=${
            encodeURIComponent(title)}`, 
            { method: 'GET' }),

    // Buscar conteúdo por título contendo o texto (case insensitive)
    getByTitleContaining: (text) =>
        request(`/by-title-containing?text=${
            encodeURIComponent(text)}`, 
            { method: 'GET' }),

    // Buscar conteúdo por descrição contendo o texto (case insensitive)
    getByDescriptionContaining: (text) =>
        request(`/by-description-containing?text=${
            encodeURIComponent(text)}`,
            { method: 'GET' }),
    
    // Verifica se existe conteúdo com o título especificado
    existsByTitle: (title) =>
        request(`/exists-by-title?title=${
            encodeURIComponent(title)}`,
            { method: 'GET' }),

    // Verifica se existe conteúdo com a URL especificada
    existsByUrl: (url) =>
        request(`/exists-by-url`, {
            method: 'POST',
            body: JSON.stringify(url),
        }),

    // Conta quantos conteúdos existem no sistema
    countContents: () =>
        request('/count', { method: 'GET' }),

    // Busca conteúdos ordenados por título (A-Z)
    getAllOrderByTitleAsc: () =>
        request('/all-order-by-title-asc', { method: 'GET' }),

    // Busca conteúdos ordenados por título (Z-A)
    getAllOrderByTitleDesc: () =>
        request('/all-order-by-title-desc', { method: 'GET' }),

    // Busca conteúdos ordenados por ID (mais recentes primeiro)
    getAllOrderByIdDesc: () =>
        request('/all-order-by-id-desc', { method: 'GET' }),

    // Busca conteúdos ordenados por ID (mais antigos primeiro)
    getAllOrderByIdAsc: () =>
        request('/all-order-by-id-asc', { method: 'GET' }),

    // Busca conteúdo por multiplas URL
    getByUrls: (urls) =>
        request('/by-urls', {
            method: 'POST',
            body: JSON.stringify(urls),
        }),

    // Busca conteúdos por ID do professor
    getByTeacherId: (teacherId) =>
        request(`/by-teacher-id?teacherId=${teacherId}`, {
            method: 'GET',
        }),

    // Atualizar o título de um conteúdo por ID
    updateTitle: (id, newTitle) =>
        request(`/update-title?id=${id}&newTitle=${encodeURIComponent(newTitle)}`, {
            method: 'PUT',
        }),

    // Atualiza a URL de um conteúdo por ID
    updateUrl: (id, newUrl) =>
        request(`/update-url?id=${id}&newUrl=${encodeURIComponent(newUrl)}`, {
            method: 'PUT',
        }),

    // Atualiza a descrição de um conteúdo por ID
    updateDescription: (id, newDescription) =>
        request(`/update-description?id=${id}&newDescription=${encodeURIComponent(newDescription)}`, {
            method: 'PUT',
        }),

    // Remove conteúdo por título
    deleteByTitle: (title) =>
        request(`/delete-by-title?title=${encodeURIComponent(title)}`, {
            method: 'DELETE',
        }),

    // Remove conteúdo por URL
    deleteByUrl: (url) =>
        request(`/delete-by-url?url=${encodeURIComponent(url)}`, {
            method: 'DELETE',
        }),

    // Busca conteúdos que contenham o texto em título, URL ou descrição
    searshByMethod: (searchText) =>
        request(`/search-by-method?searchText=${encodeURIComponent(searchText)}`, {
            method: 'GET',
        }),

    // Buscar conteudos que contenham um texto na descrição ou no título (case insensitive)
    searchByText: (searchText) =>
        request(`/search-by-text?searchText=${encodeURIComponent(searchText)}`, {
            method: 'GET',
        }),

    // Paginação e Ordenação Avançada
    getPaginatedContents: (id, page, size) =>
        request(`/paginated?id=${id}&page=${page}&size=${size}`, {
            method: 'GET',
        }),

    // Buscar os conteúdos mais recentes
    getLatestContents: (limit) =>
        request(`/latest?limit=${limit}`, {
            method: 'GET',
        }),
};

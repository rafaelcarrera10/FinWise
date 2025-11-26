// src/lib/api/tagApi.ts

export type Tag = "INVESTIMENTO" | "ACOES" | "POUPANCA" | "ECONOMIA";

const BASE_URL = "http://localhost:8080/tags";

// Função genérica para tratar resposta
async function handleResponse<T>(response: Response): Promise<T> {
    if (!response.ok) {
        const text = await response.text();
        throw new Error(text || "Erro desconhecido na requisição");
    }
    return response.json();
}

export const TagAPI = {
    /**
     * Busca todas as tags disponíveis no enum
     */
    getAll: async (): Promise<Tag[]> => {
        try {
            const response = await fetch(BASE_URL);
            return handleResponse<Tag[]>(response);
        } catch (e) {
            console.error("Erro ao buscar tags:", e);
            throw e;
        }
    },

    /**
     * Busca uma tag pelo ordinal
     */
    getById: async (id: number): Promise<Tag> => {
        try {
            const response = await fetch(`${BASE_URL}/${id}`);
            return handleResponse<Tag>(response);
        } catch (e) {
            console.error(`Erro ao buscar tag ID ${id}:`, e);
            throw e;
        }
    },
};

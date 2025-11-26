// src/api/publicacaoApi.ts

export enum TagEnum {
  INVESTIMENTO = "INVESTIMENTO",
  ACOES = "ACOES",
  POUPANCA = "POUPANCA",
  ECONOMIA = "ECONOMIA"
}

// DTO vindo do backend
export interface PublicacaoDTO {
  id: number;
  titulo: string;
  descricao: string;
  tag: TagEnum;
  fotos: string[];  // Base64
  texto: string;
}

// Modelo usado para criar publicação (sem id)
export type PublicacaoCreate = Omit<PublicacaoDTO, "id">;

const BASE_URL = "http://localhost:8080/publicacoes";

export const publicacaoApi = {

  // CREATE
  create: async (publicacao: PublicacaoCreate): Promise<PublicacaoDTO> => {
    const res = await fetch(`${BASE_URL}/create`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(publicacao),
    });

    if (!res.ok) {
      throw new Error("Erro ao criar publicação");
    }

    return res.json();
  },

  // GET ALL (recebe um DTO da API)
  getAll: async (): Promise<PublicacaoDTO[]> => {
    const res = await fetch(`${BASE_URL}/all`);
    if (!res.ok) return [];
    return res.json();
  },

  // GET BY ID
  getById: async (id: number): Promise<PublicacaoDTO | null> => {
    const res = await fetch(`${BASE_URL}/by-id?id=${id}`);
    return res.ok ? res.json() : null;
  },

  // GET BY TITLE
  getByTitulo: async (titulo: string): Promise<PublicacaoDTO | null> => {
    const res = await fetch(`${BASE_URL}/by-titulo?titulo=${encodeURIComponent(titulo)}`);
    return res.ok ? res.json() : null;
  },

  // SEARCH BY TITLE
  searchByTitulo: async (q: string): Promise<PublicacaoDTO[]> => {
    const res = await fetch(`${BASE_URL}/search?q=${encodeURIComponent(q)}`);
    return res.ok ? res.json() : [];
  },

  // GET BY TAG
  getByTag: async (tag: TagEnum): Promise<PublicacaoDTO[]> => {
    const res = await fetch(`${BASE_URL}/by-tag?tag=${tag}`);
    return res.ok ? res.json() : [];
  },

  // DELETE
  deleteById: async (id: number): Promise<void> => {
    await fetch(`${BASE_URL}/delete?id=${id}`, {
      method: "POST",
    });
  },
};

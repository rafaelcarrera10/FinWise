// src/api/publicacaoApi.ts

export enum TagEnum {
  INVESTIMENTO = "INVESTIMENTO",
    ACOES = "ACOES",
    POUPANCA = "POUPANCA",
    ECONOMIA = "ECONOMIA"
}

export interface Publicacao {
  id?: number;
  titulo: string;
  descricao: string;
  tag: TagEnum;
  fotos: string[]; // Base64 strings
  texto: string;
}

const BASE_URL = "http://localhost:8080/publicacoes";

export const publicacaoApi = {
  create: async (publicacao: Omit<Publicacao, "id">): Promise<Publicacao> => {
    const res = await fetch(`${BASE_URL}/create`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(publicacao),
    });
    return res.json();
  },

  getAll: async (): Promise<Publicacao[]> => {
    const res = await fetch(`${BASE_URL}/all`);
    return res.json();
  },

  getById: async (id: number): Promise<Publicacao | null> => {
    const res = await fetch(`${BASE_URL}/by-id?id=${id}`);
    return res.ok ? res.json() : null;
  },

  getByTitulo: async (titulo: string): Promise<Publicacao | null> => {
    const res = await fetch(`${BASE_URL}/by-titulo?titulo=${encodeURIComponent(titulo)}`);
    return res.ok ? res.json() : null;
  },

  searchByTitulo: async (q: string): Promise<Publicacao[]> => {
    const res = await fetch(`${BASE_URL}/search?q=${encodeURIComponent(q)}`);
    return res.ok ? res.json() : [];
  },

  getByTag: async (tag: TagEnum): Promise<Publicacao[]> => {
    const res = await fetch(`${BASE_URL}/by-tag?tag=${tag}`);
    return res.ok ? res.json() : [];
  },

  deleteById: async (id: number): Promise<void> => {
    await fetch(`${BASE_URL}/delete?id=${id}`, { method: "POST" });
  },
};

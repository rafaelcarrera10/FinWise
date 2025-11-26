// transacaoApi.ts

export interface Transacao {
  id?: number;
  valor: number;
  dataInicial: string; // YYYY-MM-DD
  dataFinal?: string; // YYYY-MM-DD
  descricao: string;
  observacao?: string;
  repeticao: "UNICA" | "SEMANAL" | "MENSAL" | "ANUAL";
  categoria: { id: number };
  conta: { id: number };
}

const BASE_URL = "http://localhost:8080/transacoes";

const handleResponse = async (response: Response) => {
  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || "Erro desconhecido");
  }
  return response.status === 204 ? null : response.json();
};

export const TransacaoAPI = {
  // Criar transação
  create: async (transacao: Transacao) => {
    const response = await fetch(`${BASE_URL}/create`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(transacao),
    });
    return handleResponse(response);
  },

  // Buscar transação por ID
  getById: async (id: number) => {
    const response = await fetch(`${BASE_URL}/by-id/${id}`);
    return handleResponse(response);
  },

  // Listar transações de uma conta
  getByConta: async (contaId: number) => {
    const response = await fetch(`${BASE_URL}/conta/${contaId}`);
    return handleResponse(response);
  },

  // Atualizar transação
  update: async (id: number, transacao: Partial<Transacao>) => {
    const response = await fetch(`${BASE_URL}/update/${id}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(transacao),
    });
    return handleResponse(response);
  },

  // Deletar transação
  delete: async (id: number) => {
    const response = await fetch(`${BASE_URL}/delete/${id}`, {
      method: "DELETE",
    });
    return handleResponse(response);
  },
};

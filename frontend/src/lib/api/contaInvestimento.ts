// ContaInvestimento.ts
import type {Tag} from '$lib/api/tag'
// -------------------- MODELO --------------------
export interface ContaInvestimento {
  id?: number;
  actionName: string;
  value: number;
  quantity: number;
  usuarioId: number;
  tag?: Tag; //  <<< ADICIONE ISTO
}

// -------------------- CONFIGURAÇÃO --------------------
const BASE_URL = "http://localhost:8080/api/contas-investimento";
const headers = { "Content-Type": "application/json" };

// Função genérica para tratar respostas HTTP
const handleResponse = async <T>(response: Response): Promise<T> => {
  if (!response.ok) {
    const text = await response.text();
    throw new Error(text || "Erro desconhecido");
  }
  // Retorna JSON ou lança erro se status não for OK
  return response.status === 204 ? (null as any) : (await response.json() as T);
};

// -------------------- API --------------------
export const ContaInvestimentoAPI = {

  // Criar nova conta
  create: async (conta: ContaInvestimento): Promise<ContaInvestimento> => {
    const response = await fetch(`${BASE_URL}`, {
      method: "POST",
      headers,
      body: JSON.stringify(conta),
    });
    return handleResponse<ContaInvestimento>(response);
  },

  // Listar todas as contas
  getAll: async (): Promise<ContaInvestimento[]> => {
    const response = await fetch(`${BASE_URL}`);
    return handleResponse<ContaInvestimento[]>(response);
  },

  // Buscar por ID
  getById: async (id: number): Promise<ContaInvestimento> => {
    const response = await fetch(`${BASE_URL}/${id}`);
    return handleResponse<ContaInvestimento>(response);
  },

  // Buscar por nome da ação
  getByActionName: async (actionName: string): Promise<ContaInvestimento[]> => {
    const response = await fetch(`${BASE_URL}/acao/${encodeURIComponent(actionName)}`);
    return handleResponse<ContaInvestimento[]>(response);
  },

  // Buscar por ID do usuário
  getByUsuarioId: async (usuarioId: number): Promise<ContaInvestimento[]> => {
    const response = await fetch(`${BASE_URL}/usuario/${usuarioId}`);
    return handleResponse<ContaInvestimento[]>(response);
  },

  // Atualizar conta
  update: async (id: number, conta: ContaInvestimento): Promise<ContaInvestimento> => {
    const response = await fetch(`${BASE_URL}/${id}`, {
      method: "PUT",
      headers,
      body: JSON.stringify(conta),
    });
    return handleResponse<ContaInvestimento>(response);
  },

  // Deletar conta
  deleteById: async (id: number): Promise<void> => {
    const response = await fetch(`${BASE_URL}/${id}`, { method: "DELETE" });
    if (!response.ok) {
      const text = await response.text();
      throw new Error(text || "Erro desconhecido");
    }
    // void, nada a retornar
  },

};

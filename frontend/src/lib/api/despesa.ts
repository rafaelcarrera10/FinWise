// src/lib/api/despesa.ts
let BASE_URL = "http://localhost:8080/despesas";

export type Despesa = {
  id?: number;
  valor: number;
  dataInicial: string; // formato ISO
  dataFinal?: string;
  descricao: string;
  observacao?: string;
  repeticao?: "UNICA" | "DIARIA" | "SEMANAL" | "MENSAL"; // adapte conforme RepeticaoEnum
  status: boolean;
  categoriaId?: number;
  contaId?: number;
  cartaoCreditoId?: number;
};

// ---------------- Função genérica ----------------
async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  try {
    const response = await fetch(`${BASE_URL}${endpoint}`, {
      headers: { "Content-Type": "application/json" },
      ...options
    });

    if (!response.ok) throw new Error(await response.text());

    const text = await response.text();
    return text ? JSON.parse(text) : null;

  } catch (err) {
    console.error("Erro API DESPESA:", err);
    throw err;
  }
}

// ---------------- API ----------------
export const DespesaAPI = {
  create: (data: Despesa) =>
    request<Despesa>("", { method: "POST", body: JSON.stringify(data) }),

  getById: (id: number) =>
    request<Despesa>(`/${id}`),

  getByConta: (contaId: number) =>
    request<Despesa[]>(`/conta/${contaId}`),

  getByCategoria: (categoriaId: number) =>
    request<Despesa[]>(`/categoria/${categoriaId}`),

  update: (id: number, data: Partial<Despesa>) =>
    request<Despesa>(`/${id}`, { method: "PUT", body: JSON.stringify(data) }),

  delete: (id: number) =>
    request<void>(`/${id}`, { method: "DELETE" }),
};

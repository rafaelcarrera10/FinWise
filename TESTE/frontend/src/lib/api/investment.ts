// src/api/investments.ts
let BASE_URL = 'http://localhost:8080/investment-accounts';

// Tipos
export type Investment = {
  id?: number;
  actionName: string;
  value: number;
  quantity: number;
  userId?: number;
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

    const text = await response.text();
    return text ? (JSON.parse(text) as T) : null;
  } catch (error) {
    console.error('Erro ao conectar ao backend:', error);
    throw error;
  }
}

export const InvestmentAPI = {
  // Buscar investimentos por nome da ação
  getByActionName: (actionName: string) =>
    request<Investment[]>(`/by-action-name?actionName=${encodeURIComponent(actionName)}`, { method: 'GET' }),

  // Verifica se existe investimento com a ação especificada
  existsByActionName: (actionName: string) =>
    request<boolean>(`/exists-by-action-name?actionName=${encodeURIComponent(actionName)}`, { method: 'GET' }),

  // Buscar investimentos por valor
  getByValueGreaterThan: (minValue: number) =>
    request<Investment[]>(`/value-greater-than?minValue=${minValue}`, { method: 'GET' }),

  getByValueLessThan: (maxValue: number) =>
    request<Investment[]>(`/value-less-than?maxValue=${maxValue}`, { method: 'GET' }),

  getByValueBetween: (minValue: number, maxValue: number) =>
    request<Investment[]>(`/value-between?minValue=${minValue}&maxValue=${maxValue}`, { method: 'GET' }),

  // Buscar investimentos por quantidade
  getByQuantityGreaterThan: (minQuantity: number) =>
    request<Investment[]>(`/quantity-greater-than?minQuantity=${minQuantity}`, { method: 'GET' }),

  getByQuantityLessThan: (maxQuantity: number) =>
    request<Investment[]>(`/quantity-less-than?maxQuantity=${maxQuantity}`, { method: 'GET' }),

  getByQuantityBetween: (minQuantity: number, maxQuantity: number) =>
    request<Investment[]>(`/quantity-between?minQuantity=${minQuantity}&maxQuantity=${maxQuantity}`, { method: 'GET' }),

  // Contagem total de investimentos
  count: () => request<number>('/count', { method: 'GET' }),

  // Ordenações
  getAllOrderByActionNameAsc: () => request<Investment[]>('/order-by-action-name-asc', { method: 'GET' }),
  getAllOrderByValueDesc: () => request<Investment[]>('/order-by-value-desc', { method: 'GET' }),
  getAllOrderByQuantityDesc: () => request<Investment[]>('/order-by-quantity-desc', { method: 'GET' }),

  // Paginação
  getPaginated: (page: number, size: number) =>
    request<Investment[]>(`/paginated?page=${page}&size=${size}`, { method: 'GET' }),

  // Agrupamento
  getGroupedByAction: () => request<Investment[]>('/grouped-by-action', { method: 'GET' }),

  // Atualizações
  updateValue: (id: number, newValue: number) =>
    request<Investment>(`/update-value?id=${id}&newValue=${newValue}`, { method: 'GET' }),

  updateQuantity: (id: number, newQuantity: number) =>
    request<Investment>(`/update-quantity?id=${id}&newQuantity=${newQuantity}`, { method: 'GET' }),

  updateActionName: (id: number, newActionName: string) =>
    request<Investment>(`/update-action-name?id=${id}&newActionName=${encodeURIComponent(newActionName)}`, { method: 'GET' }),

  // Alterações de quantidade
  addToQuantity: (id: number, amount: number) =>
    request<Investment>(`/add-to-quantity?id=${id}&amount=${amount}`, { method: 'GET' }),

  subtractFromQuantity: (id: number, amount: number) =>
    request<Investment>(`/subtract-from-quantity?id=${id}&amount=${amount}`, { method: 'GET' }),

  // Remoção
  deleteByActionName: (actionName: string) =>
    request<void>(`/delete-by-action-name?actionName=${encodeURIComponent(actionName)}`, { method: 'GET' }),

  // Cálculos por usuário
  getTotalValueByUserId: (userId: number) =>
    request<number>(`/total-value-by-user?userId=${userId}`, { method: 'GET' }),

  getTotalQuantityByUserId: (userId: number) =>
    request<number>(`/total-quantity-by-user?userId=${userId}`, { method: 'GET' }),

  getMaxValueByUserId: (userId: number) =>
    request<number>(`/max-value-by-user?userId=${userId}`, { method: 'GET' }),

  getMinValueByUserId: (userId: number) =>
    request<number>(`/min-value-by-user?userId=${userId}`, { method: 'GET' }),

  getMaxQuantityByUserId: (userId: number) =>
    request<number>(`/max-quantity-by-user?userId=${userId}`, { method: 'GET' }),

  getMinQuantityByUserId: (userId: number) =>
    request<number>(`/min-quantity-by-user?userId=${userId}`, { method: 'GET' }),

  // Criptografia (opcional)
  encrypt: (data: string) =>
    request<string>('/encrypt', { method: 'POST', body: JSON.stringify(data) }),

  decrypt: (data: string) =>
    request<string>('/decrypt', { method: 'POST', body: JSON.stringify(data) }),
};

// src/lib/api/investments.ts
const API_URL = import.meta.env.VITE_API_URL + '/investment-accounts';

// Tipos
export type Investment = {
  id?: number;
  actionName: string;
  value: number;
  quantity: number;
  userId?: number;
  [key: string]: any; // outros campos opcionais
};

export type PaginatedRequest = { page: number; size: number };

async function request<T>(endpoint: string, options: RequestInit = {}): Promise<T | null> {
  const res = await fetch(`${API_URL}${endpoint}`, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });

  if (!res.ok) {
    const errorText = await res.text();
    throw new Error(`Erro na API (${res.status}): ${errorText}`);
  }

  return res.status !== 204 ? res.json() : null;
}

export const InvestmentAPI = {
  // Buscar investimentos por nome da ação
  getByActionName: (actionName: string) =>
    request<Investment[]>(`/by-action-name`, {
      method: 'POST',
      body: JSON.stringify(actionName),
    }),

  existsByActionName: (actionName: string) =>
    request<boolean>(`/exists-by-action-name`, {
      method: 'POST',
      body: JSON.stringify(actionName),
    }),

  getByValueGreaterThan: (minValue: number) =>
    request<Investment[]>(`/value-greater-than`, {
      method: 'POST',
      body: JSON.stringify(minValue),
    }),

  getByValueLessThan: (maxValue: number) =>
    request<Investment[]>(`/value-less-than`, {
      method: 'POST',
      body: JSON.stringify(maxValue),
    }),

  getByValueBetween: (minValue: number, maxValue: number) =>
    request<Investment[]>(`/value-between`, {
      method: 'POST',
      body: JSON.stringify({ minValue, maxValue }),
    }),

  getByQuantityGreaterThan: (minQuantity: number) =>
    request<Investment[]>(`/quantity-greater-than`, {
      method: 'POST',
      body: JSON.stringify(minQuantity),
    }),

  getByQuantityLessThan: (maxQuantity: number) =>
    request<Investment[]>(`/quantity-less-than`, {
      method: 'POST',
      body: JSON.stringify(maxQuantity),
    }),

  getByQuantityBetween: (minQuantity: number, maxQuantity: number) =>
    request<Investment[]>(`/quantity-between`, {
      method: 'POST',
      body: JSON.stringify({ minQuantity, maxQuantity }),
    }),

  countInvestments: () => request<number>(`/count`, { method: 'GET' }),

  getAllOrderByActionNameAsc: () => request<Investment[]>(`/order-by-action-name-asc`, { method: 'GET' }),
  getAllOrderByValueDesc: () => request<Investment[]>(`/order-by-value-desc`, { method: 'GET' }),
  getAllOrderByQuantityDesc: () => request<Investment[]>(`/order-by-quantity-desc`, { method: 'GET' }),

  getPaginated: (page: number, size: number) =>
    request<Investment[]>(`/paginated`, {
      method: 'POST',
      body: JSON.stringify({ page, size }),
    }),

  getInvestmentsGroupedByAction: () => request<Record<string, Investment[]>>(`/grouped-by-action`, { method: 'GET' }),

  updateInvestmentValue: (id: number, newValue: number) =>
    request<Investment>(`/update-value`, {
      method: 'POST',
      body: JSON.stringify({ id, newValue }),
    }),

  updateInvestmentQuantity: (id: number, newQuantity: number) =>
    request<Investment>(`/update-quantity`, {
      method: 'POST',
      body: JSON.stringify({ id, newQuantity }),
    }),

  updateInvestmentActionName: (id: number, newActionName: string) =>
    request<Investment>(`/update-action-name`, {
      method: 'POST',
      body: JSON.stringify({ id, newActionName }),
    }),

  addToInvestmentQuantity: (id: number, amount: number) =>
    request<Investment>(`/add-to-quantity`, {
      method: 'POST',
      body: JSON.stringify({ id, amount }),
    }),

  subtractFromInvestmentQuantity: (id: number, amount: number) =>
    request<Investment>(`/subtract-from-quantity`, {
      method: 'POST',
      body: JSON.stringify({ id, amount }),
    }),

  deleteByActionName: (actionName: string) =>
    request<void>(`/delete-by-action-name`, {
      method: 'POST',
      body: JSON.stringify(actionName),
    }),

  getTotalInvestmentValueByUserId: (userId: number) =>
    request<number>(`/total-value-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),

  getTotalQuantityByUserId: (userId: number) =>
    request<number>(`/total-quantity-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),

  getMaxValueByUserId: (userId: number) =>
    request<number>(`/max-value-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),

  getMinValueByUserId: (userId: number) =>
    request<number>(`/min-value-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),

  getMaxQuantityByUserId: (userId: number) =>
    request<number>(`/max-quantity-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),

  getMinQuantityByUserId: (userId: number) =>
    request<number>(`/min-quantity-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),
};

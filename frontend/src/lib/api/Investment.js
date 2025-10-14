const API_URL = import.meta.env.VITE_API_URL + '/investment-accounts';

async function request(endpoint, options = {}) {
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

  // Buscar investimentos por nome da ação (case insensitive)
  getByActionName: (actionName) =>
    request(`/by-action-name`, {
      method: 'POST',
      body: JSON.stringify(actionName),
    }),

  // Verifica se existe investimento com a ação especificada
  existsByActionName: (actionName) =>
    request(`/exists-by-action-name`, {
      method: 'POST',
      body: JSON.stringify(actionName),
    }),

  // Buscar investimentos com valor maior que o especificado
  getByValueGreaterThan: (minValue) =>
    request(`/value-greater-than`, {
      method: 'POST',
      body: JSON.stringify(minValue),
    }),

  // Buscar investimentos com valor menor que o especificado
  getByValueLessThan: (maxValue) =>
    request(`/value-less-than`, {
      method: 'POST',
      body: JSON.stringify(maxValue),
    }),

  // Buscar investimentos com valor entre dois valores
  getByValueBetween: (minValue, maxValue) =>
    request(`/value-between`, {
      method: 'POST',
      body: JSON.stringify({ minValue, maxValue }),
    }),

  // Buscar investimentos com quantidade maior que a especificada
  getByQuantityGreaterThan: (minQuantity) =>
    request(`/quantity-greater-than`, {
      method: 'POST',
      body: JSON.stringify(minQuantity),
    }),

  // Buscar investimentos com quantidade menor que a especificada
  getByQuantityLessThan: (maxQuantity) =>
    request(`/quantity-less-than`, {
      method: 'POST',
      body: JSON.stringify(maxQuantity),
    }),

  // Buscar investimentos com quantidade entre dois valores
  getByQuantityBetween: (minQuantity, maxQuantity) =>
    request(`/quantity-between`, {
      method: 'POST',
      body: JSON.stringify({ minQuantity, maxQuantity }),
    }),

  // Contar total de investimentos
  countInvestments: () =>
    request('/count', { method: 'GET' }),

  // Buscar todos os investimentos ordenados por nome da ação
  getAllOrderByActionNameAsc: () =>
    request('/order-by-action-name-asc', { method: 'GET' }),

  // Buscar todos os investimentos ordenados por valor (decrescente)
  getAllOrderByValueDesc: () =>
    request('/order-by-value-desc', { method: 'GET' }),

  // Buscar todos os investimentos ordenados por quantidade (decrescente)
  getAllOrderByQuantityDesc: () =>
    request('/order-by-quantity-desc', { method: 'GET' }),

  // Buscar invertimentos com paginação
  getPaginated: (page, size) =>
    request(`/paginated`, {
      method: 'POST',
      body: JSON.stringify({ page, size }),
    }),

  // Buscar investimentos agrupados por ação
  getInvestmentsGroupedByAction: () =>
    request('/grouped-by-action', { method: 'GET' }),

  // Atualizar o valor de um investimento por ID
  updateInvestmentValue: (id, newValue) =>
    request(`/update-value`, {
      method: 'POST',
      body: JSON.stringify({ id, newValue }),
    }),

  // Atualizar a quantidade de um investimento por ID
  updateInvestmentQuantity: (id, newQuantity) =>
    request(`/update-quantity`, {
      method: 'POST',
      body: JSON.stringify({ id, newQuantity }),
    }),

  // Atualizar o nome da ação de um investimento por ID
  updateInvestmentActionName: (id, newActionName) =>
    request(`/update-action-name`, {
      method: 'POST',
      body: JSON.stringify({ id, newActionName }),
    }),

  // Adiciona quantidade a um investimento
  addToInvestmentQuantity: (id, amount) =>
    request(`/add-to-quantity`, {
      method: 'POST',
      body: JSON.stringify({ id, amount }),
    }),

  // Subtrai quantidade de um investimento
  subtractFromInvestmentQuantity: (id, amount) =>
    request(`/subtract-from-quantity`, {
      method: 'POST',
      body: JSON.stringify({ id, amount }),
    }),

  // Remove investimento por nome da ação
  deleteByActionName: (actionName) =>
    request(`/delete-by-action-name`, {
      method: 'POST',
      body: JSON.stringify(actionName),
    }),

  // Calcula o valor total investido por usuário
  getTotalInvestmentValueByUserId: (userId) =>
    request(`/total-value-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),

  // Calcula a quantidade total de ações por usuário
  getTotalQuantityByUserId: (userId) =>
    request(`/total-quantity-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),

  // Encontra o maior valor entre todos os investimentos por usuário
  getMaxValueByUserId: (userId) =>
    request(`/max-value-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),

  // Encontra o menor valor entre todos os investimentos por usuário
  getMinValueByUserId: (userId) =>
    request(`/min-value-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),

  // Encontra a maior quantidade entre todos os investimentos por usuário
  getMaxQuantityByUserId: (userId) =>
    request(`/max-quantity-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),

  // Encontra a menor quantidade entre todos os investimentos por usuário
  getMinQuantityByUserId: (userId) =>
    request(`/min-quantity-by-user`, {
      method: 'POST',
      body: JSON.stringify(userId),
    }),
};

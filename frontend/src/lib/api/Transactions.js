// src/lib/api/transactions.js

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

export const TransactionsAPI = {
    // Salvar transação
    create: async (transaction) => {
        const res = await fetch(`${API_BASE_URL}/transactions/create`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(transaction)
        });
        return res.json();
    },

    // Buscar por ID
    getById: async (id) => {
        const res = await fetch(`${API_BASE_URL}/transactions/by-id`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(id)
        });
        return res.json();
    },

    // Buscar por tipo
    getByType: async (type) => {
        const res = await fetch(`${API_BASE_URL}/transactions/by-type`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(type)
        });
        return res.json();
    },

    // Buscar por valor mínimo
    getByValueGreaterThan: async (minValue) => {
        const res = await fetch(`${API_BASE_URL}/transactions/value-greater-than`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(minValue)
        });
        return res.json();
    },

    // Buscar por valor máximo
    getByValueLessThan: async (maxValue) => {
        const res = await fetch(`${API_BASE_URL}/transactions/value-less-than`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(maxValue)
        });
        return res.json();
    },

    // Buscar por descrição
    getByDescription: async (text) => {
        const res = await fetch(`${API_BASE_URL}/transactions/by-description`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(text)
        });
        return res.json();
    },

    // Atualizar valor
    updateValue: async (id, newValue, newDescription) => {
        const body = { id, newValue, newDescription };
        const res = await fetch(`${API_BASE_URL}/transactions/update-value`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });
        return res.json();
    },

    // Atualizar tipo
    updateType: async (id, newType) => {
        const body = { id, newType };
        const res = await fetch(`${API_BASE_URL}/transactions/update-type`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });
        return res.json();
    },

    // Atualizar descrição
    updateDescription: async (id, newDescription) => {
        const body = { id, newDescription };
        const res = await fetch(`${API_BASE_URL}/transactions/update-description`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(body)
        });
        return res.json();
    },

    // Deletar por ID
    deleteById: async (id) => {
        await fetch(`${API_BASE_URL}/transactions/delete-by-id`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(id)
        });
    },

    // Soma total
    sumAll: async () => {
        const res = await fetch(`${API_BASE_URL}/transactions/sum-all`, {
            method: "POST",
            headers: { "Content-Type": "application/json" }
        });
        return res.json();
    },

    // Soma por tipo
    sumByType: async (type) => {
        const res = await fetch(`${API_BASE_URL}/transactions/sum-by-type`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(type)
        });
        return res.json();
    },

    // Últimas transações
    getLatest: async (limit) => {
        const res = await fetch(`${API_BASE_URL}/transactions/latest`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(limit)
        });
        return res.json();
    }
};

// src/lib/api/transactions.ts
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

// Tipos
export type Transaction = {
    id?: number; // opcional ao criar
    type: string;
    value: number;
    description: string;
    [key: string]: any; // campos extras
};

export type TransactionUpdateValue = {
    id: number;
    newValue: number;
    newDescription?: string;
};

export type TransactionUpdateType = {
    id: number;
    newType: string;
};

export type TransactionUpdateDescription = {
    id: number;
    newDescription: string;
};

export const TransactionsAPI = {
    // Criar transação
    create: async (transaction: Transaction) => {
        const res = await fetch(`${API_BASE_URL}/transactions/create`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(transaction),
        });
        return res.json();
    },

    // Buscar por ID
    getById: async (id: number) => {
        const res = await fetch(`${API_BASE_URL}/transactions/by-id`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(id),
        });
        return res.json();
    },

    // Buscar por tipo
    getByType: async (type: string) => {
        const res = await fetch(`${API_BASE_URL}/transactions/by-type`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(type),
        });
        return res.json();
    },

    // Buscar por valor mínimo
    getByValueGreaterThan: async (minValue: number) => {
        const res = await fetch(`${API_BASE_URL}/transactions/value-greater-than`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(minValue),
        });
        return res.json();
    },

    // Buscar por valor máximo
    getByValueLessThan: async (maxValue: number) => {
        const res = await fetch(`${API_BASE_URL}/transactions/value-less-than`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(maxValue),
        });
        return res.json();
    },

    // Buscar por descrição
    getByDescription: async (text: string) => {
        const res = await fetch(`${API_BASE_URL}/transactions/by-description`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(text),
        });
        return res.json();
    },

    // Atualizações
    updateValue: async ({ id, newValue, newDescription }: TransactionUpdateValue) => {
        const res = await fetch(`${API_BASE_URL}/transactions/update-value`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, newValue, newDescription }),
        });
        return res.json();
    },

    updateType: async ({ id, newType }: TransactionUpdateType) => {
        const res = await fetch(`${API_BASE_URL}/transactions/update-type`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, newType }),
        });
        return res.json();
    },

    updateDescription: async ({ id, newDescription }: TransactionUpdateDescription) => {
        const res = await fetch(`${API_BASE_URL}/transactions/update-description`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, newDescription }),
        });
        return res.json();
    },

    // Deletar por ID
    deleteById: async (id: number) => {
        await fetch(`${API_BASE_URL}/transactions/delete-by-id`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(id),
        });
    },

    // Soma total
    sumAll: async (): Promise<number> => {
        const res = await fetch(`${API_BASE_URL}/transactions/sum-all`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
        });
        return res.json();
    },

    // Soma por tipo
    sumByType: async (type: string): Promise<number> => {
        const res = await fetch(`${API_BASE_URL}/transactions/sum-by-type`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(type),
        });
        return res.json();
    },

    // Últimas transações
    getLatest: async (limit: number) => {
        const res = await fetch(`${API_BASE_URL}/transactions/latest`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(limit),
        });
        return res.json();
    },
};

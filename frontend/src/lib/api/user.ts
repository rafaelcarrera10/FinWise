// src/lib/api/user.ts
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/users";

// Tipos de usuário
export type UserLogin = {
    email: string;
    password: string;
};

export type UserCreate = {
    name: string;
    email: string;
    password: string;
    [key: string]: any; // para outros campos opcionais
};

export type UserUpdateName = { id: number; newName: string };
export type UserUpdateEmail = { id: number; newEmail: string };
export type UserUpdatePassword = { id: number; newPassword: string };
export type UserUpdatePhoto = { id: number; photo: string };
export type UserUpdateDescription = { id: number; description: string };

export const UserAPI = {
    // Criar usuário
    create: async (user: UserCreate) => {
        const res = await fetch(`${API_BASE_URL}/create`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user),
        });
        return res.json();
    },

    // Login
    login: async (email: string, password: string) => {
        const res = await fetch(`${API_BASE_URL}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password }),
        });
        return res.ok ? res.json() : null;
    },

    // Buscar usuário por email
    getByEmail: async (email: string) => {
        const res = await fetch(`${API_BASE_URL}/by-email`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(email),
        });
        return res.ok ? res.json() : null;
    },

    // Buscar por nome (parcial)
    getByNameContaining: async (name: string) => {
        const res = await fetch(`${API_BASE_URL}/name-containing`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(name),
        });
        return res.json();
    },

    // Verificar existência por email
    existsByEmail: async (email: string) => {
        const res = await fetch(`${API_BASE_URL}/exists-by-email`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(email),
        });
        return res.json();
    },

    // Contar usuários
    count: async (): Promise<number> => {
        const res = await fetch(`${API_BASE_URL}/count`);
        return res.json();
    },

    // Atualizações
    updateName: async ({ id, newName }: UserUpdateName) => {
        const res = await fetch(`${API_BASE_URL}/update-name`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, newName }),
        });
        return res.ok;
    },

    updateEmail: async ({ id, newEmail }: UserUpdateEmail) => {
        const res = await fetch(`${API_BASE_URL}/update-email`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, newEmail }),
        });
        return res.ok;
    },

    updatePassword: async ({ id, newPassword }: UserUpdatePassword) => {
        const res = await fetch(`${API_BASE_URL}/update-password`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, newPassword }),
        });
        return res.ok;
    },

    updatePhoto: async ({ id, photo }: UserUpdatePhoto) => {
        const res = await fetch(`${API_BASE_URL}/update-photo`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, photo }),
        });
        return res.ok;
    },

    updateDescription: async ({ id, description }: UserUpdateDescription) => {
        const res = await fetch(`${API_BASE_URL}/update-description`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, description }),
        });
        return res.ok;
    },

    // Deletar
    deleteByEmail: async (email: string) => {
        const res = await fetch(`${API_BASE_URL}/delete-by-email`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(email),
        });
        return res.ok;
    },

    deleteByName: async (name: string) => {
        const res = await fetch(`${API_BASE_URL}/delete-by-name`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(name),
        });
        return res.ok;
    },

    // Remover foto e descrição
    removePhoto: async (id: number) => {
        const res = await fetch(`${API_BASE_URL}/remove-photo`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(id),
        });
        return res.ok;
    },

    removeDescription: async (id: number) => {
        const res = await fetch(`${API_BASE_URL}/remove-description`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(id),
        });
        return res.ok;
    },

    // Outros métodos podem receber tipagem semelhante
};

// src/lib/api/user.js
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/users";

export const UserAPI = {
    // Salvar usuário
    create: async (user) => {
        const res = await fetch(`${API_BASE_URL}/create`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user)
        });
        return res.json();
    },

    // Buscar usuário por email
    getByEmail: async (email) => {
        const res = await fetch(`${API_BASE_URL}/by-email`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(email)
        });
        return res.ok ? res.json() : null;
    },

    // Buscar por nome (parcial)
    getByNameContaining: async (name) => {
        const res = await fetch(`${API_BASE_URL}/name-containing`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(name)
        });
        return res.json();
    },

    // Verificar existência por e-mail
    existsByEmail: async (email) => {
        const res = await fetch(`${API_BASE_URL}/exists-by-email`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(email)
        });
        return res.json();
    },

    // Contar usuários
    count: async () => {
        const res = await fetch(`${API_BASE_URL}/count`);
        return res.json();
    },

    // Buscar por nome exato
    getByName: async (name) => {
        const res = await fetch(`${API_BASE_URL}/by-name`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(name)
        });
        return res.json();
    },

    // Buscar nomes que começam com
    getByNameStartingWith: async (prefix) => {
        const res = await fetch(`${API_BASE_URL}/name-starting-with`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(prefix)
        });
        return res.json();
    },

    // Buscar todos ordenados por nome
    getAllOrderByNameAsc: async () => {
        const res = await fetch(`${API_BASE_URL}/all-order-by-name-asc`);
        return res.json();
    },

    // Buscar todos ordenados por ID desc
    getAllOrderByIdDesc: async () => {
        const res = await fetch(`${API_BASE_URL}/all-order-by-id-desc`);
        return res.json();
    },

    // Paginação
    getUsersWithPagination: async (offset, limit) => {
        const res = await fetch(`${API_BASE_URL}/with-pagination`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ offset, limit })
        });
        return res.json();
    },

    // Buscar por múltiplos IDs
    getByIds: async (ids) => {
        const res = await fetch(`${API_BASE_URL}/by-ids`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(ids)
        });
        return res.json();
    },

    // Buscar por parte do email
    getByEmailContaining: async (emailPart) => {
        const res = await fetch(`${API_BASE_URL}/email-containing`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(emailPart)
        });
        return res.json();
    },

    // Buscar por nome ou email
    getByNameOrEmailContaining: async (searchText) => {
        const res = await fetch(`${API_BASE_URL}/name-or-email-containing`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(searchText)
        });
        return res.json();
    },

    // Buscar foto de perfil
    getPhotoByUserId: async (id) => {
        const res = await fetch(`${API_BASE_URL}/photo-by-user-id`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(id)
        });
        return res.ok ? await res.arrayBuffer() : null;
    },

    // Buscar descrição
    getDescriptionByUserId: async (id) => {
        const res = await fetch(`${API_BASE_URL}/description-by-user-id`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(id)
        });
        return res.ok ? res.text() : null;
    },

    // Atualizar nome
    updateName: async (id, newName) => {
        const res = await fetch(`${API_BASE_URL}/update-name`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, newName })
        });
        return res.ok;
    },

    // Atualizar email
    updateEmail: async (id, newEmail) => {
        const res = await fetch(`${API_BASE_URL}/update-email`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, newEmail })
        });
        return res.ok;
    },

    // Atualizar senha
    updatePassword: async (id, newPassword) => {
        const res = await fetch(`${API_BASE_URL}/update-password`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, newPassword })
        });
        return res.ok;
    },

    // Atualizar foto
    updatePhoto: async (id, photo) => {
        const res = await fetch(`${API_BASE_URL}/update-photo`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, photo })
        });
        return res.ok;
    },

    // Atualizar descrição
    updateDescription: async (id, description) => {
        const res = await fetch(`${API_BASE_URL}/update-description`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ id, description })
        });
        return res.ok;
    },

    // Deletar por email
    deleteByEmail: async (email) => {
        const res = await fetch(`${API_BASE_URL}/delete-by-email`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(email)
        });
        return res.ok;
    },

    // Deletar por nome
    deleteByName: async (name) => {
        const res = await fetch(`${API_BASE_URL}/delete-by-name`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(name)
        });
        return res.ok;
    },

    // Remover foto
    removePhoto: async (id) => {
        const res = await fetch(`${API_BASE_URL}/remove-photo`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(id)
        });
        return res.ok;
    },

    // Remover descrição
    removeDescription: async (id) => {
        const res = await fetch(`${API_BASE_URL}/remove-description`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(id)
        });
        return res.ok;
    }
};

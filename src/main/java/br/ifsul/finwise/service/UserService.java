package br.ifsul.finwise.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import br.ifsul.finwise.model.UserModel;
import br.ifsul.finwise.repository.UserRepository;

public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    // CRUD - Buscar

    // Busca usuário por email
    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Busca usuário por nome (busca parcial, case insensitive)
    public List<UserModel> findByNameContainingIgnoreCase(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }

    // Verifica se existe um usuário com o email especificado
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // Conta quantos usuários existem no sistema
    public long count() {
        return userRepository.count();
    }

    // Busca usuários por nome exato (case insensitive)
    public List<UserModel> findByNameIgnoreCase(String name) {
        return userRepository.findByNameIgnoreCase(name);
    }

    // Busca usuários cujo nome começa com o texto especificado
    public List<UserModel> findByNameStartingWithIgnoreCase(String prefix) {
        return userRepository.findByNameStartingWithIgnoreCase(prefix);
    }

    // Busca usuários ordenados por nome
    public List<UserModel> findAllOrderByNameAsc() {
        return userRepository.findAllOrderByNameAsc();
    }

    // Busca usuários ordenados por data de criação (ID)
    public List<UserModel> findAllOrderByIdDesc() {
        return userRepository.findAllOrderByIdDesc();
    }

    // Busca usuários com paginação
    public List<UserModel> findUsersWithPagination(int offset, int limit) {
        return userRepository.findUsersWithPagination(offset, limit);
    }

    // Busca usuários por múltiplos IDs
    public List<UserModel> findByIds(List<Long> ids) {
        return userRepository.findByIds(ids);
    }

    // Busca usuários cujo email contém o texto especificado
    public List<UserModel> findByEmailContaining(String emailPart) {
        return userRepository.findByEmailContaining(emailPart);
    }

    // Busca usuários com nome ou email que contenham o texto especificado
    public List<UserModel> findByNameOrEmailContaining(String searchText) {
        return userRepository.findByNameOrEmailContaining(searchText);
    }

    // Buscar foto de perfil por ID do usuário (professor)
    public byte[] findPhotoByUserId(Long id) {
        return userRepository.findPhotoByUserId(id);
    }

    // Buscar descrição por ID do usuário (professor)
    public String findDescriptionByUserId(Long id) {
        return userRepository.findDescriptionByUserId(id);
    }

    // CRUD - Atualizar

    // Atualiza o nome de um usuário por ID
    public int updateUserNameById(Long id, String newName) {
        return userRepository.updateUserNameById(id, newName);
    }

    // Atualiza o email de um usuário por ID
    public int updateUserEmailById(Long id, String newEmail) {
        return userRepository.updateUserEmailById(id, newEmail);
    }

    // Atualiza a senha de um usuário por ID
    public int updateUserPasswordById(Long id, String newPassword) {
        return userRepository.updateUserPasswordById(id, newPassword);
    }

    // Atualiza a foto de perfil do usuário (professor) por ID
    public int updateUserPhotoById(Long id, byte[] photo) {
        return userRepository.updateUserPhotoById(id, photo);
    }

    // Atualiza a descrição do professor por ID
    public int updateTeacherDescriptionById(Long id, String description) {
        return userRepository.updateTeacherDescriptionById(id, description);
    }

    // CRUD - Deletar

    // Remove usuário por email
    public int deleteByEmail(String email) {
        return userRepository.deleteByEmail(email);
    }

    // Remove todos os usuários com nome específico
    public int deleteByName(String name) {
        return userRepository.deleteByName(name);
    }

    // Remove foto de perfil do usuário (professor) por ID
    public int removeUserPhotoById(Long id) {
        return userRepository.removeUserPhotoById(id);
    }

    // Remove descrição do professor por ID
    public int removeTeacherDescriptionById(Long id) {
        return userRepository.removeTeacherDescriptionById(id);
    }
}

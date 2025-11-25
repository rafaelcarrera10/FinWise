package br.ifsul.finwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifsul.finwise.model.UsuarioModelo;
import br.ifsul.finwise.repository.UserRepository;
// Importações adicionadas
import br.ifsul.finwise.service.ValidationService.ValidationResult;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UserRepository userRepository;

    // Dependência injetada para validação
    @Autowired
    private ValidationService validationService;

    /**
     * Salva um novo usuário ou atualiza um existente.
     * Valida o nome e criptografa a senha antes de salvar.
     */
    public UsuarioModelo save(UsuarioModelo usuario) {
        
        // 1. Validar o Nome
        ValidationResult nameResult = validationService.validateUserData(usuario.getName());
        if (!nameResult.isValid()) {
            throw new IllegalArgumentException(nameResult.getErrorMessage());
        }

        // 2. Lógica para Criptografar Senha (Create vs Update)
        if (usuario.getId() == null) {
            // --- CRIAR NOVO USUÁRIO ---
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Senha é obrigatória para novos usuários.");
            }
            // Criptografa a nova senha
            usuario.setPassword(EncryptionService.encrypt(usuario.getPassword()));
            
        } else {
            // --- ATUALIZAR USUÁRIO EXISTENTE ---
            // Verifica se a senha foi fornecida (ou seja, se o usuário quer alterá-la)
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                // Criptografa a nova senha
                usuario.setPassword(EncryptionService.encrypt(usuario.getPassword()));
            } else {
                // Se a senha veio nula/vazia no DTO, significa que não é para alterar.
                // Buscamos a senha antiga (já criptografada) do banco para mantê-la.
                userRepository.findById(usuario.getId())
                    .map(UsuarioModelo::getPassword) // Pega a senha antiga (criptografada)
                    .ifPresent(usuario::setPassword); // Seta no objeto para não salvar nulo
            }
        }

        // 3. Salvar no repositório
        return userRepository.save(usuario);
    }

    // Retorna todos os usuários (com senhas descriptografadas)
    public List<UsuarioModelo> findAll() {
        List<UsuarioModelo> usuarios = userRepository.findAll();
        usuarios.forEach(this::decryptPassword); // Descriptografa a senha de cada usuário
        return usuarios;
    }

    // Busca um usuário pelo ID (com senha descriptografada)
    public Optional<UsuarioModelo> findById(Long id) {
        Optional<UsuarioModelo> usuarioOpt = userRepository.findById(id);
        usuarioOpt.ifPresent(this::decryptPassword); // Descriptografa a senha se o usuário for encontrado
        return usuarioOpt;
    }

    // Exclui um usuário pelo ID
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    // Busca usuários (com senhas descriptografadas)
    public List<UsuarioModelo> findByNameContaining(String name) {
        List<UsuarioModelo> usuarios = userRepository.findByNameContainingIgnoreCase(name);
        usuarios.forEach(this::decryptPassword);
        return usuarios;
    }

    // Busca usuários (com senhas descriptografadas)
    public List<UsuarioModelo> findByNameExact(String name) {
        List<UsuarioModelo> usuarios = userRepository.findByNameIgnoreCase(name);
        usuarios.forEach(this::decryptPassword);
        return usuarios;
    }

    // Busca usuários (com senhas descriptografadas)
    public List<UsuarioModelo> findByNamePrefix(String prefix) {
        List<UsuarioModelo> usuarios = userRepository.findByNameStartingWithIgnoreCase(prefix);
        usuarios.forEach(this::decryptPassword);
        return usuarios;
    }

    // Lista todos os usuários (com senhas descriptografadas)
    public List<UsuarioModelo> findAllOrderByName() {
        List<UsuarioModelo> usuarios = userRepository.findAllOrderByNameAsc();
        usuarios.forEach(this::decryptPassword);
        return usuarios;
    }

    // Lista todos os usuários (com senhas descriptografadas)
    public List<UsuarioModelo> findAllOrderByIdDesc() {
        List<UsuarioModelo> usuarios = userRepository.findAllOrderByIdDesc();
        usuarios.forEach(this::decryptPassword);
        return usuarios;
    }

    // Busca múltiplos usuários (com senhas descriptografadas)
    public List<UsuarioModelo> findByIds(List<Long> ids) {
        List<UsuarioModelo> usuarios = userRepository.findByIds(ids);
        usuarios.forEach(this::decryptPassword);
        return usuarios;
    }

    /**
     * Atualiza o nome de um usuário, validando-o primeiro.
     */
    @Transactional
    public int updateName(Long id, String newName) {
        // Valida o novo nome
        ValidationResult nameResult = validationService.validateUserData(newName);
        if (!nameResult.isValid()) {
            throw new IllegalArgumentException(nameResult.getErrorMessage());
        }
        return userRepository.updateUserNameById(id, newName);
    }

    /**
     * Atualiza a senha de um usuário, criptografando-a primeiro.
     */
    @Transactional
    public int updatePassword(Long id, String newPassword) {
        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("Nova senha não pode ser nula ou vazia.");
        }
        // Criptografa a nova senha antes de atualizar
        String encryptedPassword = EncryptionService.encrypt(newPassword);
        return userRepository.updateUserPasswordById(id, encryptedPassword);
    }

    // --- Métodos restantes permanecem os mesmos, pois lidam com biografia/foto ---

    @Transactional
    public int updateFotoPerfil(Long id, byte[] fotoPerfil) {
        return userRepository.updateUserfotoPerfilById(id, fotoPerfil);
    }

    @Transactional
    public int updateBiografia(Long id, String biografia) {
        return userRepository.updateUserbiografiaById(id, biografia);
    }

    @Transactional
    public int removeFotoPerfil(Long id) {
        return userRepository.removeUserfotoPerfilById(id);
    }

    @Transactional
    public int removeBiografia(Long id) {
        return userRepository.removeUserbiografiaById(id);
    }

    @Transactional
    public int deleteByName(String name) {
        return userRepository.deleteByName(name);
    }

    public byte[] getFotoPerfil(Long id) {
        return userRepository.findfotoPerfilByUserId(id);
    }

    public String getBiografia(Long id) {
        return userRepository.findbiografiaByUserId(id);
    }

    // --- MÉTODO AUXILIAR ---

    /**
     * Método auxiliar para descriptografar a senha de um usuário.
     * Lida com senhas nulas ou falhas na descriptografia.
     */
    private void decryptPassword(UsuarioModelo usuario) {
        if (usuario == null || usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            return;
        }
        
        try {
            String decryptedPassword = EncryptionService.decrypt(usuario.getPassword());
            usuario.setPassword(decryptedPassword);
        } catch (Exception e) {
            // Log de erro (idealmente)
            System.err.println("Falha ao descriptografar senha do usuário " + usuario.getId() + ": " + e.getMessage());
            // Define a senha como nula para não expor a string criptografada
            usuario.setPassword(null); 
        }
    }
}
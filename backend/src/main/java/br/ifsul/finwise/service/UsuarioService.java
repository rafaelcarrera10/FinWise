package br.ifsul.finwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ifsul.finwise.model.UsuarioModelo;
import br.ifsul.finwise.repository.UsuarioRepositorio;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EncryptionService Encrypt;


    /**
     * Cadastro de usuário com criptografia SHA-256
     */
    public UsuarioModelo criarUsuario(UsuarioModelo usuario) {

        // Criptografa a senha
        String senhaCriptografada = Encrypt.encrypt(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        return usuarioRepositorio.save(usuario);
    }

    /**
     * Login usando a classe Encrypt
     */
    public UsuarioModelo login(String name, String senhaDigitada) {

        // Busca usuário pelo nome
        Optional<UsuarioModelo> usuarioOpt = usuarioRepositorio.findByName(name);

        if (usuarioOpt.isEmpty()) return null;

        UsuarioModelo usuario = usuarioOpt.get();

        // Criptografa a senha digitada para comparar
        String senhaCriptografada = Encrypt.encrypt(senhaDigitada);

        if (usuario.getSenha().equals(senhaCriptografada)) {
            return usuario; // Login OK
        }

        return null; // Senha incorreta
    }

    /**
     * Editar usuário (com atualização de senha opcional)
     */
    public UsuarioModelo atualizarUsuario(Integer id, UsuarioModelo novosDados) {

        UsuarioModelo existente = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        existente.setName(novosDados.getName());

        // Atualiza senha somente se nova senha vier preenchida
        if (novosDados.getSenha() != null && !novosDados.getSenha().isBlank()) {
            existente.setSenha(Encrypt.encrypt(novosDados.getSenha()));
        }

        return usuarioRepositorio.save(existente);
    }

    // Deletar usuário
    public void deletarUsuario(Integer id) {
        usuarioRepositorio.deleteById(id);
    }

    // Buscar usuário por ID
    public Optional<UsuarioModelo> buscarUsuarioPorId(Integer id) {
        return usuarioRepositorio.findById(id);
    }

    // Buscar usuário por nome
    public Optional<UsuarioModelo> buscarUsuarioPorNome(String name) {
        return usuarioRepositorio.findByName(name);
    }

    // Buscar usuário por nome ignorando case
    public Optional<UsuarioModelo> buscarUsuarioPorNomeIgnorandoCase(String name) {
        return usuarioRepositorio.findByNameIgnoreCase(name);
    }

    // Editar senha
    public void AtualizaSenha(Integer userId, String oldPassword, String newPassword) {

        UsuarioModelo usuario = usuarioRepositorio.findById(userId.intValue())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    
        // Descriptografa a senha atual do banco
        String senhaAtualDescriptografada = Encrypt.decrypt(usuario.getSenha());
    
        // Verifica se a senha antiga informada está correta
        if (!senhaAtualDescriptografada.equals(oldPassword)) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }
    
        // Criptografa a nova senha
        String novaSenhaCriptografada = Encrypt.encrypt(newPassword);
    
        // Atualiza e salva
        usuario.setSenha(novaSenhaCriptografada);
        usuarioRepositorio.save(usuario);
    }

    // Atualizar Nome
    public void AtualizarNome(Integer id, String newName) {

        UsuarioModelo usuario = usuarioRepositorio.findById(id.intValue())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
    
        usuario.setName(newName.trim());
        usuarioRepositorio.save(usuario);
    }

    

}

package br.ifsul.finwise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifsul.finwise.model.UsuarioModelo;
import br.ifsul.finwise.repository.UsuarioRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EncryptionService encryptService;

    // Criação de usuário com criptografia
    public UsuarioModelo criarUsuario(UsuarioModelo usuario) {
        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }

        String senhaCriptografada = encryptService.encrypt(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        return usuarioRepositorio.save(usuario);
    }

    // Login
    public Optional<UsuarioModelo> login(String emailOuNome, String senhaDigitada) {
        Optional<UsuarioModelo> usuarioOpt = usuarioRepositorio.findByName(emailOuNome);

        if (usuarioOpt.isEmpty()) return Optional.empty();

        UsuarioModelo usuario = usuarioOpt.get();
        String senhaCriptografada = encryptService.encrypt(senhaDigitada);

        if (usuario.getSenha().equals(senhaCriptografada)) {
            return Optional.of(usuario);
        }
        return Optional.empty();
    }

    // Atualizar usuário
    @Transactional
    public UsuarioModelo atualizarUsuario(Integer id, UsuarioModelo novosDados) {
        UsuarioModelo existente = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        if (novosDados.getName() != null && !novosDados.getName().isBlank()) {
            existente.setName(novosDados.getName().trim());
        }

        if (novosDados.getSenha() != null && !novosDados.getSenha().isBlank()) {
            existente.setSenha(encryptService.encrypt(novosDados.getSenha()));
        }

        return usuarioRepositorio.save(existente);
    }

    // Atualizar senha
    @Transactional
    public void atualizarSenha(Integer id, String oldPassword, String newPassword) {
        UsuarioModelo usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        String senhaAtualDescriptografada = encryptService.decrypt(usuario.getSenha());
        if (!senhaAtualDescriptografada.equals(oldPassword)) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        usuario.setSenha(encryptService.encrypt(newPassword));
        usuarioRepositorio.save(usuario);
    }

    // Atualizar nome
    @Transactional
    public void atualizarNome(Integer id, String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        UsuarioModelo usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.setName(newName.trim());
        usuarioRepositorio.save(usuario);
    }

    // Deletar usuário
    public void deletarUsuario(Integer id) {
        usuarioRepositorio.deleteById(id);
    }

    // Buscas
    public Optional<UsuarioModelo> buscarUsuarioPorId(Integer id) {
        return usuarioRepositorio.findById(id);
    }

    public Optional<UsuarioModelo> buscarUsuarioPorNome(String name) {
        return usuarioRepositorio.findByName(name);
    }

    public Optional<UsuarioModelo> buscarUsuarioPorNomeIgnorandoCase(String name) {
        return usuarioRepositorio.findByNameIgnoreCase(name);
    }

    public List<UsuarioModelo> buscarPorFragmentoNome(String fragment) {
        return usuarioRepositorio.findByNameContainingIgnoreCase(fragment);
    }
}

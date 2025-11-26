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

    // Criação de usuário com criptografia AES
    public UsuarioModelo criarUsuario(UsuarioModelo usuario) {
        if (usuario.getpassword() == null || usuario.getpassword().isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }

        String passwordCriptografada = encryptService.encrypt(usuario.getpassword());
        usuario.setpassword(passwordCriptografada);

        return usuarioRepositorio.save(usuario);
    }

    // Login (descriptografa a senha armazenada)
    public Optional<UsuarioModelo> login(String name, String passwordDigitada) {
        Optional<UsuarioModelo> usuarioOpt = usuarioRepositorio.findByName(name);

        if (usuarioOpt.isEmpty()) return Optional.empty();

        UsuarioModelo usuario = usuarioOpt.get();
        String senhaDoBanco = encryptService.decrypt(usuario.getpassword());

        if (senhaDoBanco.equals(passwordDigitada)) {
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

        if (novosDados.getpassword() != null && !novosDados.getpassword().isBlank()) {
            existente.setpassword(encryptService.encrypt(novosDados.getpassword()));
        }

        return usuarioRepositorio.save(existente);
    }

    // Atualizar password
    @Transactional
    public void atualizarpassword(Integer id, String oldpassword, String newpassword) {
        UsuarioModelo usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        String passwordAtualDescriptografada = encryptService.decrypt(usuario.getpassword());
        if (!passwordAtualDescriptografada.equals(oldpassword)) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        usuario.setpassword(encryptService.encrypt(newpassword));
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

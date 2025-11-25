package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.UsuarioModelo;
import br.ifsul.finwise.service.UsuarioService;
import br.ifsul.finwise.service.EncryptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EncryptionService encryptService;

    // Criptografia / Descriptografia
    @PostMapping("/encrypt")
    public ResponseEntity<String> encrypt(@RequestBody String data) {
        return ResponseEntity.ok(encryptService.encrypt(data));
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decrypt(@RequestBody String data) {
        try {
            return ResponseEntity.ok(encryptService.decrypt(data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Falha ao descriptografar");
        }
    }

    // Criar usuário
    @PostMapping("/create")
    public ResponseEntity<UsuarioModelo> create(@RequestBody UsuarioModelo user) {
        try {
            UsuarioModelo created = usuarioService.criarUsuario(user);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<UsuarioModelo> login(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String password = (String) body.get("password");

        Optional<UsuarioModelo> usuario = usuarioService.login(name, password);

        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(401).build());
    }

    // Buscar usuário por ID
    @GetMapping("/by-id")
    public ResponseEntity<UsuarioModelo> getById(@RequestParam Integer id) {
        Optional<UsuarioModelo> usuario = usuarioService.buscarUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar usuário por nome
    @GetMapping("/by-name")
    public ResponseEntity<UsuarioModelo> getByName(@RequestParam String name) {
        Optional<UsuarioModelo> usuario = usuarioService.buscarUsuarioPorNome(name);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar por fragmento de nome
    @GetMapping("/search")
    public ResponseEntity<List<UsuarioModelo>> searchByFragment(@RequestParam String q) {
        List<UsuarioModelo> usuarios = usuarioService.buscarPorFragmentoNome(q);
        return ResponseEntity.ok(usuarios);
    }

    // Atualizar nome
    @PostMapping("/update-name")
    public ResponseEntity<Void> updateName(@RequestBody Map<String, Object> body) {
        try {
            Integer id = ((Number) body.get("id")).intValue();
            String newName = (String) body.get("newName");
            usuarioService.atualizarNome(id, newName);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Atualizar senha
    @PostMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody Map<String, Object> body) {
        try {
            Integer id = ((Number) body.get("id")).intValue();
            String oldPassword = (String) body.get("oldPassword");
            String newPassword = (String) body.get("newPassword");
            usuarioService.atualizarSenha(id, oldPassword, newPassword);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).build();
        }
    }

    // Deletar usuário
    @PostMapping("/delete-by-id")
    public ResponseEntity<Void> deleteById(@RequestParam Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok().build();
    }
}

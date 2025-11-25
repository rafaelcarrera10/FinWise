package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.UsuarioModelo;
import br.ifsul.finwise.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifsul.finwise.service.EncryptionService;

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
    private EncryptionService Encrypt;

    // ----------- CRIPTOGRAFIA / DESCRIPTOGRAFIA -----------

    @PostMapping("/encrypt")
    public ResponseEntity<String> encrypt(@RequestBody String data) {
        return ResponseEntity.ok(Encrypt.encrypt(data));
    }

    @PostMapping("/decrypt")
    public ResponseEntity<String> decrypt(@RequestBody String data) {
        try {
            return ResponseEntity.ok(Encrypt.decrypt(data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Falha ao descriptografar");
        }
    }

    // ----------- CRIAÇÃO DE USUÁRIO -----------

    @PostMapping("/create")
    public ResponseEntity<UsuarioModelo> create(@RequestBody UsuarioModelo user) {
        try {
            return ResponseEntity.ok(usuarioService.criarUsuario(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // ----------- LOGIN -----------

    @PostMapping("/login")
        public ResponseEntity<UsuarioModelo> login(@RequestBody Map<String, Object> body) {
        String email = (String) body.get("email");
        String password = (String) body.get("password");

        UsuarioModelo usuario = usuarioService.login(email, password);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    // BUSCAS 


    @GetMapping("/by-name")
    public ResponseEntity<Optional<UsuarioModelo>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorNome(name));
    }

    // TUALIZAÇÕES

    @PostMapping("/update-name")
    public ResponseEntity<Void> updateName(@RequestBody Map<String, Object> body) {
        Integer id = ((Number) body.get("id")).intValue();
        String newName = (String) body.get("newName");

        usuarioService.AtualizarNome(id, newName);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody Map<String, Object> body) {
        Integer userId = ((Number) body.get("userId")).intValue();
        String oldPassword = (String) body.get("oldPassword");
        String newPassword = (String) body.get("newPassword");

        try {
            usuarioService.AtualizaSenha(userId, oldPassword, newPassword);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).build();
        }
    }

    // ----------- REMOÇÕES -----------

    @PostMapping("/delete-by-id")
    public ResponseEntity<Void> deleteByName(@RequestParam Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok().build();
    }


}

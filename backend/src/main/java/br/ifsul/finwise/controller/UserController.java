package br.ifsul.finwise.controller;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifsul.finwise.model.UserModel;
import br.ifsul.finwise.service.UserService;
import br.ifsul.finwise.service.EncryptionService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Criptografar dados
    @PostMapping("/encrypt")
    public String encryptUserData(@RequestBody String data) {
        return EncryptionService.encrypt(data);
    }

    // Descriptografar dados
    @PostMapping("/decrypt")
    public String decryptUserData(@RequestBody String data) {
        return EncryptionService.decrypt(data);
    }

    // Salvar usuário
    @PostMapping("/create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        UserModel savedUser = userService.save(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    // Login de usuário
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> data) {
        try {
            String email = data.get("email");
            String password = data.get("password");

            if (email == null || password == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "email e password obrigatórios"));
            }

            return userService.login(email, password)
                    .<ResponseEntity<?>>map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(401).body(Map.of("error", "Credenciais inválidas")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Erro interno"));
        }
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar usuário por email
    @GetMapping("/by-email")
    public ResponseEntity<UserModel> getByEmail(@RequestParam String email) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por nome (parcial)
    @GetMapping("/name-containing")
    public ResponseEntity<List<UserModel>> getByNameContaining(@RequestParam String name) {
        return ResponseEntity.ok(userService.findByNameContainingIgnoreCase(name));
    }

    // Verificar se existe usuário por email
    @GetMapping("/exists-by-email")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }

    // Contar usuários
    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        return ResponseEntity.ok(userService.count());
    }

    // Atualizar foto do usuário usando Base64
    @PostMapping("/update-photo")
    public ResponseEntity<Void> updateUserPhoto(@RequestParam Long id, @RequestBody String photoBase64) {
        byte[] photo = Base64.getDecoder().decode(photoBase64);
        return userService.updateUserPhotoById(id, photo) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Buscar foto por id de usuário (retorna Base64)
    @GetMapping("/photo-by-user-id")
    public ResponseEntity<String> getPhotoByUserId(@RequestParam Long id) {
        byte[] photo = userService.findPhotoByUserId(id);
        return photo != null ? ResponseEntity.ok(Base64.getEncoder().encodeToString(photo)) : ResponseEntity.notFound().build();
    }
}

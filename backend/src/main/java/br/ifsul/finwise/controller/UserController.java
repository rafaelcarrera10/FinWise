package br.ifsul.finwise.controller;

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

    public UserController(UserService userService, EncryptionService encryptionService) {
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

    // Buscar por nome (exato)
    @GetMapping("/by-name")
    public ResponseEntity<List<UserModel>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.findByNameIgnoreCase(name));
    }

    // Buscar por nome (prefixo)
    @GetMapping("/name-starting-with")
    public ResponseEntity<List<UserModel>> getByNameStartingWith(@RequestParam String prefix) {
        return ResponseEntity.ok(userService.findByNameStartingWithIgnoreCase(prefix));
    }

    // Listar usuários por nome em ordem crescente
    @GetMapping("/all-order-by-name-asc")
    public ResponseEntity<List<UserModel>> getAllOrderByNameAsc() {
        return ResponseEntity.ok(userService.findAllOrderByNameAsc());
    }

    // Listar usuários por id em ordem decrescente
    @GetMapping("/all-order-by-id-desc")
    public ResponseEntity<List<UserModel>> getAllOrderByIdDesc() {
        return ResponseEntity.ok(userService.findAllOrderByIdDesc());
    }

    // Buscar usuários com paginação
    @GetMapping("/with-pagination")
    public ResponseEntity<List<UserModel>> getUsersWithPagination(
            @RequestParam int offset,
            @RequestParam int limit) {
        return ResponseEntity.ok(userService.findUsersWithPagination(offset, limit));
    }

    // Buscar por lista de ids
    @GetMapping("/by-ids")
    public ResponseEntity<List<UserModel>> getByIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(userService.findByIds(ids));
    }

    // Buscar por parte do email
    @GetMapping("/email-containing")
    public ResponseEntity<List<UserModel>> getByEmailContaining(@RequestParam String emailPart) {
        return ResponseEntity.ok(userService.findByEmailContaining(emailPart));
    }

    // Buscar por parte do nome ou email
    @GetMapping("/name-or-email-containing")
    public ResponseEntity<List<UserModel>> getByNameOrEmailContaining(@RequestParam String searchText) {
        return ResponseEntity.ok(userService.findByNameOrEmailContaining(searchText));
    }

    // Buscar foto por id de usuário
    @GetMapping("/photo-by-user-id")
    public ResponseEntity<byte[]> getPhotoByUserId(@RequestParam Long id) {
        byte[] photo = userService.findPhotoByUserId(id);
        return photo != null ? ResponseEntity.ok(photo) : ResponseEntity.notFound().build();
    }

    // Buscar descrição por id de usuário
    @GetMapping("/description-by-user-id")
    public ResponseEntity<String> getDescriptionByUserId(@RequestParam Long id) {
        String description = userService.findDescriptionByUserId(id);
        return description != null ? ResponseEntity.ok(description) : ResponseEntity.notFound().build();
    }

    // Atualizar nome do usuário
    @PostMapping("/update-name")
    public ResponseEntity<Void> updateUserName(@RequestParam Long id, @RequestParam String newName) {
        return userService.updateUserNameById(id, newName) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Atualizar email do usuário
    @PostMapping("/update-email")
    public ResponseEntity<Void> updateUserEmail(@RequestParam Long id, @RequestParam String newEmail) {
        return userService.updateUserEmailById(id, newEmail) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Atualizar senha do usuário
    @PostMapping("/update-password")
    public ResponseEntity<Void> updateUserPassword(@RequestParam Long id, @RequestParam String newPassword) {
        return userService.updateUserPasswordById(id, newPassword) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Atualizar foto do usuário
    @PostMapping("/update-photo")
    public ResponseEntity<Void> updateUserPhoto(@RequestParam Long id, @RequestParam byte[] photo) {
        return userService.updateUserPhotoById(id, photo) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Atualizar descrição do professor
    @PostMapping("/update-description")
    public ResponseEntity<Void> updateTeacherDescription(@RequestParam Long id, @RequestParam String description) {
        return userService.updateTeacherDescriptionById(id, description) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Excluir usuário por email
    @PostMapping("/delete-by-email")
    public ResponseEntity<Void> deleteByEmail(@RequestParam String email) {
        return userService.deleteByEmail(email) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Excluir usuário por nome
    @PostMapping("/delete-by-name")
    public ResponseEntity<Void> deleteByName(@RequestParam String name) {
        return userService.deleteByName(name) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Remover foto do usuário
    @PostMapping("/remove-photo")
    public ResponseEntity<Void> removeUserPhoto(@RequestParam Long id) {
        return userService.removeUserPhotoById(id) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    // Remover descrição do professor
    @PostMapping("/remove-description")
    public ResponseEntity<Void> removeTeacherDescription(@RequestParam Long id) {
        return userService.removeTeacherDescriptionById(id) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}

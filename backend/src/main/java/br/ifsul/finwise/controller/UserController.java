package br.ifsul.finwise.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ifsul.finwise.model.UserModel;
import br.ifsul.finwise.service.UserService;
import br.ifsul.finwise.service.EncryptionService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EncryptionService encryptionService;

    @Autowired
    public UserController(UserService userService, EncryptionService encryptionService) {
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/encrypt")
    public String encryptUserData(@RequestBody String data) {
        return encryptionService.encrypt(data); // chave usada automaticamente
    }

    @PostMapping("/decrypt")
    public String decryptUserData(@RequestBody String data) {
        return encryptionService.decrypt(data); // chave usada automaticamente
    }

    // Salvar usuário
    @PostMapping("/create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        UserModel savedUser = userService.save(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserModel> loginUser(@RequestBody UserModel userRequest) {
        try {
            String encryptedPassword = encryptionService.encrypt(userRequest.getPassword());
            return userService.login(userRequest.getEmail(), encryptedPassword)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.status(401).build());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
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

    @GetMapping("/exists-by-email")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        return ResponseEntity.ok(userService.count());
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<UserModel>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.findByNameIgnoreCase(name));
    }

    @GetMapping("/name-starting-with")
    public ResponseEntity<List<UserModel>> getByNameStartingWith(@RequestParam String prefix) {
        return ResponseEntity.ok(userService.findByNameStartingWithIgnoreCase(prefix));
    }

    @GetMapping("/all-order-by-name-asc")
    public ResponseEntity<List<UserModel>> getAllOrderByNameAsc() {
        return ResponseEntity.ok(userService.findAllOrderByNameAsc());
    }

    @GetMapping("/all-order-by-id-desc")
    public ResponseEntity<List<UserModel>> getAllOrderByIdDesc() {
        return ResponseEntity.ok(userService.findAllOrderByIdDesc());
    }

    @GetMapping("/with-pagination")
    public ResponseEntity<List<UserModel>> getUsersWithPagination(
            @RequestParam int offset,
            @RequestParam int limit) {
        return ResponseEntity.ok(userService.findUsersWithPagination(offset, limit));
    }

    @GetMapping("/by-ids")
    public ResponseEntity<List<UserModel>> getByIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(userService.findByIds(ids));
    }

    @GetMapping("/email-containing")
    public ResponseEntity<List<UserModel>> getByEmailContaining(@RequestParam String emailPart) {
        return ResponseEntity.ok(userService.findByEmailContaining(emailPart));
    }

    @GetMapping("/name-or-email-containing")
    public ResponseEntity<List<UserModel>> getByNameOrEmailContaining(@RequestParam String searchText) {
        return ResponseEntity.ok(userService.findByNameOrEmailContaining(searchText));
    }

    @GetMapping("/photo-by-user-id")
    public ResponseEntity<byte[]> getPhotoByUserId(@RequestParam Long id) {
        byte[] photo = userService.findPhotoByUserId(id);
        return photo != null ? ResponseEntity.ok(photo) : ResponseEntity.notFound().build();
    }

    @GetMapping("/description-by-user-id")
    public ResponseEntity<String> getDescriptionByUserId(@RequestParam Long id) {
        String description = userService.findDescriptionByUserId(id);
        return description != null ? ResponseEntity.ok(description) : ResponseEntity.notFound().build();
    }

    @PostMapping("/update-name")
    public ResponseEntity<Void> updateUserName(@RequestParam Long id, @RequestParam String newName) {
        return userService.updateUserNameById(id, newName) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/update-email")
    public ResponseEntity<Void> updateUserEmail(@RequestParam Long id, @RequestParam String newEmail) {
        return userService.updateUserEmailById(id, newEmail) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/update-password")
    public ResponseEntity<Void> updateUserPassword(@RequestParam Long id, @RequestParam String newPassword) {
        return userService.updateUserPasswordById(id, newPassword) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/update-photo")
    public ResponseEntity<Void> updateUserPhoto(@RequestParam Long id, @RequestParam byte[] photo) {
        return userService.updateUserPhotoById(id, photo) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/update-description")
    public ResponseEntity<Void> updateTeacherDescription(@RequestParam Long id, @RequestParam String description) {
        return userService.updateTeacherDescriptionById(id, description) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/delete-by-email")
    public ResponseEntity<Void> deleteByEmail(@RequestParam String email) {
        return userService.deleteByEmail(email) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/delete-by-name")
    public ResponseEntity<Void> deleteByName(@RequestParam String name) {
        return userService.deleteByName(name) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/remove-photo")
    public ResponseEntity<Void> removeUserPhoto(@RequestParam Long id) {
        return userService.removeUserPhotoById(id) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/remove-description")
    public ResponseEntity<Void> removeTeacherDescription(@RequestParam Long id) {
        return userService.removeTeacherDescriptionById(id) > 0 ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}


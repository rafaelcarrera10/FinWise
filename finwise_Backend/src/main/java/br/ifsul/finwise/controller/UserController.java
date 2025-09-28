package br.ifsul.finwise.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifsul.finwise.model.UserModel;
import br.ifsul.finwise.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Salvar usuário
    @PostMapping("/create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        UserModel savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // Buscar usuário por email
    @PostMapping("/by-email")
    public ResponseEntity<UserModel> getByEmail(@RequestBody String email) {
        return userService.findByEmail(email)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por nome (busca parcial, case insensitive)
    @PostMapping("/name-containing")
    public ResponseEntity<List<UserModel>> getByNameContaining(@RequestBody String name) {
        List<UserModel> users = userService.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(users);
    }

    // Verifica se existe um usuário com o email especificado
    @PostMapping("/exists-by-email")
    public ResponseEntity<Boolean> existsByEmail(@RequestBody String email) {
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    // Conta quantos usuários existem no sistema
    @GetMapping("/count")
    public ResponseEntity<Long> countUsers() {
        long count = userService.count();
        return ResponseEntity.ok(count);
    }

    // Busca usuários por nome exato (case insensitive)
    @PostMapping("/by-name")
    public ResponseEntity<List<UserModel>> getByName(@RequestBody String name) {
        List<UserModel> users = userService.findByNameIgnoreCase(name);
        return ResponseEntity.ok(users);
    }

    // Busca usuários cujo nome começa com o texto especificado
    @PostMapping("/name-starting-with")
    public ResponseEntity<List<UserModel>> getByNameStartingWith(@RequestBody String prefix) {
        List<UserModel> users = userService.findByNameStartingWithIgnoreCase(prefix);
        return ResponseEntity.ok(users);
    }

    // Busca usuários ordenados por nome
    @GetMapping("/all-order-by-name-asc")
    public ResponseEntity<List<UserModel>> getAllOrderByNameAsc() {
        List<UserModel> users = userService.findAllOrderByNameAsc();
        return ResponseEntity.ok(users);
    }

    // Busca usuários ordenados por data de criação (ID)
    @GetMapping("/all-order-by-id-desc")
    public ResponseEntity<List<UserModel>> getAllOrderByIdDesc() {
        List<UserModel> users = userService.findAllOrderByIdDesc();
        return ResponseEntity.ok(users);
    }

    // Busca usuários com paginação
    @PostMapping("/with-pagination")
    public ResponseEntity<List<UserModel>> getUsersWithPagination(@RequestBody int offset, @RequestBody int limit) {
        List<UserModel> users = userService.findUsersWithPagination(offset, limit);
        return ResponseEntity.ok(users);
    }

    // Busca usuários por múltiplos IDs
    @PostMapping("/by-ids")
    public ResponseEntity<List<UserModel>> getByIds(@RequestBody List<Long> ids) {
        List<UserModel> users = userService.findByIds(ids);
        return ResponseEntity.ok(users);
    }

    // Busca usuários cujo email contém o texto especificado
    @PostMapping("/email-containing")
    public ResponseEntity<List<UserModel>> getByEmailContaining(@RequestBody String emailPart) {
        List<UserModel> users = userService.findByEmailContaining(emailPart);
        return ResponseEntity.ok(users);
    }

    // Busca usuário com nome e email que contenham os textos especificados
    @PostMapping("/name-or-email-containing")
    public ResponseEntity<List<UserModel>> getByNameOrEmailContaining(@RequestBody String searchText) {
        List<UserModel> users = userService.findByNameOrEmailContaining(searchText);
        return ResponseEntity.ok(users);
    }

    // Buscar foto de perfil por ID do usuário (professor)
    @PostMapping("/photo-by-user-id")
    public ResponseEntity<byte[]> getPhotoByUserId(@RequestBody Long id) {
        byte[] photo = userService.findPhotoByUserId(id);
        if (photo != null) {
            return ResponseEntity.ok(photo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar descrição por ID do usuário (professor)
    @PostMapping("/description-by-user-id")
    public ResponseEntity<String> getDescriptionByUserId(@RequestBody Long id) {
        String description = userService.findDescriptionByUserId(id);
        if (description != null) {
            return ResponseEntity.ok(description);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar o nome de um usuário por ID
    @PostMapping("/update-name")
    public ResponseEntity<Void> updateUserName(@RequestBody Long id, @RequestBody String newName) {
        int updatedRows = userService.updateUserNameById(id, newName);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualiza o email de um usuário por ID
    @PostMapping("/update-email")
    public ResponseEntity<Void> updateUserEmail(@RequestBody Long id, @RequestBody String newEmail) {
        int updatedRows = userService.updateUserEmailById(id, newEmail);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualiza a senha de um usuário por ID
    @PostMapping("/update-password")
    public ResponseEntity<Void> updateUserPassword(@RequestBody Long id, @RequestBody String newPassword) {
        int updatedRows = userService.updateUserPasswordById(id, newPassword);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualiza a foto de perfil do usuário (professor) por ID
    @PostMapping("/update-photo")
    public ResponseEntity<Void> updateUserPhoto(@RequestBody Long id, @RequestBody byte[] photo) {
        int updatedRows = userService.updateUserPhotoById(id, photo);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualiza a descrição do professor por ID
    @PostMapping("/update-description")
    public ResponseEntity<Void> updateTeacherDescription(@RequestBody Long id, @RequestBody String description) {
        int updatedRows = userService.updateTeacherDescriptionById(id, description);
        if (updatedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remove usuário por email
    @PostMapping("/delete-by-email")
    public ResponseEntity<Void> deleteByEmail(@RequestBody String email) {
        int deletedRows = userService.deleteByEmail(email);
        if (deletedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remove todos os usuários com nome específico
    @PostMapping("/delete-by-name")
    public ResponseEntity<Void> deleteByName(@RequestBody String name) {
        int deletedRows = userService.deleteByName(name);
        if (deletedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remove foto de perfil do usuário (professor) por ID
    @PostMapping("/remove-photo")
    public ResponseEntity<Void> removeUserPhoto(@RequestBody Long id) {
        int deletedRows = userService.removeUserPhotoById(id);
        if (deletedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remove descrição do professor por ID
    @PostMapping("/remove-description")
    public ResponseEntity<Void> removeTeacherDescription(@RequestBody Long id) {
        int deletedRows = userService.removeTeacherDescriptionById(id);
        if (deletedRows > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
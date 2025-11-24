package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.UsuarioModelo;
import br.ifsul.finwise.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users") 
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Recebe uma String pura do body (ex: "meu-dado")
    @PostMapping("/encrypt")
    public ResponseEntity<String> encrypt(@RequestBody String data) {
        return ResponseEntity.ok(usuarioService.encrypt(data));
    }

    // Recebe uma String pura do body (ex: "dado-criptografado")
    @PostMapping("/decrypt")
    public ResponseEntity<String> decrypt(@RequestBody String data) {
        try {
            return ResponseEntity.ok(usuarioService.decrypt(data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Falha ao descriptografar");
        }
    }

    // Cria um novo usuário
    @PostMapping("/create")
    public ResponseEntity<UsuarioModelo> create(@RequestBody UsuarioModelo user) {
        try {
            UsuarioModelo novoUsuario = usuarioService.create(user);
            return ResponseEntity.ok(novoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); 
        }
    }

    // Autentica um usuário (login)
    @PostMapping("/login")
    public ResponseEntity<UsuarioModelo> login(@RequestBody Map<String, Object> body) {
        String email = (String) body.get("email");
        String password = (String) body.get("password");
        
        return usuarioService.login(email, password)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build()); // 401 Unauthorized
    }

    // Busca usuário pelo email
    @GetMapping("/by-email")
    public ResponseEntity<UsuarioModelo> getByEmail(@RequestParam String email) {
        return usuarioService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Busca usuários por nome parcial
    @GetMapping("/name-containing")
    public ResponseEntity<List<UsuarioModelo>> getByNameContaining(@RequestParam String name) {
        return ResponseEntity.ok(usuarioService.findByNameContaining(name));
    }
    
    // Verifica se um email já existe
    @GetMapping("/exists-by-email")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        return ResponseEntity.ok(usuarioService.existsByEmail(email));
    }
    
    // Retorna a contagem total de usuários
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(usuarioService.count());
    }

    // Busca por nome exato
    @GetMapping("/by-name")
    public ResponseEntity<List<UsuarioModelo>> getByName(@RequestParam String name) {
        return ResponseEntity.ok(usuarioService.findByNameExact(name));
    }

    // Busca por prefixo de nome
    @GetMapping("/name-starting-with")
    public ResponseEntity<List<UsuarioModelo>> getByNameStartingWith(@RequestParam String prefix) {
        return ResponseEntity.ok(usuarioService.findByNamePrefix(prefix));
    }

    // Lista todos ordenados por nome ASC
    @GetMapping("/all-order-by-name-asc")
    public ResponseEntity<List<UsuarioModelo>> getAllOrderByNameAsc() {
        return ResponseEntity.ok(usuarioService.findAllOrderByName());
    }

    // Lista todos ordenados por ID DESC (mais recentes)
    @GetMapping("/all-order-by-id-desc")
    public ResponseEntity<List<UsuarioModelo>> getAllOrderByIdDesc() {
        return ResponseEntity.ok(usuarioService.findAllOrderByIdDesc());
    }

    // Busca por uma lista de IDs
    @GetMapping("/by-ids")
    public ResponseEntity<List<UsuarioModelo>> getByIds(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(usuarioService.findByIds(ids));
    }

    // Busca foto do perfil (codificada em Base64)
    @GetMapping("/photo-by-user-id")
    public ResponseEntity<String> getPhotoByUserId(@RequestParam Long id) {
        String photoBase64 = usuarioService.getPhotoByUserId(id);
        return photoBase64 != null ? ResponseEntity.ok(photoBase64) : ResponseEntity.notFound().build();
    }

    // Busca a descrição (biografia) do usuário
    @GetMapping("/description-by-user-id")
    public ResponseEntity<String> getDescriptionByUserId(@RequestParam Long id) {
        String desc = usuarioService.getBiografia(id); // Reutiliza o método do service
        return desc != null ? ResponseEntity.ok(desc) : ResponseEntity.notFound().build();
    }

    // Atualiza o nome do usuário
    @PostMapping("/update-name")
    public ResponseEntity<Void> updateName(@RequestBody Map<String, Object> body) {
        try {
            Long id = ((Number) body.get("id")).longValue();
            String newName = (String) body.get("newName");
            
            usuarioService.updateName(id, newName);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Atualiza o email do usuário
    @PostMapping("/update-email")
    public ResponseEntity<Void> updateEmail(@RequestBody Map<String, Object> body) {
        try {
            Long id = ((Number) body.get("id")).longValue();
            String newEmail = (String) body.get("newEmail");

            usuarioService.updateEmail(id, newEmail);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Atualiza a senha do usuário
    @PostMapping("/update-password")
    public ResponseEntity<Void> updatePassword(@RequestBody Map<String, Object> body) {
        try {
            Long userId = ((Number) body.get("userId")).longValue();
            String oldPassword = (String) body.get("oldPassword");
            String newPassword = (String) body.get("newPassword");

            usuarioService.updatePassword(userId, oldPassword, newPassword);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).build(); // Senha antiga errada
        }
    }

    // Atualiza a foto do perfil (recebendo Base64)
    @PostMapping("/update-photo")
    public ResponseEntity<Void> updatePhoto(@RequestBody Map<String, Object> body) {
        try {
            Long id = ((Number) body.get("id")).longValue();
            String photo = (String) body.get("photo"); // Recebe a foto como uma string Base64
            
            usuarioService.updatePhoto(id, photo);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Atualiza a descrição (biografia)
    @PostMapping("/update-description")
    public ResponseEntity<Void> updateDescription(@RequestBody Map<String, Object> body) {
        Long id = ((Number) body.get("id")).longValue();
        String description = (String) body.get("description");
        
        usuarioService.updateBiografia(id, description); // Reutiliza o método do service
        return ResponseEntity.ok().build();
    }

    // Deleta um usuário pelo nome
    @PostMapping("/delete-by-name")
    public ResponseEntity<Void> deleteByName(@RequestParam String name) {
        usuarioService.deleteByName(name);
        return ResponseEntity.ok().build();
    }

    // Remove a foto do perfil
    @PostMapping("/remove-photo")
    public ResponseEntity<Void> removePhoto(@RequestParam Long id) {
        usuarioService.removeFotoPerfil(id); // Reutiliza o método do service
        return ResponseEntity.ok().build();
    }
    
    // Remove a descrição (biografia)
    @PostMapping("/remove-description")
    public ResponseEntity<Void> removeDescription(@RequestParam Long id) {
        usuarioService.removeBiografia(id); // Reutiliza o método do service
        return ResponseEntity.ok().build();
    }
}
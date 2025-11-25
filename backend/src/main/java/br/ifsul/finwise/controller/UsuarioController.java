package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.InvestmentAccountModel;
import br.ifsul.finwise.model.UsuarioModelo;
import br.ifsul.finwise.service.ContaInvestimentoService;
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
    private ContaInvestimentoService investmentService;

    @Autowired
    private EncryptionService encryptService;

    // -------------------- Criptografia / Descriptografia --------------------
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

    // -------------------- Usuário CRUD --------------------
    @PostMapping("/create")
    public ResponseEntity<UsuarioModelo> create(@RequestBody UsuarioModelo user) {
        try {
            UsuarioModelo created = usuarioService.criarUsuario(user);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioModelo> login(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        String password = (String) body.get("password");
        Optional<UsuarioModelo> usuario = usuarioService.login(name, password);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(401).build());
    }

    @GetMapping("/by-id")
    public ResponseEntity<UsuarioModelo> getById(@RequestParam Integer id) {
        Optional<UsuarioModelo> usuario = usuarioService.buscarUsuarioPorId(id);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/by-name")
    public ResponseEntity<UsuarioModelo> getByName(@RequestParam String name) {
        Optional<UsuarioModelo> usuario = usuarioService.buscarUsuarioPorNome(name);
        return usuario.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UsuarioModelo>> searchByFragment(@RequestParam String q) {
        List<UsuarioModelo> usuarios = usuarioService.buscarPorFragmentoNome(q);
        return ResponseEntity.ok(usuarios);
    }

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

    @PostMapping("/delete-by-id")
    public ResponseEntity<Void> deleteById(@RequestParam Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.ok().build();
    }

    // -------------------- Investimentos do Usuário --------------------

    // Listar todos os investimentos de um usuário
    @GetMapping("/{userId}/investments")
    public ResponseEntity<List<InvestmentAccountModel>> getInvestments(@PathVariable Integer userId) {
        Optional<UsuarioModelo> usuario = usuarioService.buscarUsuarioPorId(userId);
        return usuario.map(u -> ResponseEntity.ok(u.getListaInvestimentos()))
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Adicionar um investimento para um usuário
    @PostMapping("/{userId}/investments")
    public ResponseEntity<InvestmentAccountModel> addInvestment(
            @PathVariable Integer userId,
            @RequestBody InvestmentAccountModel investment) {

        Optional<UsuarioModelo> usuario = usuarioService.buscarUsuarioPorId(userId);
        if (usuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Vincula o investimento ao usuário
        investment.setUsuario(usuario.get());
        InvestmentAccountModel saved = investmentService.save(investment);

        // Adiciona à lista do usuário
        usuario.get().getListaInvestimentos().add(saved);
        usuarioService.criarUsuario(usuario.get()); // Atualiza o usuário com novo investimento

        return ResponseEntity.ok(saved);
    }
}

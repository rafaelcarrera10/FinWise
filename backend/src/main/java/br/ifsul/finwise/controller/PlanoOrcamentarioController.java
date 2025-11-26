package br.ifsul.finwise.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifsul.finwise.model.PlanoOrcamentarioModelo;
import br.ifsul.finwise.service.PlanoOrcamentarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/plano")
@CrossOrigin(origins = "*")
public class PlanoOrcamentarioController {

    @Autowired
    private PlanoOrcamentarioService service;

    // Criar
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody PlanoOrcamentarioModelo plano) {
        if (plano.dataInvalida()) {
            return ResponseEntity.badRequest()
                    .body("A data inicial não pode ser maior que a data final.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(plano));
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<PlanoOrcamentarioModelo>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return service.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Plano não encontrado."));
    }

    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id,
                                    @Valid @RequestBody PlanoOrcamentarioModelo plano) {

        if (plano.dataInvalida()) {
            return ResponseEntity.badRequest()
                    .body("A data inicial não pode ser maior que a data final.");
        }

        PlanoOrcamentarioModelo atualizado = service.update(id, plano);

        if (atualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plano não encontrado.");
        }

        return ResponseEntity.ok(atualizado);
    }

    // Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (!service.delete(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plano não encontrado.");
        }
        return ResponseEntity.noContent().build();
    }

    // Verifica se ultrapassou orçamento
    @GetMapping("/{id}/ultrapassou")
    public ResponseEntity<?> verificarUltrapassou(@PathVariable Integer id) {
        Optional<PlanoOrcamentarioModelo> p = service.findById(id);

        if (p.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plano não encontrado.");
        }

        return ResponseEntity.ok(p.get().ultrapassouOrcamento());
    }

    // Retorna saldo do orçamento
    @GetMapping("/{id}/saldo")
    public ResponseEntity<?> saldo(@PathVariable Integer id) {
        Optional<PlanoOrcamentarioModelo> p = service.findById(id);

        if (p.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Plano não encontrado.");
        }

        return ResponseEntity.ok(p.get().calcularSaldoOrcamento());
    }
}

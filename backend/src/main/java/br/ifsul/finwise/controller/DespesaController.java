package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.DespesaModelo;
import br.ifsul.finwise.service.DespesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
@CrossOrigin(origins = "*")
public class DespesaController {

    private final DespesaService service;

    public DespesaController(DespesaService service) {
        this.service = service;
    }

    // Criar despesa
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody DespesaModelo despesa) {
        try {
            DespesaModelo criada = service.criar(despesa);
            return ResponseEntity.ok(criada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Buscar despesa por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(despesa -> ResponseEntity.ok(despesa))
                .orElseGet(() -> ResponseEntity.status(404).body("Despesa não encontrada"));
    }

    // Listar despesas por conta
    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<DespesaModelo>> buscarPorConta(@PathVariable Integer contaId) {
        return ResponseEntity.ok(service.buscarPorConta(contaId));
    }

    // Listar despesas por categoria
    @GetMapping("/categoria/{catId}")
    public ResponseEntity<List<DespesaModelo>> buscarPorCategoria(@PathVariable Integer catId) {
        return ResponseEntity.ok(service.buscarPorCategoria(catId));
    }

    // Atualizar despesa
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody DespesaModelo despesa) {
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(d -> {
                    DespesaModelo atualizada = service.editar(id, despesa);
                    return ResponseEntity.ok(atualizada);
                })
                .orElseGet(() -> ResponseEntity.status(404).body("Despesa não encontrada"));
    }

    // Deletar despesa
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(d -> {
                    service.deletar(id);
                    return ResponseEntity.ok("Despesa deletada com sucesso!");
                })
                .orElseGet(() -> ResponseEntity.status(404).body("Despesa não encontrada"));
    }
}

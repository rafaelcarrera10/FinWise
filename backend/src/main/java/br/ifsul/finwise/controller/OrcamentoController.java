package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.OrcamentoModelo;
import br.ifsul.finwise.service.OrcamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orcamento")
@CrossOrigin(origins = "http://localhost:5173")
public class OrcamentoController {

    @Autowired
    private OrcamentoService orcamentoService;

    // Criar orçamento
    @PostMapping("/create")
    public ResponseEntity<OrcamentoModelo> create(@RequestBody OrcamentoModelo orcamento) {
        try {
            OrcamentoModelo saved = orcamentoService.save(orcamento);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Listar todos os orçamentos
    @GetMapping("/all")
    public ResponseEntity<List<OrcamentoModelo>> getAll() {
        return ResponseEntity.ok(orcamentoService.findAll());
    }

    // Buscar orçamento por ID
    @GetMapping("/by-id")
    public ResponseEntity<OrcamentoModelo> getById(@RequestParam Integer id) {
        return orcamentoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar orçamento
    @PutMapping("/update")
    public ResponseEntity<OrcamentoModelo> update(@RequestBody OrcamentoModelo orcamento) {
        try {
            if (orcamento.getId() == null) {
                return ResponseEntity.badRequest().build();
            }
            OrcamentoModelo updated = orcamentoService.save(orcamento);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Deletar orçamento
    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        try {
            orcamentoService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

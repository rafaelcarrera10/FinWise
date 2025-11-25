package main.java.br.ifsul.finwise.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifsul.finwise.model.ReceitaModelo;
import br.ifsul.finwise.service.ReceitaService;

@RestController
@RequestMapping("/receita")
@CrossOrigin(origins = "*")
public class ReceitaController {

    @Autowired
    private ReceitaService service;

    // Criar receita
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ReceitaModelo receita) {
        try {
            ReceitaModelo criada = service.criar(receita);
            return ResponseEntity.ok(criada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar receita: " + e.getMessage());
        }
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<ReceitaModelo> receita = service.buscarPorId(id);
        return receita.isPresent()
                ? ResponseEntity.ok(receita.get())
                : ResponseEntity.status(404).body("Receita não encontrada");
    }

    // Buscar por conta
    @GetMapping("/conta/{contaId}")
    public ResponseEntity<?> listarPorConta(@PathVariable Integer contaId) {
        List<ReceitaModelo> receitas = service.listarPorConta(contaId);
        return ResponseEntity.ok(receitas);
    }

    // Atualizar receita
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ReceitaModelo receita) {
        try {
            ReceitaModelo atualizada = service.atualizar(id, receita);
            return ResponseEntity.ok(atualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar receita: " + e.getMessage());
        }
    }

    // Deletar receita
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            service.deletar(id);
            return ResponseEntity.ok("Receita deletada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar receita: " + e.getMessage());
        }
    }
}

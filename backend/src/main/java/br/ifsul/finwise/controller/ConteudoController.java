package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.ConteudoModelo;
import br.ifsul.finwise.model.ProfessorModelo;
import br.ifsul.finwise.service.ConteudoService;
import br.ifsul.finwise.service.ProfessorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conteudos")
@CrossOrigin(origins = "http://localhost:5173")
public class ConteudoController {

    @Autowired
    private ConteudoService conteudoService;

    @Autowired
    private ProfessorService professorService;

    // Criar conteúdo (valida se professor existe e está ativo)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ConteudoModelo conteudo) {

        // Verifica professor
        if (conteudo.getProfessor() == null || conteudo.getProfessor().getId() == null) {
            return ResponseEntity.badRequest().body("Professor não informado.");
        }

        ProfessorModelo professor = professorService.findById(
                conteudo.getProfessor().getId()
        ).orElse(null);

        if (professor == null) {
            return ResponseEntity.badRequest().body("Professor não encontrado.");
        }

        if (!Boolean.TRUE.equals(professor.getStatus())) {
            return ResponseEntity.status(403).body("Professor inativo.");
        }

        ConteudoModelo saved = conteudoService.save(conteudo);
        return ResponseEntity.ok(saved);
    }

    // Listar todos
    @GetMapping
    public ResponseEntity<List<ConteudoModelo>> getAll() {
        return ResponseEntity.ok(conteudoService.findAll());
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return conteudoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar conteúdo
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Integer id,
            @RequestBody ConteudoModelo conteudo) {

        return conteudoService.findById(id)
                .map(existing -> {
                    conteudo.setId(id);
                    ConteudoModelo updated = conteudoService.save(conteudo);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        conteudoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar por título parcial
    @GetMapping("/search")
    public ResponseEntity<List<ConteudoModelo>> search(@RequestParam String titulo) {
        return ResponseEntity.ok(conteudoService.searchByTitulo(titulo));
    }

    // Conteúdos por professor
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<?> byProfessor(@PathVariable Integer professorId) {

        ProfessorModelo professor = professorService.findById(professorId).orElse(null);

        if (professor == null) {
            return ResponseEntity.notFound().build();
        }

        if (!Boolean.TRUE.equals(professor.getStatus())) {
            return ResponseEntity.status(403).body("Professor inativo.");
        }

        return ResponseEntity.ok(conteudoService.buscarPorProfessor(professorId));
    }
}

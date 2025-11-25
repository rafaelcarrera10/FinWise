package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.ConteudoModelo;
import br.ifsul.finwise.model.ProfessorModelo;
import br.ifsul.finwise.service.ConteudoService;
import br.ifsul.finwise.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/conteudos")
@CrossOrigin(origins = "http://localhost:5173")
public class ConteudoController {

    @Autowired
    private ConteudoService conteudoService;

    @Autowired
    private ProfessorService professorService; // Usado para buscar o professor

    // Cria um novo conteúdo
    @PostMapping("/create")
    public ResponseEntity<ConteudoModelo> create(@RequestBody ConteudoModelo conteudo) {
        return ResponseEntity.ok(conteudoService.save(conteudo));
    }

    // Lista todos os conteúdos
    @GetMapping("/all")
    public ResponseEntity<List<ConteudoModelo>> getAll() {
        return ResponseEntity.ok(conteudoService.findAll());
    }

    // Busca conteúdo por ID
    @GetMapping("/by-id")
    public ResponseEntity<ConteudoModelo> getById(@RequestParam Integer id) {
        return conteudoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Busca conteúdo por título exato
    @GetMapping("/by-titulo")
    public ResponseEntity<ConteudoModelo> getByTitulo(@RequestParam String titulo) {
        return conteudoService.findByTitulo(titulo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Busca conteúdo por palavra-chave no título
    @GetMapping("/search")
    public ResponseEntity<List<ConteudoModelo>> searchByTitulo(@RequestParam("q") String palavraChave) {
        return ResponseEntity.ok(conteudoService.searchByTitulo(palavraChave));
    }

    // Busca conteúdos de um professor específico
    @GetMapping("/by-professor")
    public ResponseEntity<List<ConteudoModelo>> getByProfessor(@RequestParam Integer professorId) {
        Optional<ProfessorModelo> professor = professorService.findById(professorId);
        if (professor.isEmpty()) {
            return ResponseEntity.notFound().build(); // Professor não encontrado
        }
        return ResponseEntity.ok(conteudoService.findByProfessor(professor.get()));
    }

    // Deleta um conteúdo pelo ID
    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        conteudoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
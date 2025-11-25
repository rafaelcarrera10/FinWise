package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.ListaConteudoModelo;
import br.ifsul.finwise.service.ListaConteudoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/listas-conteudo")
@CrossOrigin(origins = "*")
public class ListaConteudoController {

    private final ListaConteudoService service;

    public ListaConteudoController(ListaConteudoService service) {
        this.service = service;
    }

    // Criar lista de conteúdo
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody ListaConteudoModelo lista) {
        try {
            ListaConteudoModelo criada = service.salvar(lista);
            return ResponseEntity.ok(criada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Buscar lista por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).body("Lista de conteúdo não encontrada"));
    }

    // Listar todas as listas
    @GetMapping
    public ResponseEntity<List<ListaConteudoModelo>> listarTudo() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // Listar listas por professor
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<ListaConteudoModelo>> listarPorProfessor(@PathVariable Integer professorId) {
        return ResponseEntity.ok(service.buscarPorProfessor(professorId));
    }

    // Atualizar lista
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ListaConteudoModelo lista) {
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(existing -> {
                    ListaConteudoModelo atualizada = service.editar(id, lista);
                    return ResponseEntity.ok(atualizada);
                })
                .orElseGet(() -> ResponseEntity.status(404).body("Lista de conteúdo não encontrada"));
    }

    // Deletar lista
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .<ResponseEntity<?>>map(existing -> {
                    service.deletar(id);
                    return ResponseEntity.ok("Lista de conteúdo deletada com sucesso!");
                })
                .orElseGet(() -> ResponseEntity.status(404).body("Lista de conteúdo não encontrada"));
    }
}

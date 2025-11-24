package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.PublicacaoModelo;
import br.ifsul.finwise.model.TagEnum;
import br.ifsul.finwise.service.PublicacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicacoes")
@CrossOrigin(origins = "http://localhost:5173")
public class PublicacaoController {

    @Autowired
    private PublicacaoService publicacaoService;

    // Cria uma nova publicação
    @PostMapping("/create")
    public ResponseEntity<PublicacaoModelo> create(@RequestBody PublicacaoModelo publicacao) {
        return ResponseEntity.ok(publicacaoService.save(publicacao));
    }

    // Lista todas as publicações
    @GetMapping("/all")
    public ResponseEntity<List<PublicacaoModelo>> getAll() {
        return ResponseEntity.ok(publicacaoService.findAll());
    }

    // Busca publicação por ID
    @GetMapping("/by-id")
    public ResponseEntity<PublicacaoModelo> getById(@RequestParam Integer id) {
        return publicacaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Busca publicação por título exato
    @GetMapping("/by-titulo")
    public ResponseEntity<PublicacaoModelo> getByTitulo(@RequestParam String titulo) {
        return publicacaoService.findByTitulo(titulo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Busca publicação por palavra-chave no título
    @GetMapping("/search")
    public ResponseEntity<List<PublicacaoModelo>> searchByTitulo(@RequestParam("q") String palavraChave) {
        return ResponseEntity.ok(publicacaoService.searchByTitulo(palavraChave));
    }

    // Busca publicação por Tag
    @GetMapping("/by-tag")
    public ResponseEntity<List<PublicacaoModelo>> getByTag(@RequestParam TagEnum tag) {
        return ResponseEntity.ok(publicacaoService.findByTag(tag));
    }

    // Deleta uma publicação pelo ID
    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        publicacaoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
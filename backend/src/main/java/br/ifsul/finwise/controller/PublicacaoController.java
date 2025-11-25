package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.PublicacaoModelo;
import br.ifsul.finwise.model.TagEnum;
import br.ifsul.finwise.service.PublicacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicacoes")
@CrossOrigin(origins = "*")
public class PublicacaoController {

    private final PublicacaoService publicacaoService;

    public PublicacaoController(PublicacaoService publicacaoService) {
        this.publicacaoService = publicacaoService;
    }

    @PostMapping("/create")
    public ResponseEntity<PublicacaoModelo> create(@RequestBody PublicacaoModelo publicacao) {
        return ResponseEntity.ok(publicacaoService.save(publicacao));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PublicacaoModelo>> getAll() {
        return ResponseEntity.ok(publicacaoService.findAll());
    }

    @GetMapping("/by-id")
    public ResponseEntity<PublicacaoModelo> getById(@RequestParam Integer id) {
        return publicacaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-titulo")
    public ResponseEntity<PublicacaoModelo> getByTitulo(@RequestParam String titulo) {
        return publicacaoService.findByTitulo(titulo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PublicacaoModelo>> searchByTitulo(@RequestParam("q") String palavraChave) {
        List<PublicacaoModelo> resultados = publicacaoService.searchByTitulo(palavraChave);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }

    @GetMapping("/by-tag")
    public ResponseEntity<List<PublicacaoModelo>> getByTag(@RequestParam TagEnum tag) {
        List<PublicacaoModelo> resultados = publicacaoService.findByTag(tag);
        return resultados.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(resultados);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        publicacaoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

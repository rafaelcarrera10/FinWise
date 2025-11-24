package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.TagEnum;
import br.ifsul.finwise.model.VideoModelo;
import br.ifsul.finwise.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
@CrossOrigin(origins = "http://localhost:5173")
public class VideoController {

    @Autowired
    private VideoService videoService;

    // Cria um novo vídeo
    @PostMapping("/create")
    public ResponseEntity<VideoModelo> create(@RequestBody VideoModelo video) {
        return ResponseEntity.ok(videoService.save(video));
    }

    // Lista todos os vídeos
    @GetMapping("/all")
    public ResponseEntity<List<VideoModelo>> getAll() {
        return ResponseEntity.ok(videoService.findAll());
    }

    // Busca vídeo por ID
    @GetMapping("/by-id")
    public ResponseEntity<VideoModelo> getById(@RequestParam Integer id) {
        return videoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Busca vídeo por título exato
    @GetMapping("/by-titulo")
    public ResponseEntity<VideoModelo> getByTitulo(@RequestParam String titulo) {
        return videoService.findByTitulo(titulo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Busca vídeo por palavra-chave no título
    @GetMapping("/search")
    public ResponseEntity<List<VideoModelo>> searchByTitulo(@RequestParam("q") String palavraChave) {
        return ResponseEntity.ok(videoService.searchByTitulo(palavraChave));
    }

    // Busca vídeo por Tag
    @GetMapping("/by-tag")
    public ResponseEntity<List<VideoModelo>> getByTag(@RequestParam TagEnum tag) {
        return ResponseEntity.ok(videoService.findByTag(tag));
    }

    // Deleta um vídeo pelo ID
    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        videoService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
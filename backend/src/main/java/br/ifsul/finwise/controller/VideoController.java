package br.ifsul.finwise.controller;

import br.ifsul.finwise.DTO.VideoDTO;
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

    // Criar vídeo
    @PostMapping("/create")
    public ResponseEntity<VideoModelo> create(@RequestBody VideoModelo video) {
        try {
            VideoModelo saved = videoService.save(video);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Listar todos os vídeos
    @GetMapping("/all")
    public List<VideoDTO> getAll() {
        return videoService.findAll()
            .stream()
            .map(VideoDTO::toDTO)
            .toList();
    }


    // Buscar vídeo por ID
    @GetMapping("/by-id")
    public ResponseEntity<VideoModelo> getById(@RequestParam Integer id) {
        return videoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar vídeo por título exato
    @GetMapping("/by-titulo")
    public ResponseEntity<VideoModelo> getByTitulo(@RequestParam String titulo) {
        return videoService.findByTitulo(titulo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Buscar vídeo por palavra-chave
    @GetMapping("/search")
    public ResponseEntity<List<VideoModelo>> searchByTitulo(@RequestParam("q") String palavraChave) {
        List<VideoModelo> videos = videoService.searchByTitulo(palavraChave);
        return ResponseEntity.ok(videos);
    }

    // Buscar por Tag
    @GetMapping("/by-tag")
    public ResponseEntity<List<VideoModelo>> getByTag(@RequestParam TagEnum tag) {
        List<VideoModelo> videos = videoService.findByTag(tag);
        return ResponseEntity.ok(videos);
    }

    // Deletar vídeo
    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        try {
            videoService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

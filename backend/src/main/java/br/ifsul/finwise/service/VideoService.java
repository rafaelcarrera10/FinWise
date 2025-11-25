package br.ifsul.finwise.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import br.ifsul.finwise.model.VideoModelo;
import br.ifsul.finwise.model.TagEnum;
import br.ifsul.finwise.repository.VideoRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    private VideoRepositorio videoRepositorio;

    // Criar ou atualizar vídeo
    public VideoModelo save(VideoModelo video) {
        return videoRepositorio.save(video);
    }

    // Buscar todos os vídeos
    public List<VideoModelo> findAll() {
        return videoRepositorio.findAll();
    }

    // Buscar vídeo por ID
    public Optional<VideoModelo> findById(Integer id) {
        return videoRepositorio.findById(id);
    }

    // Buscar por título exato
    public Optional<VideoModelo> findByTitulo(String titulo) {
        return videoRepositorio.findByTitulo(titulo);
    }

    // Buscar por título parcialmente
    public List<VideoModelo> searchByTitulo(String palavraChave) {
        return videoRepositorio.findByTituloContainingIgnoreCase(palavraChave);
    }

    // Buscar por tag
    public List<VideoModelo> findByTag(TagEnum tag) {
        return videoRepositorio.findByTag(tag);
    }

    // Deletar vídeo
    @Transactional
    public void deleteById(Integer id) {
        if (!videoRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Vídeo não encontrado");
        }
        videoRepositorio.deleteById(id);
    }
}

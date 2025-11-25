package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.VideoModelo;
import br.ifsul.finwise.model.TagEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepositorio extends JpaRepository<VideoModelo, Integer> {

    Optional<VideoModelo> findByTitulo(String titulo);

    List<VideoModelo> findByTag(TagEnum tag);

    List<VideoModelo> findByTituloContainingIgnoreCase(String palavraChave);

    Optional<VideoModelo> findById(Integer id);
}

package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.VideoModelo;
import br.ifsul.finwise.model.TagEnum;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepositorio extends JpaRepository<VideoModelo, Integer> {

    // Busca por título exato
    Optional<VideoModelo> findByTitulo(String titulo);

    // Busca todos os vídeos de uma determinada tag
    List<VideoModelo> findByTag(TagEnum tag);
    
    // Busca vídeos cujo título contém uma palavra ou frase
    List<VideoModelo> findByTituloContainingIgnoreCase(String palavraChave);
}

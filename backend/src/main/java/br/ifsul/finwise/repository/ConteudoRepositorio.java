package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.ConteudoModelo;
import br.ifsul.finwise.model.ProfessorModelo;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConteudoRepositorio extends JpaRepository<ConteudoModelo, Integer> {

    // Busca por título exato
    Optional<ConteudoModelo> findByTitulo(String titulo);

    // Busca por título parcialmente
    List<ConteudoModelo> findByTituloContainingIgnoreCase(String palavraChave);

    // Busca todos os conteúdos de um professor
    List<ConteudoModelo> findByProfessor(ProfessorModelo professor);

}
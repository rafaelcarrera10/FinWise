package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.ConteudoModelo;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConteudoRepositorio extends JpaRepository<ConteudoModelo, Integer> {

    // Busca por título exato
    Optional<ConteudoModelo> findByTitulo(String titulo);


    // Busca por título parcialmente
    List<ConteudoModelo> findByTituloContainingIgnoreCase(String palavraChave);

    // Busca por ID
    Optional<ConteudoModelo> findById(Integer id);

    // Busca todos os conteúdos de um professor
    @Query("SELECT c FROM ConteudoModelo c WHERE c.professor.id = :professorId")
    List<ConteudoModelo> findByProfessorId(@Param("professorId") Integer professorId);
    
    // Deletar por ID
    @Query("DELETE FROM ConteudoModelo c WHERE c.id = :id")
    void deleteById(@Param("id") Integer id);

}
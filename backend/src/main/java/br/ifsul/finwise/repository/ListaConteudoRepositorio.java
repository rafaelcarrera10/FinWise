package br.ifsul.finwise.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.ListaConteudoModelo;
import br.ifsul.finwise.model.TagEnum;
import jakarta.transaction.Transactional;

@Repository
public interface ListaConteudoRepositorio extends JpaRepository<ListaConteudoModelo, Integer> {

    List<ListaConteudoModelo> findByNome(String nome);

    List<ListaConteudoModelo> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT l FROM ListaConteudoModelo l WHERE l.professor.id = :profId")
    List<ListaConteudoModelo> findByProfessorId(@Param("profId") Integer professorId);

    @Query("SELECT l FROM ListaConteudoModelo l JOIN l.tags t WHERE t = :tag")
    List<ListaConteudoModelo> findByTag(@Param("tag") TagEnum tag);

    @Query("SELECT l FROM ListaConteudoModelo l JOIN l.livros c WHERE c.id = :conteudoId")
    List<ListaConteudoModelo> findByConteudoId(@Param("conteudoId") Integer conteudoId);

    @Modifying
    @Transactional
    @Query("DELETE FROM ListaConteudoModelo l WHERE l.id = :id")
    void deleteById(@Param("id") Integer id);
}


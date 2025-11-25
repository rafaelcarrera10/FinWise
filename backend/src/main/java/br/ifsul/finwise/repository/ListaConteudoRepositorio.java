package br.ifsul.finwise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.ListaConteudoModelo;
import br.ifsul.finwise.model.TagEnum;

@Repository
public interface ListaConteudoRepositorio extends JpaRepository<ListaConteudoModelo, Integer> {

    // Busca listas pelo nome (exato)
    List<ListaConteudoModelo> findByNome(String nome);

    // Busca lista por ID
    Optional<ListaConteudoModelo> findById(Integer id);

    // Busca listas que contenham parte do nome (LIKE)
    List<ListaConteudoModelo> findByNomeContainingIgnoreCase(String nome);

    // Busca listas de um professor específico
    @Query("SELECT l FROM ListaConteudoModelo l WHERE l.professor.id = :profId")
    List<ListaConteudoModelo> findByProfessorId(@Param("profId") Integer professorId);

    // Busca por tag específica
    @Query("SELECT l FROM ListaConteudoModelo l JOIN l.tags t WHERE t = :tag")
    List<ListaConteudoModelo> findByTag(@Param("tag") TagEnum tag);

    // Busca listas que contenham determinado conteúdo/livro
    @Query("SELECT l FROM ListaConteudoModelo l JOIN l.livros c WHERE c.id = :conteudoId")
    List<ListaConteudoModelo> findByConteudoId(@Param("conteudoId") Integer conteudoId);

    // Deletar lista 
    @Query("DELETE FROM ListaConteudoModel c WHERE c.id =:id")
    void deleteById(@Param("id") Integer id);
}

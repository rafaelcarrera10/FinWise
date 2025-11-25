package br.ifsul.finwise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.ProfessorModelo;
import br.ifsul.finwise.model.PublicacaoModelo;
import br.ifsul.finwise.model.TagEnum;

@Repository
public interface PublicacaoRepositorio extends JpaRepository<PublicacaoModelo, Integer> {

    // Buscar por título exato
    Optional<PublicacaoModelo> findByTitulo(String titulo);

    // Buscar por título parcialmente
    List<PublicacaoModelo> findByTituloContainingIgnoreCase(String titulo);

    // Buscar por tag
    List<PublicacaoModelo> findByTag(TagEnum tag);

    // Buscar por ID
    Optional<PublicacaoModelo> findById(Integer id);

    // Buscar por professor
    List<PublicacaoModelo> findByProfessor(ProfessorModelo professor);


    // Deletar por ID
    @Query("DELETE FROM PublicacaoModelo p WHERE p.id = :id")
    void deleteById(@Param("id") Integer id);
}

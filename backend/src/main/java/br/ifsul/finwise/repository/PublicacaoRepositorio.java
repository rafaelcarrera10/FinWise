package br.ifsul.finwise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ifsul.finwise.model.ProfessorModelo;
import br.ifsul.finwise.model.PublicacaoModelo;
import br.ifsul.finwise.model.TagEnum;

@Repository
public interface PublicacaoRepositorio extends JpaRepository<PublicacaoModelo, Integer> {

    Optional<PublicacaoModelo> findByTitulo(String titulo);

    List<PublicacaoModelo> findByTituloContainingIgnoreCase(String titulo);

    List<PublicacaoModelo> findByTag(TagEnum tag);

    Optional<PublicacaoModelo> findById(Integer id);

    List<PublicacaoModelo> findByProfessor(ProfessorModelo professor);

    @Modifying
    @Transactional
    @Query("DELETE FROM PublicacaoModelo p WHERE p.id = :id")
    void deleteById(@Param("id") Integer id);
}


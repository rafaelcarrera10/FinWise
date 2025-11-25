package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ifsul.finwise.model.CategoriaModelo;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepositorio extends JpaRepository<CategoriaModelo, Integer> {

    // Buscar por nome exato
    Optional<CategoriaModelo> findByName(String name);

    // Listar categorias por usuário
    @Query("SELECT c FROM CategoriaModelo c WHERE c.usuario.id = :userId")
    List<CategoriaModelo> findByUsuarioId(@Param("userId") Integer userId);

    // Ordenadas por nome
    @Query("SELECT c FROM CategoriaModelo c WHERE c.usuario.id = :userId ORDER BY c.name ASC")
    List<CategoriaModelo> findByUsuarioIdOrderByNameAsc(@Param("userId") Integer userId);

    // Delete com JPQL correto
    @Modifying
    @Transactional
    @Query("DELETE FROM CategoriaModelo c WHERE c.name = :name")
    int deleteByName(@Param("name") String name);
}

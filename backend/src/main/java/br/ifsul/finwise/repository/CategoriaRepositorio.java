package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import br.ifsul.finwise.model.CategoriaModelo;
import java.util.List;


@Repository
public interface CategoriaRepositorio extends JpaRepository<CategoriaModelo, Integer>{

    // Buscar por categoria
    Optional<CategoriaModelo> findByName(String name);

    // Busca categorias de um usuário específico
    @Query("SELECT c FROM CategoriaModelo c WHERE c.usuario.id = :userId")
    List<CategoriaModelo> findByUsuarioId(@Param("userId") Integer userId);

    // Lista categorias de um usuário ordenadas por nome
    @Query("SELECT c FROM CategoriaModelo c WHERE c.usuario.id = :userId ORDER BY c.name ASC")
    List<CategoriaModelo> findByUsuarioIdOrderByNameAsc(@Param("userId") Integer userId);

    // Deleta categorias pelo nome
    @Query("DELETE FROM CategoriaModelo c WHERE c.name = :name")
    int deleteByName(@Param("name") String name);

}
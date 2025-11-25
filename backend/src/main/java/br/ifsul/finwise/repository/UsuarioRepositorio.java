package br.ifsul.finwise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.UsuarioModelo;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioModelo, Integer> {

    // Busca usuário por nome exato (case-sensitive ou insensitive)
    Optional<UsuarioModelo> findByName(String name);
    Optional<UsuarioModelo> findByNameIgnoreCase(String name);

    // Fragmentos de nome
    List<UsuarioModelo> findByNameContainingIgnoreCase(String fragment);
    List<UsuarioModelo> findByNameStartingWithIgnoreCase(String prefix);

    // Verificação de existência
    boolean existsByName(String name);

    // Atualizações via JPQL
    @Modifying
    @Query("UPDATE UsuarioModelo u SET u.name = :newName WHERE u.id = :id")
    int updateUserNameById(@Param("id") Integer id, @Param("newName") String newName);

    @Modifying
    @Query("UPDATE UsuarioModelo u SET u.senha = :newPassword WHERE u.id = :id")
    int updateUserPasswordById(@Param("id") Integer id, @Param("newPassword") String newPassword);

    // Listagens ordenadas
    List<UsuarioModelo> findAllByOrderByNameAsc();
    List<UsuarioModelo> findAllByOrderByIdDesc();

    // Buscar por lista de ids
    List<UsuarioModelo> findByIdIn(List<Integer> ids);
}

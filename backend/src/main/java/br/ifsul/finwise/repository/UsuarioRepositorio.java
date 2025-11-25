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

    // Buscas simples
    Optional<UsuarioModelo> findByNameAndSenha(String name, String senha); // raramente usado diretamente (ex.: para autenticação)
    Optional<UsuarioModelo> findByName(String name);
    Optional<UsuarioModelo> findByNameIgnoreCase(String name);
    boolean existsByName(String name);

    // Buscas por fragmento de nome
    List<UsuarioModelo> findByNameContainingIgnoreCase(String fragment);
    List<UsuarioModelo> findByNameStartingWithIgnoreCase(String prefix);

    // Atualizações via JPQL: precisam de @Modifying e chamada dentro de @Transactional
    @Modifying
    @Query("UPDATE UsuarioModelo u SET u.name = :newName WHERE u.id = :id")
    int updateUserNameById(@Param("id") Integer id, @Param("newName") String newName);

    @Modifying
    @Query("UPDATE UsuarioModelo u SET u.senha = :newPassword WHERE u.id = :id")
    int updateUserPasswordById(@Param("id") Integer id, @Param("newPassword") String newPassword);

    // Exemplos de métodos auxiliares que você pode querer
    List<UsuarioModelo> findAllByOrderByNameAsc();
    List<UsuarioModelo> findAllByOrderByIdDesc();

    // Exemplo de busca por lista de ids (Spring Data cria automaticamente se seguir a convenção)
    List<UsuarioModelo> findByIdIn(List<Integer> ids);
}

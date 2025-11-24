package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.UsuarioModelo;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioModelo, Long> {

    // Login
    Optional<UsuarioModelo> findByNameAndSenha(String name, String senha);

    // Buscar usuário por nome
    Optional<UsuarioModelo> findByName(String name);

    // Buscar usuário por nome (ignorando maiusculas e minusculas)
    Optional<UsuarioModelo> findByNameIgnoreCase(String name);

    // Busca se o Usuário existe
    boolean existsByName(String name);

    // Busca usuários com paginação (usando offset e limit)
    @Query("SELECT u FROM UsuarioModelo u ORDER BY u.id ASC")
    List<UsuarioModelo> findUsersWithPagination(@Param("offset") int offset, @Param("limit") int limit);
   
    // Atualiza nome do usuário
    @Query("UPDATE UsuarioModelo u SET u.name = :newName WHERE u.id = :id")
    int updateUserNameById(@Param("id") Long id, @Param("newName") String newName);

    // Atualiza senha do usuário
    @Query("UPDATE UsuarioModelo u SET u.password = :newPassword WHERE u.id = :id")
    int updateUserPasswordById(@Param("id") Long id, @Param("newPassword") String newPassword);

}

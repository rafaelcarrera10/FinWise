package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.ProfessorModelo;
import br.ifsul.finwise.model.UsuarioModelo;
import java.util.List;

@Repository
public interface ProfessorRepositorio extends JpaRepository<ProfessorModelo, Long> {

    // Busca usuários contendo parte do nome (ignora maiúsculas/minúsculas)
    List<UsuarioModelo> findByNameContainingIgnoreCase(String name);

    // Busca usuários por nome exato (ignora maiúsculas/minúsculas)
    List<UsuarioModelo> findByNameIgnoreCase(String name);

    // Busca usuários cujo nome começa com o prefixo informado
    List<UsuarioModelo> findByNameStartingWithIgnoreCase(String prefix);

    // Buscar status do professor por id
    @Query("SELECT p FROM ProfessorModelo WHERE p.id = id")
    List<ProfessorModelo> findStatusById(@Param("id") Long id);

    // Lista usuários em ordem alfabética
    @Query("SELECT u FROM UsuarioModelo u ORDER BY u.name ASC")
    List<UsuarioModelo> findAllOrderByNameAsc();

    // Lista usuários por ID decrescente (mais recentes primeiro)
    @Query("SELECT u FROM UsuarioModelo u ORDER BY u.id DESC")
    List<UsuarioModelo> findAllOrderByIdDesc();

    // Busca usuários por múltiplos IDs
    @Query("SELECT u FROM UsuarioModelo u WHERE u.id IN :ids")
    List<UsuarioModelo> findByIds(@Param("ids") List<Long> ids);

     // Busca foto de perfil do usuário por ID
    @Query("SELECT u.fotoPerfil FROM ProfessorModelo u WHERE u.id = :id")
    byte[] findfotoPerfilByUserId(@Param("id") Long id);

    // Busca biografia do usuário por ID
    @Query("SELECT u.biografia FROM ProfessorModelo u WHERE u.id = :id")
    String findbiografiaByUserId(@Param("id") Long id);

    // Atualiza nome do usuário
    @Query("UPDATE UsuarioModelo u SET u.name = :newName WHERE u.id = :id")
    int updateUserNameById(@Param("id") Long id, @Param("newName") String newName);

    // Atualiza senha do usuário
    @Query("UPDATE UsuarioModelo u SET u.password = :newPassword WHERE u.id = :id")
    int updateUserPasswordById(@Param("id") Long id, @Param("newPassword") String newPassword);

    // Atualiza foto de perfil do usuário
    @Query("UPDATE ProfessorModelo u SET u.fotoPerfil = :fotoPerfil WHERE u.id = :id")
    int updateUserfotoPerfilById(@Param("id") Long id, @Param("fotoPerfil") byte[] fotoPerfil);

    // Atualiza biografia do usuário
    @Query("UPDATE ProfessorModelo u SET u.biografia = :biografia WHERE u.id = :id")
    int updateUserbiografiaById(@Param("id") Long id, @Param("biografia") String biografia);

    // Exclui usuários pelo nome
    @Query("DELETE FROM UsuarioModelo u WHERE u.name = :name")
    int deleteByName(@Param("name") String name);

    // Remove foto de perfil do usuário
    @Query("UPDATE ProfessorModelo u SET u.fotoPerfil = null WHERE u.id = :id")
    int removeUserfotoPerfilById(@Param("id") Long id);

    // Remove descrição do usuário
    @Query("UPDATE ProfessorModelo u SET u.biografia = null WHERE u.id = :id")
    int removeUserbiografiaById(@Param("id") Long id);
}

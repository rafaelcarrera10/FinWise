package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.UserModel;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    // Busca usuário por email
    Optional<UserModel> findByEmail(String email);

    // Busca usuários contendo parte do nome (ignora maiúsculas/minúsculas)
    List<UserModel> findByNameContainingIgnoreCase(String name);

    // Verifica se existe usuário com o email informado
    boolean existsByEmail(String email);

    // Conta o total de usuários
    long count();

    // Busca usuários por nome exato (ignora maiúsculas/minúsculas)
    List<UserModel> findByNameIgnoreCase(String name);

    // Busca usuários cujo nome começa com o prefixo informado
    List<UserModel> findByNameStartingWithIgnoreCase(String prefix);

    // Lista usuários em ordem alfabética
    @Query("SELECT u FROM UserModel u ORDER BY u.name ASC")
    List<UserModel> findAllOrderByNameAsc();

    // Lista usuários por ID decrescente (mais recentes primeiro)
    @Query("SELECT u FROM UserModel u ORDER BY u.id DESC")
    List<UserModel> findAllOrderByIdDesc();

    // Busca usuários com paginação (usando offset e limit)
    @Query("SELECT u FROM UserModel u ORDER BY u.id ASC")
    List<UserModel> findUsersWithPagination(@Param("offset") int offset, @Param("limit") int limit);

    // Busca usuários por múltiplos IDs
    @Query("SELECT u FROM UserModel u WHERE u.id IN :ids")
    List<UserModel> findByIds(@Param("ids") List<Long> ids);

    // Busca usuários cujo email contém o texto informado
    @Query("SELECT u FROM UserModel u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :emailPart, '%'))")
    List<UserModel> findByEmailContaining(@Param("emailPart") String emailPart);

    // Busca usuários cujo nome ou email contêm o texto informado
    @Query("SELECT u FROM UserModel u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<UserModel> findByNameOrEmailContaining(@Param("searchText") String searchText);

    // Busca foto de perfil do usuário por ID
    @Query("SELECT u.photo FROM UserModel u WHERE u.id = :id")
    byte[] findPhotoByUserId(@Param("id") Long id);

    // Busca descrição do usuário por ID
    @Query("SELECT u.description FROM UserModel u WHERE u.id = :id")
    String findDescriptionByUserId(@Param("id") Long id);

    // Atualiza nome do usuário
    @Query("UPDATE UserModel u SET u.name = :newName WHERE u.id = :id")
    int updateUserNameById(@Param("id") Long id, @Param("newName") String newName);

    // Atualiza email do usuário
    @Query("UPDATE UserModel u SET u.email = :newEmail WHERE u.id = :id")
    int updateUserEmailById(@Param("id") Long id, @Param("newEmail") String newEmail);

    // Atualiza senha do usuário
    @Query("UPDATE UserModel u SET u.password = :newPassword WHERE u.id = :id")
    int updateUserPasswordById(@Param("id") Long id, @Param("newPassword") String newPassword);

    // Atualiza foto de perfil do usuário
    @Query("UPDATE UserModel u SET u.photo = :photo WHERE u.id = :id")
    int updateUserPhotoById(@Param("id") Long id, @Param("photo") byte[] photo);

    // Atualiza descrição do usuário
    @Query("UPDATE UserModel u SET u.description = :description WHERE u.id = :id")
    int updateUserDescriptionById(@Param("id") Long id, @Param("description") String description);

    // Exclui usuário por email
    @Query("DELETE FROM UserModel u WHERE u.email = :email")
    int deleteByEmail(@Param("email") String email);

    // Exclui usuários pelo nome
    @Query("DELETE FROM UserModel u WHERE u.name = :name")
    int deleteByName(@Param("name") String name);

    // Remove foto de perfil do usuário
    @Query("UPDATE UserModel u SET u.photo = null WHERE u.id = :id")
    int removeUserPhotoById(@Param("id") Long id);

    // Remove descrição do usuário
    @Query("UPDATE UserModel u SET u.description = null WHERE u.id = :id")
    int removeUserDescriptionById(@Param("id") Long id);
}

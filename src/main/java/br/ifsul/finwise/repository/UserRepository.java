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
    
    // CRUD - Buscar

    /**
     * Busca usuário por email
     * @param email Email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<UserModel> findByEmail(String email);
    
    /**
     * Busca usuário por nome (busca parcial, case insensitive)
     * @param name Nome ou parte do nome do usuário
     * @return Lista de usuários que contêm o nome
     */
    List<UserModel> findByNameContainingIgnoreCase(String name);
    
    /**
     * Verifica se existe um usuário com o email especificado
     * @param email Email a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByEmail(String email);
    
    /**
     * Conta quantos usuários existem no sistema
     * @return Número total de usuários
     */
    long count();
    
    /**
     * Busca usuários por nome exato (case insensitive)
     * @param name Nome exato do usuário
     * @return Lista de usuários com o nome exato
     */
    List<UserModel> findByNameIgnoreCase(String name);
    
    /**
     * Busca usuários cujo nome começa com o texto especificado
     * @param prefix Prefixo do nome
     * @return Lista de usuários cujo nome começa com o prefixo
     */
    List<UserModel> findByNameStartingWithIgnoreCase(String prefix);

    /**
     * Busca usuários ordenados por nome
     * @return Lista de usuários ordenados alfabeticamente por nome
     */
    @Query("SELECT u FROM UserModel u ORDER BY u.name ASC")
    List<UserModel> findAllOrderByNameAsc();
    
    /**
     * Busca usuários ordenados por data de criação (ID)
     * @return Lista de usuários ordenados por ID (mais recentes primeiro)
     */
    @Query("SELECT u FROM UserModel u ORDER BY u.id DESC")
    List<UserModel> findAllOrderByIdDesc();
    
    /**
     * Busca usuários com paginação
     * @param offset Posição inicial
     * @param limit Número máximo de registros
     * @return Lista de usuários com paginação
     */
    @Query("SELECT u FROM UserModel u ORDER BY u.id ASC")
    List<UserModel> findUsersWithPagination(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * Busca usuários por múltiplos IDs
     * @param ids Lista de IDs
     * @return Lista de usuários encontrados
     */
    @Query("SELECT u FROM UserModel u WHERE u.id IN :ids")
    List<UserModel> findByIds(@Param("ids") List<Long> ids);
    
    /**
     * Busca usuários cujo email contém o texto especificado
     * @param emailPart Parte do email
     * @return Lista de usuários cujo email contém o texto
     */
    @Query("SELECT u FROM UserModel u WHERE u.email LIKE %:emailPart%")
    List<UserModel> findByEmailContaining(@Param("emailPart") String emailPart);
    
    /**
     * Busca usuários com nome ou email que contenham o texto especificado
     * @param searchText Texto para busca
     * @return Lista de usuários encontrados
     */
    @Query("SELECT u FROM UserModel u WHERE u.name LIKE %:searchText% OR u.email LIKE %:searchText%")
    List<UserModel> findByNameOrEmailContaining(@Param("searchText") String searchText);

    /** 
     * Buscar foto de perfil por ID do usuário (professor) 
     * @param id ID do usuário
     * @return Foto em formato de array de bytes
     */
    @Query("SELECT t.photo FROM UserModel t WHERE t.id = :id")
    byte[] findPhotoByUserId(@Param("id") Long id);

    /** 
     * Buscar descrição por ID do usuário (professor)
     * @param id ID do usuário
     * @return Descrição do professor
     */
    @Query("SELECT t.description FROM UserModel t WHERE t.id = :id")
    String findDescriptionByUserId(@Param("id") Long id);

    // CRUD - Atualizar
    
    /**
     * Atualiza o nome de um usuário por ID
     * @param id ID do usuário
     * @param newName Novo nome
     * @return Número de registros atualizados
     */
    @Query("UPDATE UserModel u SET u.name = :newName WHERE u.id = :id")
    int updateUserNameById(@Param("id") Long id, @Param("newName") String newName);
    
    /**
     * Atualiza o email de um usuário por ID
     * @param id ID do usuário
     * @param newEmail Novo email
     * @return Número de registros atualizados
     */
    @Query("UPDATE UserModel u SET u.email = :newEmail WHERE u.id = :id")
    int updateUserEmailById(@Param("id") Long id, @Param("newEmail") String newEmail);
    
    /**
     * Atualiza a senha de um usuário por ID
     * @param id ID do usuário
     * @param newPassword Nova senha (já criptografada)
     * @return Número de registros atualizados
     */
    @Query("UPDATE UserModel u SET u.password = :newPassword WHERE u.id = :id")
    int updateUserPasswordById(@Param("id") Long id, @Param("newPassword") String newPassword);

    /** 
     * Atualiza a foto de perfil do usuário (professor) por ID
     * @param id ID do usuário
     */
    @Query("UPDATE UserModel t SET t.photo = :photo WHERE t.id = :id")
    int updateUserPhotoById(@Param("id") Long id, @Param("photo") byte[] photo);

    /**
     * Atualiza a descrição do professor por ID
     * @param id ID do usuário
     * @param description Nova descrição
     * @return Número de registros atualizados
     */
    @Query("UPDATE UserModel t SET t.description = :description WHERE t.id = :id")
    int updateTeacherDescriptionById(@Param("id") Long id, @Param("description") String description);
     
    // CRUD - Deletar
    
    /**
     * Remove usuário por email
     * @param email Email do usuário a ser removido
     * @return Número de registros removidos
     */
    @Query("DELETE FROM UserModel u WHERE u.email = :email")
    int deleteByEmail(@Param("email") String email);
    
    /**
     * Remove todos os usuários com nome específico
     * @param name Nome dos usuários a serem removidos
     * @return Número de registros removidos
     */
    @Query("DELETE FROM UserModel u WHERE u.name = :name")
    int deleteByName(@Param("name") String name);

    /**
     * Remover foto de perfil do usuário (professor) por ID
     * @param id ID do usuário
     * @return Número de registros atualizados
     */
    @Query ("DELETE UserModel t SET t.photo = null WHERE t.id = :id")
    int removeUserPhotoById(@Param("id") Long id);

    /**
     * Remover descrição do professor por ID
     * @param id ID do usuário
     */
    @Query ("DELETE UserModel t SET t.description = null WHERE t.id = :id")
    int removeTeacherDescriptionById(@Param("id") Long id);
    
}
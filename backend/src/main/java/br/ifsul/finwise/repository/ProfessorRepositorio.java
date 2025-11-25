package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.ifsul.finwise.model.ProfessorModelo;
import java.util.List;

@Repository
public interface ProfessorRepositorio extends JpaRepository<ProfessorModelo, Integer> {

    // Busca professores pelo nome (contendo, ignorando maiúsculas/minúsculas)
    List<ProfessorModelo> findByNameContainingIgnoreCase(String name);

    // Busca professores pelo nome exato (ignorando maiúsculas/minúsculas)
    List<ProfessorModelo> findByNameIgnoreCase(String name);

    // Busca professores cujo nome começa com prefixo
    List<ProfessorModelo> findByNameStartingWithIgnoreCase(String prefix);

    // Buscar status do professor por id
    @Query("SELECT p.status FROM ProfessorModelo p WHERE p.id = :id")
    Boolean findStatusById(@Param("id") Integer id);

    // Lista professores em ordem alfabética
    @Query("SELECT p FROM ProfessorModelo p ORDER BY p.name ASC")
    List<ProfessorModelo> findAllOrderByNameAsc();

    // Lista professores em ordem decrescente pelo ID
    @Query("SELECT p FROM ProfessorModelo p ORDER BY p.id DESC")
    List<ProfessorModelo> findAllOrderByIdDesc();

    // Busca professores por múltiplos IDs
    @Query("SELECT p FROM ProfessorModelo p WHERE p.id IN :ids")
    List<ProfessorModelo> findByIds(@Param("ids") List<Integer> ids);

    // Foto de perfil
    @Query("SELECT p.fotoPerfil FROM ProfessorModelo p WHERE p.id = :id")
    byte[] findFotoPerfilById(@Param("id") Integer id);

    // Biografia
    @Query("SELECT p.biografia FROM ProfessorModelo p WHERE p.id = :id")
    String findBiografiaById(@Param("id") Integer id);

    // Atualizações
    @Modifying
    @Transactional
    @Query("UPDATE ProfessorModelo p SET p.name = :newName WHERE p.id = :id")
    int updateNameById(@Param("id") Integer id, @Param("newName") String newName);

    @Modifying
    @Transactional
    @Query("UPDATE ProfessorModelo p SET p.password = :newPassword WHERE p.id = :id")
    int updatePasswordById(@Param("id") Integer id, @Param("newPassword") String newPassword);

    @Modifying
    @Transactional
    @Query("UPDATE ProfessorModelo p SET p.fotoPerfil = :fotoPerfil WHERE p.id = :id")
    int updateFotoPerfilById(@Param("id") Integer id, @Param("fotoPerfil") byte[] fotoPerfil);

    @Modifying
    @Transactional
    @Query("UPDATE ProfessorModelo p SET p.biografia = :biografia WHERE p.id = :id")
    int updateBiografiaById(@Param("id") Integer id, @Param("biografia") String biografia);

    // Remove campos
    @Modifying
    @Transactional
    @Query("UPDATE ProfessorModelo p SET p.fotoPerfil = null WHERE p.id = :id")
    int removeFotoPerfilById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE ProfessorModelo p SET p.biografia = null WHERE p.id = :id")
    int removeBiografiaById(@Param("id") Integer id);

    // Deletar por nome
    @Modifying
    @Transactional
    @Query("DELETE FROM ProfessorModelo p WHERE p.name = :name")
    int deleteByName(@Param("name") String name);
}


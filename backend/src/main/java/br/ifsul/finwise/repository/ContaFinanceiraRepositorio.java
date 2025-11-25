package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.ContaFinanceiraModelo;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ContaFinanceiraRepositorio extends JpaRepository<ContaFinanceiraModelo, Integer> {

    // Busca conta financeira pelo ID do usuário (cada usuário tem 1 conta)
    @Query("SELECT c FROM ContaFinanceiraModelo c WHERE c.usuario.id = :userId")
    Optional<ContaFinanceiraModelo> findByUsuarioId(@Param("userId") Long userId);

    // Lista todas as contas ordenadas pelo ID
    @Query("SELECT c FROM ContaFinanceiraModelo c ORDER BY c.id ASC")
    List<ContaFinanceiraModelo> findAllOrderByIdAsc();

    // Verifica se o usuário já possui conta
    @Query("SELECT COUNT(c) > 0 FROM ContaFinanceiraModelo c WHERE c.usuario.id = :userId")
    boolean existsByUsuarioId(@Param("userId") Long userId);
}

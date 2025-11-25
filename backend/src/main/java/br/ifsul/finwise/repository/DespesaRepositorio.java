package br.ifsul.finwise.repository;

import br.ifsul.finwise.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface DespesaRepositorio extends JpaRepository<DespesaModelo, Integer> {

    List<DespesaModelo> findByContaId(Integer contaId);

    List<DespesaModelo> findByCategoriaId(Integer categoriaId);

    List<DespesaModelo> findByStatus(Boolean status);

    List<DespesaModelo> findByCartaoId(Integer cartaoId);

    List<DespesaModelo> findByDataInicial(Date dataInicial);

    List<DespesaModelo> findByDataFinal(Date dataFinal);

    List<DespesaModelo> findByValor(Double valor);

    List<DespesaModelo> findByRepeticao(RepeticaoEnum repeticao);

    List<DespesaModelo> findByObservacao(String observacao);

    List<DespesaModelo> findByCategoriaIdAndCartaoId(Integer categoriaId, Integer cartaoId);

    @Modifying
    @Transactional
    @Query("DELETE FROM DespesaModelo d WHERE d.id = :id")
    void deleteById(@Param("id") Integer id);
}

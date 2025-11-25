package br.ifsul.finwise.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.TransacaoModelo;
import br.ifsul.finwise.model.RepeticaoEnum;

@Repository
public interface TransacaoRepositorio extends JpaRepository<TransacaoModelo, Integer> {

    // Buscar transações por conta financeira
    List<TransacaoModelo> findByContaId(Integer contaId);

    // Buscar transações por categoria
    List<TransacaoModelo> findByCategoriaId(Integer categoriaId);

    // Buscar por repetição (UNICA, SEMANAL, MENSAL, ANUAL)
    List<TransacaoModelo> findByRepeticao(RepeticaoEnum repeticao);

    // Buscar transações em um intervalo de datas
    @Query("SELECT t FROM TransacaoModelo t WHERE t.dataInicial >= :inicio AND t.dataInicial <= :fim")
    List<TransacaoModelo> findByPeriodo(
        @Param("inicio") Date inicio,
        @Param("fim") Date fim
    );

    // Buscar transações ativas (dataFinal >= hoje OU dataFinal=null)
    @Query("""
        SELECT t FROM TransacaoModelo t
        WHERE t.dataFinal IS NULL OR t.dataFinal >= CURRENT_DATE
    """)
    List<TransacaoModelo> findAtivas();

    // Buscar transações contendo texto na descrição
    List<TransacaoModelo> findByDescricaoContainingIgnoreCase(String descricao);

    // Buscar transações por valor mínimo
    List<TransacaoModelo> findByValorGreaterThanEqual(Double valor);

    // Buscar transações por valor máximo
    List<TransacaoModelo> findByValorLessThanEqual(Double valor);

    // Buscar transações entre valores
    List<TransacaoModelo> findByValorBetween(Double min, Double max);

}

package br.ifsul.finwise.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.CartaoCreditoModelo;
import br.ifsul.finwise.model.CategoriaModelo;
import br.ifsul.finwise.model.ContaFinanceiraModelo;
import br.ifsul.finwise.model.DespesaModelo;
import br.ifsul.finwise.model.RepeticaoEnum;

@Repository
public interface DespesaRepositorio extends JpaRepository<DespesaModelo, Integer> {

    // Buscar despesas por conta
    List<DespesaModelo> findByContaId(Integer contaId);

    // Buscar despesas por categoria
    List<DespesaModelo> findByCategoriaId(Integer categoriaId);

    // Buscar despesas por status
    List<DespesaModelo> findByStatus(Boolean status);

    // Buscar despesas por cartão
    List<DespesaModelo> findByCartaoId(Integer cartaoId);

    // Buscar despesas por data inicial
    List<DespesaModelo> findByDataInicial(Date dataInicial);

    // Buscar despesas por data final
    List<DespesaModelo> findByDataFinal(Date dataFinal);

    // Buscar despesas por valor
    List<DespesaModelo> findByValor(Double valor);

    // Buscar despesas por repetição
    List<DespesaModelo> findByRepeticao(RepeticaoEnum repeticao);

    // Buscar despesas por observação
    List<DespesaModelo> findByObservacao(String observacao);

    // Buscar despesas por categoria e cartão
    List<DespesaModelo> findByCategoriaIdAndCartaoId(Integer categoriaId, Integer cartaoId);

    // Deletar por ID
    @Query("DELETE FROM DespesaModelo d WHERE d.id = :id")
    void deleteById(@Param("id") Integer id);

    // Atualizar despesa
    @Query("UPDATE DespesaModelo d SET d.valor = :valor, d.dataInicial = :dataInicial, d.dataFinal = :dataFinal, d.descricao = :descricao, d.observacao = :observacao, d.repeticao = :repeticao, d.categoria = :categoria, d.conta = :conta, d.status = :status, d.cartao = :cartao WHERE d.id = :id")
    void updateById(@Param("id") Integer id, @Param("valor") Double valor, @Param("dataInicial") Date dataInicial, @Param("dataFinal") Date dataFinal, @Param("descricao") String descricao, @Param("observacao") String observacao, @Param("repeticao") RepeticaoEnum repeticao, @Param("categoria") CategoriaModelo categoria, @Param("conta") ContaFinanceiraModelo conta, @Param("status") Boolean status, @Param("cartao") CartaoCreditoModelo cartao);

    
}
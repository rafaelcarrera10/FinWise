package br.ifsul.finwise.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.TransactionsModel;
import br.ifsul.finwise.model.TransactionsModel.TransactionType;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionsModel, Long> {

    // Busca transações por tipo
    @Query("SELECT t FROM TransactionsModel t WHERE t.type = :type")
    List<TransactionsModel> findByType(@Param("type") TransactionType type);

    // Busca transações com valor maior que o informado
    @Query("SELECT t FROM TransactionsModel t WHERE t.value > :minValue")
    List<TransactionsModel> findByValueGreaterThan(@Param("minValue") BigDecimal minValue);

    // Busca transações com valor menor que o informado
    @Query("SELECT t FROM TransactionsModel t WHERE t.value < :maxValue")
    List<TransactionsModel> findByValueLessThan(@Param("maxValue") BigDecimal maxValue);

    // Busca transações com valor entre dois valores
    @Query("SELECT t FROM TransactionsModel t WHERE t.value BETWEEN :minValue AND :maxValue")
    List<TransactionsModel> findByValueBetween(@Param("minValue") BigDecimal minValue, @Param("maxValue") BigDecimal maxValue);

    // Busca transações contendo parte da descrição
    @Query("SELECT t FROM TransactionsModel t WHERE LOWER(t.description) LIKE LOWER(CONCAT('%', :descriptionPart, '%'))")
    List<TransactionsModel> findByDescriptionContaining(@Param("descriptionPart") String descriptionPart);

    // Busca transações por tipo e valor maior que o informado
    @Query("SELECT t FROM TransactionsModel t WHERE t.type = :type AND t.value > :minValue")
    List<TransactionsModel> findByTypeAndValueGreaterThan(@Param("type") TransactionType type, @Param("minValue") BigDecimal minValue);

    // Busca transações por tipo e valor menor que o informado
    @Query("SELECT t FROM TransactionsModel t WHERE t.type = :type AND t.value < :maxValue")
    List<TransactionsModel> findByTypeAndValueLessThan(@Param("type") TransactionType type, @Param("maxValue") BigDecimal maxValue);

    // Conta todas as transações
    long count();

    // Conta transações por tipo
    long countByType(TransactionType type);

    // Lista transações por valor decrescente
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.value DESC")
    List<TransactionsModel> findAllOrderByValueDesc();

    // Lista transações por valor crescente
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.value ASC")
    List<TransactionsModel> findAllOrderByValueAsc();

    // Lista transações por tipo
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.type ASC")
    List<TransactionsModel> findAllOrderByType();

    // Lista transações por ID decrescente (mais recentes primeiro)
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.id DESC")
    List<TransactionsModel> findAllOrderByIdDesc();

    // Busca transações por lista de tipos
    @Query("SELECT t FROM TransactionsModel t WHERE t.type IN :types")
    List<TransactionsModel> findByTypes(@Param("types") List<TransactionType> types);

    // Atualiza o valor da transação
    @Query("UPDATE TransactionsModel t SET t.value = :newValue WHERE t.id = :id")
    int updateValueById(@Param("id") Long id, @Param("newValue") BigDecimal newValue);

    // Atualiza o tipo da transação
    @Query("UPDATE TransactionsModel t SET t.type = :newType WHERE t.id = :id")
    int updateTypeById(@Param("id") Long id, @Param("newType") TransactionType newType);

    // Atualiza a descrição da transação
    @Query("UPDATE TransactionsModel t SET t.description = :newDescription WHERE t.id = :id")
    int updateDescriptionById(@Param("id") Long id, @Param("newDescription") String newDescription);

    // Exclui transação por ID
    @Query("DELETE FROM TransactionsModel t WHERE t.id = :id")
    int deleteByIdCustom(@Param("id") Long id);

    // Soma o valor total de todas as transações
    @Query("SELECT SUM(t.value) FROM TransactionsModel t")
    BigDecimal getTotalValue();

    // Soma o valor total por tipo
    @Query("SELECT SUM(t.value) FROM TransactionsModel t WHERE t.type = :type")
    BigDecimal getTotalValueByType(@Param("type") TransactionType type);

    // Lista transações com paginação
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.id ASC")
    Page<TransactionsModel> findAllTransactions(Pageable pageable);

    // Lista transações agrupadas por tipo
    @Query("SELECT t FROM TransactionsModel t GROUP BY t.type ORDER BY t.type ASC")
    List<TransactionsModel> findTransactionsGroupedByType();

    // Lista as últimas transações (limitado por parâmetro)
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.id DESC")
    List<TransactionsModel> findLatestTransactions(@Param("limit") int limit);
}

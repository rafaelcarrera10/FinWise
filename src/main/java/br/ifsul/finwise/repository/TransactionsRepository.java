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
    
    // CRUD - Buscar    
    
    /**
     * Busca transações por tipo
     * @param type Tipo da transação
     * @return Lista de transações do tipo especificado
     */
    @Query("SELECT t FROM TransactionsModel t WHERE t.type = :type")
     List<TransactionsModel> findByType(TransactionType type);

    
    /**
     * Busca transações com valor maior que o especificado
     * @param minValue Valor mínimo
     * @return Lista de transações com valor maior
     */
    @Query("SELECT t FROM TransactionsModel t WHERE t.value > :minValue")
    List<TransactionsModel> findByValueGreaterThan(@Param("minValue") BigDecimal minValue);
    
    /**
     * Busca transações com valor menor que o especificado
     * @param maxValue Valor máximo
     * @return Lista de transações com valor menor
     */
    @Query("SELECT t FROM TransactionsModel t WHERE t.value < :maxValue")
    List<TransactionsModel> findByValueLessThan(@Param("maxValue") BigDecimal maxValue);
    
    /**
     * Busca transações com valor entre dois valores
     * @param minValue Valor mínimo
     * @param maxValue Valor máximo
     * @return Lista de transações com valor no intervalo
     */
    @Query("SELECT t FROM TransactionsModel t WHERE t.value BETWEEN :minValue AND :maxValue")
    List<TransactionsModel> findByValueBetween(@Param("minValue") BigDecimal minValue, @Param("maxValue") BigDecimal maxValue);
    
    /**
     * Busca transações por descrição contendo o texto (case insensitive)
     * @param descriptionPart Parte da descrição
     * @return Lista de transações que contêm o texto
     */
    @Query("SELECT t FROM TransactionsModel t WHERE t.description LIKE %:descriptionPart%")
    List<TransactionsModel> findByDescriptionContaining(@Param("descriptionPart") String descriptionPart);
    
    /**
     * Busca transações por tipo e valor maior que o especificado
     * @param type Tipo da transação
     * @param minValue Valor mínimo
     * @return Lista de transações do tipo com valor maior
     */
    @Query("SELECT t FROM TransactionsModel t WHERE t.type = :type AND t.value > :minValue")
    List<TransactionsModel> findByTypeAndValueGreaterThan(@Param("type") TransactionType type, @Param("minValue") BigDecimal minValue);
    
    /**
     * Busca transações por tipo e valor menor que o especificado
     * @param type Tipo da transação
     * @param maxValue Valor máximo
     * @return Lista de transações do tipo com valor menor
     */
    @Query("SELECT t FROM TransactionsModel t WHERE t.type = :type AND t.value < :maxValue")
    List<TransactionsModel> findByTypeAndValueLessThan(@Param("type") TransactionType type, @Param("maxValue") BigDecimal maxValue);
    
    /**
     * Conta quantas transações existem no sistema
     * @return Número total de transações
     */
    long count();
    
    /**
     * Conta transações por tipo
     * @param type Tipo da transação
     * @return Número de transações do tipo
     */
    long countByType(TransactionType type);
    
    /**
     * Busca transações ordenadas por valor (maior para menor)
     * @return Lista de transações ordenadas por valor decrescente
     */
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.value DESC")
    List<TransactionsModel> findAllOrderByValueDesc();
    
    /**
     * Busca transações ordenadas por valor (menor para maior)
     * @return Lista de transações ordenadas por valor crescente
     */
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.value ASC")
    List<TransactionsModel> findAllOrderByValueAsc();
    
    /**
     * Busca transações ordenadas por tipo
     * @return Lista de transações ordenadas por tipo
     */
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.type ASC")
    List<TransactionsModel> findAllOrderByType();
    
    /**
     * Busca transações ordenadas por ID (mais recentes primeiro)
     * @return Lista de transações ordenadas por ID decrescente
     */
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.id DESC")
    List<TransactionsModel> findAllOrderByIdDesc();
    
    /**
     * Busca transações por múltiplos tipos
     * @param types Lista de tipos de transação
     * @return Lista de transações encontradas
     */
    @Query("SELECT t FROM TransactionsModel t WHERE t.type IN :types")
    List<TransactionsModel> findByTypes(@Param("types") List<TransactionType> types);
    
    // CRUD - Atualizar
    
    /**
     * Atualiza o valor de uma transação por ID
     * @param id ID da transação
     * @param newValue Novo valor
     * @return Número de registros atualizados
     */
    @Query("UPDATE TransactionsModel t SET t.value = :newValue WHERE t.id = :id")
    int updateValueById(@Param("id") Long id, @Param("newValue") BigDecimal newValue);
    
    /**
     * Atualiza o tipo de uma transação por ID
     * @param id ID da transação
     * @param newType Novo tipo
     * @return Número de registros atualizados
     */
    @Query("UPDATE TransactionsModel t SET t.type = :newType WHERE t.id = :id")
    int updateTypeById(@Param("id") Long id, @Param("newType") TransactionType newType);
    
    /**
     * Atualiza a descrição de uma transação por ID
     * @param id ID da transação
     * @param newDescription Nova descrição
     * @return Número de registros atualizados
     */
    @Query("UPDATE TransactionsModel t SET t.description = :newDescription WHERE t.id = :id")
    int updateDescriptionById(@Param("id") Long id, @Param("newDescription") String newDescription);
    
    // CRUD - Deletar
    
    /**
     * Remover transação por ID
     * @param id id da transação
     * @return 
     */
    @Query("DELETE FROM TransactionsModel t WHERE t.id = :id")
    void deleteById(@Param("id") Long id);
    
    // Consultas de Relatórios
    
    /**
     * Calcula o valor total de todas as transações
     * @return Soma total dos valores
     */
    @Query("SELECT SUM(t.value) FROM TransactionsModel t")
    BigDecimal getTotalValue();
    
    /**
     * Calcula o valor total por tipo de transação
     * @param type Tipo da transação
     * @return Soma total dos valores do tipo
     */
    @Query("SELECT SUM(t.value) FROM TransactionsModel t WHERE t.type = :type")
    BigDecimal getTotalValueByType(@Param("type") TransactionType type);
    
    /**
     * Busca transações com paginação
     * @param id id da transação
     * @param pageable Configuração de paginação
     * @return Lista de transações com paginação
     */
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.id ASC")
    Page<TransactionsModel> findAllTransactions(Pageable pageable);

    /**
     * Busca transações agrupadas por tipo
     * @return Lista de transações agrupadas
     */
    @Query("SELECT t FROM TransactionsModel t GROUP BY t.type ORDER BY t.type ASC")
    List<TransactionsModel> findTransactionsGroupedByType();
    
    /**
     * Busca as últimas transações
     * @param limit Número de transações
     * @return Lista das últimas transações
     */
    @Query("SELECT t FROM TransactionsModel t ORDER BY t.id DESC")
    List<TransactionsModel> findLatestTransactions(@Param("limit") int limit);
}

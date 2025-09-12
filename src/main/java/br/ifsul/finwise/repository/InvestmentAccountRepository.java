package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.InvestmentAccountModel;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvestmentAccountRepository extends JpaRepository<InvestmentAccountModel, Long> {
    
    // CRUD - Buscar
    /**
     * Busca investimento por nome da ação
     * @param actionName Nome da ação
     * @return Lista de investimentos com a ação especificada
     */
    List<InvestmentAccountModel> findByActionName(String actionName);
    
    /**
     * Busca investimentos por nome da ação (case insensitive)
     * @param actionName Nome da ação
     * @return Lista de investimentos com a ação especificada
     */
    List<InvestmentAccountModel> findByActionNameIgnoreCase(String actionName);
    
    /**
     * Busca investimentos por nome da ação contendo o texto (case insensitive)
     * @param actionNamePart Parte do nome da ação
     * @return Lista de investimentos que contêm o texto
     */
    List<InvestmentAccountModel> findByActionNameContainingIgnoreCase(String actionNamePart);
    
    /**
     * Verifica se existe investimento com a ação especificada
     * @param actionName Nome da ação
     * @return true se existe, false caso contrário
     */
    boolean existsByActionName(String actionName);
    
    /**
     * Busca investimentos com valor maior que o especificado
     * @param minValue Valor mínimo
     * @return Lista de investimentos com valor maior
     */
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.value > :minValue")
    List<InvestmentAccountModel> findByValueGreaterThan(@Param("minValue") BigDecimal minValue);
    
    /**
     * Busca investimentos com valor menor que o especificado
     * @param maxValue Valor máximo
     * @return Lista de investimentos com valor menor
     */
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.value < :maxValue")
    List<InvestmentAccountModel> findByValueLessThan(@Param("maxValue") BigDecimal maxValue);
    
    /**
     * Busca investimentos com valor entre dois valores
     * @param minValue Valor mínimo
     * @param maxValue Valor máximo
     * @return Lista de investimentos com valor no intervalo
     */
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.value BETWEEN :minValue AND :maxValue")
    List<InvestmentAccountModel> findByValueBetween(@Param("minValue") BigDecimal minValue, @Param("maxValue") BigDecimal maxValue);
    
    /**
     * Busca investimentos com quantidade maior que a especificada
     * @param minQuantity Quantidade mínima
     * @return Lista de investimentos com quantidade maior
     */
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.quantity > :minQuantity")
    List<InvestmentAccountModel> findByQuantityGreaterThan(@Param("minQuantity") Integer minQuantity);
    
    /**
     * Busca investimentos com quantidade menor que a especificada
     * @param maxQuantity Quantidade máxima
     * @return Lista de investimentos com quantidade menor
     */
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.quantity < :maxQuantity")
    List<InvestmentAccountModel> findByQuantityLessThan(@Param("maxQuantity") Integer maxQuantity);
    
    /**
     * Busca investimentos com quantidade entre dois valores
     * @param minQuantity Quantidade mínima
     * @param maxQuantity Quantidade máxima
     * @return Lista de investimentos com quantidade no intervalo
     */
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.quantity BETWEEN :minQuantity AND :maxQuantity")
    List<InvestmentAccountModel> findByQuantityBetween(@Param("minQuantity") Integer minQuantity, @Param("maxQuantity") Integer maxQuantity);
    
    /**
     * Conta quantos investimentos existem no sistema
     * @return Número total de investimentos
     */
    long count();
    
    /**
     * Busca investimentos ordenados por nome da ação
     * @return Lista de investimentos ordenados por ação
     */
    @Query("SELECT i FROM InvestmentAccountModel i ORDER BY i.actionName ASC")
    List<InvestmentAccountModel> findAllOrderByActionNameAsc();
    
    /**
     * Busca investimentos ordenados por valor (maior para menor)
     * @return Lista de investimentos ordenados por valor decrescente
     */
    @Query("SELECT i FROM InvestmentAccountModel i ORDER BY i.value DESC")
    List<InvestmentAccountModel> findAllOrderByValueDesc();
    
    /**
     * Busca investimentos ordenados por quantidade (maior para menor)
     * @return Lista de investimentos ordenados por quantidade decrescente
     */
    @Query("SELECT i FROM InvestmentAccountModel i ORDER BY i.quantity DESC")
    List<InvestmentAccountModel> findAllOrderByQuantityDesc();
    
    /**
     * Busca investimentos por múltiplas ações
     * @param actionNames Lista de nomes de ações
     * @return Lista de investimentos encontrados
     */
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.actionName IN :actionNames")
    List<InvestmentAccountModel> findByActionNames(@Param("actionNames") List<String> actionNames);
    
    /**
     * Busca investimentos com quantidade zero
     * @return Lista de investimentos com quantidade zero
     */
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.quantity = 0")
    List<InvestmentAccountModel> findByZeroQuantity();
    
    /**
     * Busca investimentos com valor zero
     * @return Lista de investimentos com valor zero
     */
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.value = 0")
    List<InvestmentAccountModel> findByZeroValue();
    
    // CRUD - Atualizar
    
    /**
     * Atualiza o valor de um investimento por ID
     * @param id ID do investimento
     * @param newValue Novo valor
     * @return Número de registros atualizados
     */
    @Query("UPDATE InvestmentAccountModel i SET i.value = :newValue WHERE i.id = :id")
    int updateValueById(@Param("id") Long id, @Param("newValue") BigDecimal newValue);
    
    /**
     * Atualiza a quantidade de um investimento por ID
     * @param id ID do investimento
     * @param newQuantity Nova quantidade
     * @return Número de registros atualizados
     */
    @Query("UPDATE InvestmentAccountModel i SET i.quantity = :newQuantity WHERE i.id = :id")
    int updateQuantityById(@Param("id") Long id, @Param("newQuantity") Integer newQuantity);
    
    /**
     * Atualiza o nome da ação de um investimento por ID
     * @param id ID do investimento
     * @param newActionName Novo nome da ação
     * @return Número de registros atualizados
     */
    @Query("UPDATE InvestmentAccountModel i SET i.actionName = :newActionName WHERE i.id = :id")
    int updateActionNameById(@Param("id") Long id, @Param("newActionName") String newActionName);
    
    /**
     * Adiciona quantidade a um investimento
     * @param id ID do investimento
     * @param amount Quantidade a ser adicionada
     * @return Número de registros atualizados
     */
    @Query("UPDATE InvestmentAccountModel i SET i.quantity = i.quantity + :amount WHERE i.id = :id")
    int addToQuantity(@Param("id") Long id, @Param("amount") Integer amount);
    
    /**
     * Subtrai quantidade de um investimento
     * @param id ID do investimento
     * @param amount Quantidade a ser subtraída
     * @return Número de registros atualizados
     */
    @Query("UPDATE InvestmentAccountModel i SET i.quantity = i.quantity - :amount WHERE i.id = :id")
    int subtractFromQuantity(@Param("id") Long id, @Param("amount") Integer amount);
    
    // CRUD - Deletar
    
    /**
     * Remove investimentos por nome da ação
     * @param actionName Nome da ação
     * @return Número de registros removidos
     */
    @Query("DELETE FROM InvestmentAccountModel i WHERE i.actionName = :actionName")
    int deleteByActionName(@Param("actionName") String actionName);
    
    /**
     * Remove investimentos com quantidade zero
     * @return Número de registros removidos
     */
    @Query("DELETE FROM InvestmentAccountModel i WHERE i.quantity = 0")
    int deleteByZeroQuantity();
    
    /**
     * Remove investimentos com valor zero
     * @return Número de registros removidos
     */
    @Query("DELETE FROM InvestmentAccountModel i WHERE i.value = 0")
    int deleteByZeroValue();
    
    // Consultas de Relatórios
    
    /**
     * Calcula o valor total de todos os investimentos
     * @return Soma total dos valores
     */
    @Query("SELECT SUM(i.value) FROM InvestmentAccountModel i")
    BigDecimal getTotalValue();
    
    /**
     * Calcula o valor médio dos investimentos
     * @return Valor médio
     */
    @Query("SELECT AVG(i.value) FROM InvestmentAccountModel i")
    BigDecimal getAverageValue();
    
    /**
     * Calcula a quantidade total de ações
     * @return Soma total das quantidades
     */
    @Query("SELECT SUM(i.quantity) FROM InvestmentAccountModel i")
    Long getTotalQuantity();
    
    /**
     * Encontra o maior valor entre todos os investimentos
     * @return Maior valor
     */
    @Query("SELECT MAX(i.value) FROM InvestmentAccountModel i")
    BigDecimal getMaxValue();
    
    /**
     * Encontra o menor valor entre todos os investimentos
     * @return Menor valor
     */
    @Query("SELECT MIN(i.value) FROM InvestmentAccountModel i")
    BigDecimal getMinValue();
    
    /**
     * Encontra a maior quantidade entre todos os investimentos
     * @return Maior quantidade
     */
    @Query("SELECT MAX(i.quantity) FROM InvestmentAccountModel i")
    Integer getMaxQuantity();
    
    /**
     * Encontra a menor quantidade entre todos os investimentos
     * @return Menor quantidade
     */
    @Query("SELECT MIN(i.quantity) FROM InvestmentAccountModel i")
    Integer getMinQuantity();
    
    /**
     * Busca investimentos com paginação
     * @param offset Posição inicial
     * @param limit Número máximo de registros
     * @return Lista de investimentos com paginação
     */
    @Query("SELECT i FROM InvestmentAccountModel i ORDER BY i.id ASC")
    List<InvestmentAccountModel> findInvestmentsWithPagination(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * Busca investimentos agrupados por ação
     * @return Lista de investimentos agrupados
     */
    @Query("SELECT i FROM InvestmentAccountModel i GROUP BY i.actionName ORDER BY i.actionName ASC")
    List<InvestmentAccountModel> findInvestmentsGroupedByAction();
}

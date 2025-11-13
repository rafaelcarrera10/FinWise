package br.ifsul.finwise.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.InvestmentAccountModel;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InvestmentAccountRepository extends JpaRepository<InvestmentAccountModel, Long> {

    // Busca investimentos por nome da ação (ignora maiúsculas/minúsculas)
    List<InvestmentAccountModel> findByActionNameIgnoreCase(String actionName);

    // Verifica se existe investimento com o nome da ação informado
    boolean existsByActionName(String actionName);

    // Busca investimentos com valor maior que o especificado
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.value > :minValue")
    List<InvestmentAccountModel> findByValueGreaterThan(@Param("minValue") BigDecimal minValue);

    // Busca investimentos com valor menor que o especificado
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.value < :maxValue")
    List<InvestmentAccountModel> findByValueLessThan(@Param("maxValue") BigDecimal maxValue);

    // Busca investimentos dentro de um intervalo de valores
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.value BETWEEN :minValue AND :maxValue")
    List<InvestmentAccountModel> findByValueBetween(@Param("minValue") BigDecimal minValue, @Param("maxValue") BigDecimal maxValue);

    // Busca investimentos com quantidade maior que a informada
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.quantity > :minQuantity")
    List<InvestmentAccountModel> findByQuantityGreaterThan(@Param("minQuantity") Integer minQuantity);

    // Busca investimentos com quantidade menor que a informada
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.quantity < :maxQuantity")
    List<InvestmentAccountModel> findByQuantityLessThan(@Param("maxQuantity") Integer maxQuantity);

    // Busca investimentos dentro de um intervalo de quantidades
    @Query("SELECT i FROM InvestmentAccountModel i WHERE i.quantity BETWEEN :minQuantity AND :maxQuantity")
    List<InvestmentAccountModel> findByQuantityBetween(@Param("minQuantity") Integer minQuantity, @Param("maxQuantity") Integer maxQuantity);

    // Lista todos os investimentos em ordem alfabética de ação (A-Z)
    @Query("SELECT i FROM InvestmentAccountModel i ORDER BY i.actionName ASC")
    List<InvestmentAccountModel> findAllOrderByActionNameAsc();

    // Lista todos os investimentos por valor decrescente
    @Query("SELECT i FROM InvestmentAccountModel i ORDER BY i.value DESC")
    List<InvestmentAccountModel> findAllOrderByValueDesc();

    // Lista todos os investimentos por quantidade decrescente
    @Query("SELECT i FROM InvestmentAccountModel i ORDER BY i.quantity DESC")
    List<InvestmentAccountModel> findAllOrderByQuantityDesc();

    // Busca todos os investimentos com paginação
    @Query("SELECT i FROM InvestmentAccountModel i ORDER BY i.id ASC")
    Page<InvestmentAccountModel> findAllWithPagination(Pageable pageable);

    // Atualiza o valor de um investimento
    @Modifying
    @Query("UPDATE InvestmentAccountModel i SET i.value = :newValue WHERE i.id = :id")
    int updateValueById(@Param("id") Long id, @Param("newValue") BigDecimal newValue);

    // Atualiza a quantidade de um investimento
    @Modifying
    @Query("UPDATE InvestmentAccountModel i SET i.quantity = :newQuantity WHERE i.id = :id")
    int updateQuantityById(@Param("id") Long id, @Param("newQuantity") Integer newQuantity);

    // Atualiza o nome da ação de um investimento
    @Modifying
    @Query("UPDATE InvestmentAccountModel i SET i.actionName = :newActionName WHERE i.id = :id")
    int updateActionNameById(@Param("id") Long id, @Param("newActionName") String newActionName);

    // Adiciona quantidade ao investimento
    @Modifying
    @Query("UPDATE InvestmentAccountModel i SET i.quantity = i.quantity + :amount WHERE i.id = :id")
    int addToQuantity(@Param("id") Long id, @Param("amount") Integer amount);

    // Subtrai quantidade do investimento
    @Modifying
    @Query("UPDATE InvestmentAccountModel i SET i.quantity = i.quantity - :amount WHERE i.id = :id")
    int subtractFromQuantity(@Param("id") Long id, @Param("amount") Integer amount);

    // Exclui investimento pelo nome da ação
    @Modifying
    @Query("DELETE FROM InvestmentAccountModel i WHERE i.actionName = :actionName")
    int deleteByActionName(@Param("actionName") String actionName);

    // Calcula o valor total de investimentos de um usuário
    @Query("SELECT SUM(i.value * i.quantity) FROM InvestmentAccountModel i WHERE i.user.id = :userId")
    BigDecimal getTotalInvestmentValueByUserId(@Param("userId") Long userId);

    // Calcula o total de ações de um usuário
    @Query("SELECT SUM(i.quantity) FROM InvestmentAccountModel i WHERE i.user.id = :userId")
    Integer getTotalQuantityByUserId(@Param("userId") Long userId);

    // Retorna o maior valor de investimento de um usuário
    @Query("SELECT MAX(i.value) FROM InvestmentAccountModel i WHERE i.user.id = :userId")
    BigDecimal getMaxValueByUserId(@Param("userId") Long userId);

    // Retorna o menor valor de investimento de um usuário
    @Query("SELECT MIN(i.value) FROM InvestmentAccountModel i WHERE i.user.id = :userId")
    BigDecimal getMinValueByUserId(@Param("userId") Long userId);

    // Retorna a maior quantidade de ações de um usuário
    @Query("SELECT MAX(i.quantity) FROM InvestmentAccountModel i WHERE i.user.id = :userId")
    Integer getMaxQuantityByUserId(@Param("userId") Long userId);

    // Retorna a menor quantidade de ações de um usuário
    @Query("SELECT MIN(i.quantity) FROM InvestmentAccountModel i WHERE i.user.id = :userId")
    Integer getMinQuantityByUserId(@Param("userId") Long userId);

    // Agrupa investimentos por nome da ação
    @Query("SELECT i FROM InvestmentAccountModel i GROUP BY i.actionName ORDER BY i.actionName ASC")
    List<InvestmentAccountModel> findInvestmentsGroupedByAction();
}

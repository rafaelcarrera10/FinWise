package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.AccountModel;
import br.ifsul.finwise.model.UserModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {
    
    // CRUD - Buscar
    /**
     * Busca conta por número
     * @param number Número da conta
     * @return Optional contendo a conta se encontrada
     */
    Optional<AccountModel> findByNumber(String number);

    /**
     * Busca contas por usuário
     * @param userId ID do usuário
     * @return Lista de contas do usuário
     */
    @Query("SELECT u FROM UserModel u WHERE u.id = :userId")
    UserModel findUserById(@Param("userId") Long userId);

    
    /**
     * Verifica se existe uma conta com o número especificado
     * @param number Número a ser verificado
     * @return true se existe, false caso contrário
     */
    boolean existsByNumber(String number);
    
    /**
     * Busca contas com saldo maior que o valor especificado
     * @param minBalance Saldo mínimo
     * @return Lista de contas com saldo maior que o especificado
     */
    @Query("SELECT a FROM AccountModel a WHERE a.balance > :minBalance")
    List<AccountModel> findByBalanceGreaterThan(@Param("minBalance") BigDecimal minBalance);
    
    /**
     * Busca contas com saldo menor que o valor especificado
     * @param maxBalance Saldo máximo
     * @return Lista de contas com saldo menor que o especificado
     */
    @Query("SELECT a FROM AccountModel a WHERE a.balance < :maxBalance")
    List<AccountModel> findByBalanceLessThan(@Param("maxBalance") BigDecimal maxBalance);
    
    /**
     * Busca contas com saldo entre dois valores
     * @param minBalance Saldo mínimo
     * @param maxBalance Saldo máximo
     * @return Lista de contas com saldo no intervalo especificado
     */
    @Query("SELECT a FROM AccountModel a WHERE a.balance BETWEEN :minBalance AND :maxBalance")
    List<AccountModel> findByBalanceBetween(@Param("minBalance") BigDecimal minBalance, @Param("maxBalance") BigDecimal maxBalance);
    
    /**
     * Conta quantas contas existem no sistema
     * @return Número total de contas
     */
    long count();
    
    /**
     * Busca contas ordenadas por saldo (maior para menor)
     * @return Lista de contas ordenadas por saldo decrescente
     */
    @Query("SELECT a FROM AccountModel a ORDER BY a.balance DESC")
    List<AccountModel> findAllOrderByBalanceDesc();
    
    /**
     * Busca contas ordenadas por saldo (menor para maior)
     * @return Lista de contas ordenadas por saldo crescente
     */
    @Query("SELECT a FROM AccountModel a ORDER BY a.balance ASC")
    List<AccountModel> findAllOrderByBalanceAsc();
    
    /**
     * Busca contas com saldo zero
     * @return Lista de contas com saldo zero
     */
    @Query("SELECT a FROM AccountModel a WHERE a.balance = 0")
    List<AccountModel> findByZeroBalance();
    
    /**
     * Busca contas com saldo positivo
     * @return Lista de contas com saldo positivo
     */
    @Query("SELECT a FROM AccountModel a WHERE a.balance > 0")
    List<AccountModel> findByPositiveBalance();
    
    /**
     * Busca contas com saldo negativo
     * @return Lista de contas com saldo negativo
     */
    @Query("SELECT a FROM AccountModel a WHERE a.balance < 0")
    List<AccountModel> findByNegativeBalance();
    
    // CRUD - Atualizar
    
    /**
     * Atualiza o saldo de uma conta por ID
     * @param id ID da conta
     * @param newBalance Novo saldo
     * @return Número de registros atualizados
     */
    @Query("UPDATE AccountModel a SET a.balance = :newBalance WHERE a.id = :id")
    int updateBalanceById(@Param("id") Long id, @Param("newBalance") BigDecimal newBalance);
    
    /**
     * Atualiza o número de uma conta por ID
     * @param id ID da conta
     * @param newNumber Novo número
     * @return Número de registros atualizados
     */
    @Query("UPDATE AccountModel a SET a.number = :newNumber WHERE a.id = :id")
    int updateNumberById(@Param("id") Long id, @Param("newNumber") Integer newNumber);
    
    /**
     * Adiciona valor ao saldo de uma conta
     * @param id ID da conta
     * @param amount Valor a ser adicionado
     * @return Número de registros atualizados
     */
    @Query("UPDATE AccountModel a SET a.balance = a.balance + :amount WHERE a.id = :id")
    int addToBalance(@Param("id") Long id, @Param("amount") BigDecimal amount);
    
    /**
     * Subtrai valor do saldo de uma conta
     * @param id ID da conta
     * @param amount Valor a ser subtraído
     * @return Número de registros atualizados
     */
    @Query("UPDATE AccountModel a SET a.balance = a.balance - :amount WHERE a.id = :id")
    int subtractFromBalance(@Param("id") Long id, @Param("amount") BigDecimal amount);
    
    // CRUD - Deletar
    
    /**
     * Remove conta por número
     * @param number Número da conta a ser removida
     * @return Número de registros removidos
     */
    @Query("DELETE FROM AccountModel a WHERE a.number = :number")
    int deleteByNumber(@Param("number") Integer number);
    
    /**
     * Remove contas com saldo zero
     * @return Número de registros removidos
     */
    @Query("DELETE FROM AccountModel a WHERE a.balance = 0")
    int deleteByZeroBalance();
    
    /**
     * Remove contas com saldo negativo
     * @return Número de registros removidos
     */
    @Query("DELETE FROM AccountModel a WHERE a.balance < 0")
    int deleteByNegativeBalance();
    
    // Consultas de Relatórios
    
    /**
     * Calcula o saldo total de todas as contas de um usuário
     * @param Id ID do usuário
     * @return Saldo total
     */
    @Query("SELECT SUM(a.balance) FROM AccountModel a WHERE a.user.id = :Id")
    BigDecimal getTotalBalanceByUserId(@Param("Id") Long Id);
    
    /**
     * Encontra o maior saldo entre todas as contas de um usuário
     * @param Id ID do usuário
     * @return Maior saldo
     */
    @Query("SELECT MAX(a.balance) FROM AccountModel a WHERE a.user.id = :Id")
    BigDecimal getMaxBalanceByUserId(@Param("Id") Long Id);

    List<AccountModel> findByUserId(long userId);
    
}
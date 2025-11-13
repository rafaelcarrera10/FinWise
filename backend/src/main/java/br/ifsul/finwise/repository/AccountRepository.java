package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.ifsul.finwise.model.AccountModel;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    // Busca uma conta pelo número
    Optional<AccountModel> findByNumber(String number);

    // Busca todas as contas associadas a um usuário
    List<AccountModel> findByUserId(Long userId);

    // Verifica se já existe uma conta com o número informado
    boolean existsByNumber(String number);

    // Busca contas com saldo acima de um valor mínimo
    List<AccountModel> findByBalanceGreaterThan(BigDecimal minBalance);

    // Busca contas com saldo abaixo de um valor máximo
    List<AccountModel> findByBalanceLessThan(BigDecimal maxBalance);

    // Busca contas com saldo entre dois valores
    List<AccountModel> findByBalanceBetween(BigDecimal minBalance, BigDecimal maxBalance);

    // Busca contas com saldo exato
    List<AccountModel> findByBalance(BigDecimal balance);

    // Soma o saldo total de todas as contas de um usuário
    @Query("SELECT SUM(a.balance) FROM AccountModel a WHERE a.user.id = :userId")
    BigDecimal getTotalBalanceByUserId(@Param("userId") Long userId);

    // Retorna o maior saldo de conta de um usuário
    @Query("SELECT MAX(a.balance) FROM AccountModel a WHERE a.user.id = :userId")
    BigDecimal getMaxBalanceByUserId(@Param("userId") Long userId);

    // Retorna o menor saldo de conta de um usuário
    @Query("SELECT MIN(a.balance) FROM AccountModel a WHERE a.user.id = :userId")
    BigDecimal getMinBalanceByUserId(@Param("userId") Long userId);

    // Busca contas que possuem limite acima de um valor específico
    List<AccountModel> findByLimiteGreaterThan(Double limite);

    // Busca contas com limite abaixo de um valor específico
    List<AccountModel> findByLimiteLessThan(Double limite);

    // Busca contas por valor de reserva acima de um valor
    List<AccountModel> findByReservationGreaterThan(Double valor);

    // Busca contas por valor de lazer acima de um valor
    List<AccountModel> findByLeisureGreaterThan(Double valor);

    // Busca contas com saldo zerado
    List<AccountModel> findByBalanceEquals(BigDecimal zero);

    // Exclui uma conta pelo número
    @Query("DELETE FROM AccountModel a WHERE a.number = :number")
    int deleteByNumber(@Param("number") String number);

    // Exclui todas as contas com saldo igual a zero
    @Query("DELETE FROM AccountModel a WHERE a.balance = 0")
    int deleteByZeroBalance();

    // Exclui todas as contas com saldo negativo
    @Query("DELETE FROM AccountModel a WHERE a.balance < 0")
    int deleteByNegativeBalance();

    // Conta quantas contas um usuário possui
    @Query("SELECT COUNT(a) FROM AccountModel a WHERE a.user.id = :userId")
    long countAccountsByUserId(@Param("userId") Long userId);

    // Calcula a média dos saldos das contas de um usuário
    @Query("SELECT AVG(a.balance) FROM AccountModel a WHERE a.user.id = :userId")
    BigDecimal getAverageBalanceByUserId(@Param("userId") Long userId);

    // Retorna todas as contas ordenadas pelo saldo (decrescente)
    List<AccountModel> findAllByOrderByBalanceDesc();

    // Retorna todas as contas ordenadas pelo saldo (crescente)
    List<AccountModel> findAllByOrderByBalanceAsc();

    // Retorna todas as contas com limite positivo
    List<AccountModel> findByLimiteGreaterThanEqual(Double zero);

    // Retorna contas com lazer ou reserva acima de certo valor
    @Query("SELECT a FROM AccountModel a WHERE a.leisure > :valor OR a.reservation > :valor")
    List<AccountModel> findHighSpendingAccounts(@Param("valor") Double valor);

    // -------------------------------------------------------------
    // ATUALIZAÇÕES DE CAMPOS (saldo, limite, reserva, lazer)
    // -------------------------------------------------------------

    // Atualiza o saldo de uma conta
    @Modifying
    @Transactional
    @Query("UPDATE AccountModel a SET a.balance = :balance WHERE a.id = :id")
    int updateBalanceById(@Param("id") Long id, @Param("balance") BigDecimal balance);

    // Atualiza o limite de uma conta
    @Modifying
    @Transactional
    @Query("UPDATE AccountModel a SET a.limite = :limite WHERE a.id = :id")
    int updateLimiteById(@Param("id") Long id, @Param("limite") BigDecimal limite);

    // Atualiza o valor de reserva de uma conta
    @Modifying
    @Transactional
    @Query("UPDATE AccountModel a SET a.reservation = :reservation WHERE a.id = :id")
    int updateReservationById(@Param("id") Long id, @Param("reservation") BigDecimal reservation);

    // Atualiza o valor de lazer de uma conta
    @Modifying
    @Transactional
    @Query("UPDATE AccountModel a SET a.leisure = :leisure WHERE a.id = :id")
    int updateLeisureById(@Param("id") Long id, @Param("leisure") BigDecimal leisure);

    // Atualiza todos os campos financeiros de uma conta
    @Modifying
    @Transactional
    @Query("UPDATE AccountModel a SET a.balance = :balance, a.limite = :limite, a.reservation = :reservation, a.leisure = :leisure WHERE a.id = :id")
    int updateFinancialDataById(
        @Param("id") Long id,
        @Param("balance") BigDecimal balance,
        @Param("limite") BigDecimal limite,
        @Param("reservation") BigDecimal reservation,
        @Param("leisure") BigDecimal leisure
    );

}

package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.ifsul.finwise.model.AccountModel;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountModel, Long> {

    Optional<AccountModel> findByNumber(String number);

    List<AccountModel> findByUserId(Long userId);

    boolean existsByNumber(String number);

    List<AccountModel> findByBalanceGreaterThan(BigDecimal minBalance);
    List<AccountModel> findByBalanceLessThan(BigDecimal maxBalance);
    List<AccountModel> findByBalanceBetween(BigDecimal minBalance, BigDecimal maxBalance);
    List<AccountModel> findByBalance(BigDecimal balance);

    @Query("SELECT SUM(a.balance) FROM AccountModel a WHERE a.user.id = :userId")
    BigDecimal getTotalBalanceByUserId(@Param("userId") Long userId);

    @Query("SELECT MAX(a.balance) FROM AccountModel a WHERE a.user.id = :userId")
    BigDecimal getMaxBalanceByUserId(@Param("userId") Long userId);

    @Query("DELETE FROM AccountModel a WHERE a.number = :number")
    int deleteByNumber(@Param("number") String number);

    @Query("DELETE FROM AccountModel a WHERE a.balance = 0")
    int deleteByZeroBalance();

    @Query("DELETE FROM AccountModel a WHERE a.balance < 0")
    int deleteByNegativeBalance();
}

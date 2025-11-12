package br.ifsul.finwise.service;

import br.ifsul.finwise.model.AccountModel;
import br.ifsul.finwise.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountModel save(AccountModel account) {
        return accountRepository.save(account);
    }

    public List<AccountModel> findAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    public Optional<AccountModel> findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    public boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }

    public List<AccountModel> findByBalanceGreaterThan(BigDecimal minBalance) {
        return accountRepository.findByBalanceGreaterThan(minBalance);
    }

    public List<AccountModel> findByBalanceLessThan(BigDecimal maxBalance) {
        return accountRepository.findByBalanceLessThan(maxBalance);
    }

    public List<AccountModel> findByBalanceBetween(BigDecimal minBalance, BigDecimal maxBalance) {
        return accountRepository.findByBalanceBetween(minBalance, maxBalance);
    }

    public long count() {
        return accountRepository.count();
    }

    public int deleteByNumber(String number) {
        return accountRepository.deleteByNumber(number);
    }

    public int deleteByZeroBalance() {
        return accountRepository.deleteByZeroBalance();
    }

    public int deleteByNegativeBalance() {
        return accountRepository.deleteByNegativeBalance();
    }

    public BigDecimal getTotalBalanceByUserId(Long userId) {
        return accountRepository.getTotalBalanceByUserId(userId);
    }

    public BigDecimal getMaxBalanceByUserId(Long userId) {
        return accountRepository.getMaxBalanceByUserId(userId);
    }
}

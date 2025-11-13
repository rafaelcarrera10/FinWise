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

    // Salva ou atualiza uma conta
    public AccountModel save(AccountModel account) {
        return accountRepository.save(account);
    }

    // Busca todas as contas de um usuário
    public List<AccountModel> findAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    // Busca uma conta pelo número
    public Optional<AccountModel> findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    // Verifica se já existe uma conta com o número informado
    public boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }

    // Busca contas com saldo acima de um valor mínimo
    public List<AccountModel> findByBalanceGreaterThan(BigDecimal minBalance) {
        return accountRepository.findByBalanceGreaterThan(minBalance);
    }

    // Busca contas com saldo abaixo de um valor máximo
    public List<AccountModel> findByBalanceLessThan(BigDecimal maxBalance) {
        return accountRepository.findByBalanceLessThan(maxBalance);
    }

    // Busca contas com saldo entre dois valores
    public List<AccountModel> findByBalanceBetween(BigDecimal minBalance, BigDecimal maxBalance) {
        return accountRepository.findByBalanceBetween(minBalance, maxBalance);
    }

    // Busca contas com saldo exato
    public List<AccountModel> findByBalance(BigDecimal balance) {
        return accountRepository.findByBalance(balance);
    }

    // Retorna o saldo total das contas de um usuário
    public BigDecimal getTotalBalanceByUserId(Long userId) {
        return accountRepository.getTotalBalanceByUserId(userId);
    }

    // Retorna o maior saldo de conta de um usuário
    public BigDecimal getMaxBalanceByUserId(Long userId) {
        return accountRepository.getMaxBalanceByUserId(userId);
    }

    // Retorna o menor saldo de conta de um usuário
    public BigDecimal getMinBalanceByUserId(Long userId) {
        return accountRepository.getMinBalanceByUserId(userId);
    }

    // Retorna a média de saldos das contas de um usuário
    public BigDecimal getAverageBalanceByUserId(Long userId) {
        return accountRepository.getAverageBalanceByUserId(userId);
    }

    // Conta quantas contas um usuário possui
    public long countAccountsByUserId(Long userId) {
        return accountRepository.countAccountsByUserId(userId);
    }

    // Busca contas com limite acima de um valor
    public List<AccountModel> findByLimiteGreaterThan(Double limite) {
        return accountRepository.findByLimiteGreaterThan(limite);
    }

    // Busca contas com limite abaixo de um valor
    public List<AccountModel> findByLimiteLessThan(Double limite) {
        return accountRepository.findByLimiteLessThan(limite);
    }

    // Busca contas com limite positivo
    public List<AccountModel> findByLimiteGreaterThanEqual(Double zero) {
        return accountRepository.findByLimiteGreaterThanEqual(zero);
    }

    // Busca contas com valor de reserva acima do informado
    public List<AccountModel> findByReservationGreaterThan(Double valor) {
        return accountRepository.findByReservationGreaterThan(valor);
    }

    // Busca contas com valor de lazer acima do informado
    public List<AccountModel> findByLeisureGreaterThan(Double valor) {
        return accountRepository.findByLeisureGreaterThan(valor);
    }

    // Busca contas com saldo zerado
    public List<AccountModel> findByBalanceEquals(BigDecimal zero) {
        return accountRepository.findByBalanceEquals(zero);
    }

    // Retorna contas com lazer ou reserva altos
    public List<AccountModel> findHighSpendingAccounts(Double valor) {
        return accountRepository.findHighSpendingAccounts(valor);
    }

    // Retorna todas as contas ordenadas por saldo (decrescente)
    public List<AccountModel> findAllByOrderByBalanceDesc() {
        return accountRepository.findAllByOrderByBalanceDesc();
    }

    // Retorna todas as contas ordenadas por saldo (crescente)
    public List<AccountModel> findAllByOrderByBalanceAsc() {
        return accountRepository.findAllByOrderByBalanceAsc();
    }

    // Exclui uma conta pelo número
    public int deleteByNumber(String number) {
        return accountRepository.deleteByNumber(number);
    }

    // Exclui todas as contas com saldo zerado
    public int deleteByZeroBalance() {
        return accountRepository.deleteByZeroBalance();
    }

    // Exclui todas as contas com saldo negativo
    public int deleteByNegativeBalance() {
        return accountRepository.deleteByNegativeBalance();
    }

    // Conta total de registros de contas
    public long count() {
        return accountRepository.count();
    }

    // -------------------------------------------------------------
    // ATUALIZAÇÕES DE CAMPOS (saldo, limite, reserva, lazer)
    // -------------------------------------------------------------

    // Atualiza o saldo de uma conta
    public int updateBalanceById(Long id, BigDecimal balance) {
        return accountRepository.updateBalanceById(id, balance);
    }

    // Atualiza o limite de uma conta
    public int updateLimiteById(Long id, BigDecimal newLimite) {
        return accountRepository.updateLimiteById(id, newLimite);
    }

    // Atualiza o valor de reserva de uma conta
    public int updateReservationById(Long id, BigDecimal newReservation) {
        return accountRepository.updateReservationById(id, newReservation);
    }

    // Atualiza o valor de lazer de uma conta
    public int updateLeisureById(Long id, BigDecimal leisure) {
        return accountRepository.updateLeisureById(id, leisure);
    }

    // Atualiza todos os campos financeiros de uma conta
    public int updateFinancialDataById(Long id, BigDecimal balance, BigDecimal limite, BigDecimal reservation, BigDecimal leisure) {
        return accountRepository.updateFinancialDataById(id, balance, limite, reservation, leisure);
    }

}

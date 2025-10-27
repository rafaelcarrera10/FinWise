package br.ifsul.finwise.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ifsul.finwise.model.AccountModel;
import br.ifsul.finwise.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final EncryptionService encryptionService;


    @Autowired
    public AccountService(AccountRepository accountRepository, EncryptionService encryptionService) {
        this.accountRepository = accountRepository;
        this.encryptionService = encryptionService;
    }
    
    public String encryptUserPassword(String password) {
        return encryptionService.encrypt(password);
    }

    public String decryptUserPassword(String encrypted) {
        return encryptionService.decrypt(encrypted);
    }

    // Salvar Conta
    public AccountModel save(AccountModel account) {
        return accountRepository.save(account);
    }

    // Buscar

    // Buscar conta por número
    public Optional<AccountModel> findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    // Verificar se existe uma conta com o número especificado
    public boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }

    // Buscar contas com saldo maior que o valor especificado
    public List<AccountModel> findByBalanceGreaterThan(BigDecimal minBalance) {
        return accountRepository.findByBalanceGreaterThan(minBalance);
    }

    // Buscar contas com saldo menor que o valor especificado
    public List<AccountModel> findByBalanceLessThan(BigDecimal maxBalance) {
        return accountRepository.findByBalanceLessThan(maxBalance);
    }

    // Buscar contas com saldo entre dois valores
    public List<AccountModel> findByBalanceBetween(BigDecimal minBalance, BigDecimal maxBalance) {
        return accountRepository.findByBalanceBetween(minBalance, maxBalance);
    }

    // Contar quantas contas existem no sistema
    public long count() {
        return accountRepository.count();
    }

    // Buscar contas ordenadas por saldo (maior para menor)
    public List<AccountModel> findAllOrderByBalanceDesc() {
        return accountRepository.findAllOrderByBalanceDesc();
    }

    // Buscar contas ordenadas por saldo (menor para maior)
    public List<AccountModel> findAllOrderByBalanceAsc() {
        return accountRepository.findAllOrderByBalanceAsc();
    }

    // Buscar contas com saldo zero
    public List<AccountModel> findByZeroBalance() {
        return accountRepository.findByZeroBalance();
    }

    // Buscar contas com saldo positivo
    public List<AccountModel> findByPositiveBalance() {
        return accountRepository.findByPositiveBalance();
    }

    // Buscar contas com saldo negativo
    public List<AccountModel> findByNegativeBalance() {
        return accountRepository.findByNegativeBalance();
    }

    // Atualizar

    // Atualizar o saldo de uma conta por ID
    public int updateBalanceById(Long id, BigDecimal newBalance) {
        return accountRepository.updateBalanceById(id, newBalance);
    }

    // Atualizar o número de uma conta por ID
    public int updateNumberById(Long id, Integer newNumber) {
        return accountRepository.updateNumberById(id, newNumber);
    }

    // Adicionar valor ao saldo de uma conta
    public int addToBalance(Long id, BigDecimal amount) {
        return accountRepository.addToBalance(id, amount);
    }

    // Subtrair valor do saldo de uma conta
    public int subtractFromBalance(Long id, BigDecimal amount) {
        return accountRepository.subtractFromBalance(id, amount);
    }

    // Deletar

    // Remover conta por número
    public int deleteByNumber(Integer number) {
        return accountRepository.deleteByNumber(number);
    }

    // Remover contas com saldo zero
    public int deleteByZeroBalance() {
        return accountRepository.deleteByZeroBalance();
    }

    // Remover contas com saldo negativo
    public int deleteByNegativeBalance() {
        return accountRepository.deleteByNegativeBalance();
    }

    // Relatórios

    // Calcular o saldo total de todas as contas de um usuário
    public BigDecimal getTotalBalanceByUserId(Long userId) {
        return accountRepository.getTotalBalanceByUserId(userId);
    }

    // Encontrar o maior saldo entre todas as contas de um usuário
    public BigDecimal getMaxBalanceByUserId(Long userId) {
        return accountRepository.getMaxBalanceByUserId(userId);
    }
}
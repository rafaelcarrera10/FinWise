package br.ifsul.finwise.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifsul.finwise.model.AccountModel;
import br.ifsul.finwise.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    // Criar nova conta
    @PostMapping("/create")
    public AccountModel createAccount(@RequestBody AccountModel account) {
        return service.save(account);
    }
    
    // Buscar conta por número (com máscara)
    @GetMapping("/by-number")
    public ResponseEntity<AccountModel> getByNumber(@RequestBody AccountModel request) {
        return service.findByNumber(request.getSecureNumber())
                .map(account -> {
                    // Mascarar número antes de retornar
                    account.setNumber(maskAccountNumber(account.getSecureNumber()));
                    return ResponseEntity.ok(account);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar contas com saldo maior que o valor especificado
    @GetMapping("/balance-greater-than")
    public ResponseEntity<List<AccountModel>> getByBalanceGreaterThan(@RequestBody BigDecimal min) {
        List<AccountModel> accounts = service.findByBalanceGreaterThan(min);
        // Mascarar número antes de retornar
        accounts.forEach(acc -> acc.setNumber(maskAccountNumber(acc.getSecureNumber())));
        return ResponseEntity.ok(accounts);
    }

    // Buscar contas com saldo menor que o valor especificado
    @GetMapping("/balance-less-than")
    public ResponseEntity<List<AccountModel>> getByBalanceLessThan(@RequestBody BigDecimal max) {
        List<AccountModel> accounts = service.findByBalanceLessThan(max);
        // Mascarar número antes de retornar
        accounts.forEach(acc -> acc.setNumber(maskAccountNumber(acc.getSecureNumber())));
        return ResponseEntity.ok(accounts);
    }

    // Buscar contas com saldo entre dois valores
    @GetMapping("/balance-between")
    public ResponseEntity<List<AccountModel>> getByBalanceBetween(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        List<AccountModel> accounts = service.findByBalanceBetween(min, max);
        // Mascarar número antes de retornar
        accounts.forEach(acc -> acc.setNumber(maskAccountNumber(acc.getSecureNumber())));
        return ResponseEntity.ok(accounts);
    }

    // Contar quantas contas existem no sistema
    @GetMapping("")
    public long countAccounts() {
        return service.count();
    }

    // Buscar contas ordenadas por saldo (maior para menor)
    @GetMapping("/order-by-balance-desc")
    public ResponseEntity<List<AccountModel>> getAllOrderByBalanceDesc() {
        List<AccountModel> accounts = service.findAllOrderByBalanceDesc();
        // Mascarar número antes de retornar
        accounts.forEach(acc -> acc.setNumber(maskAccountNumber(acc.getSecureNumber())));
        return ResponseEntity.ok(accounts);
    }

    // Buscar contas ordenadas por saldo (menor para maior)
    @GetMapping("/order-by-balance-asc")
    public ResponseEntity<List<AccountModel>> getAllOrderByBalanceAsc() {
        List<AccountModel> accounts = service.findAllOrderByBalanceAsc();
        // Mascarar número antes de retornar
        accounts.forEach(acc -> acc.setNumber(maskAccountNumber(acc.getSecureNumber())));
        return ResponseEntity.ok(accounts);
    }

    // Buscar contas com saldo zero
    @GetMapping("/zero-balance")
    public ResponseEntity<List<AccountModel>> getByZeroBalance() {
        List<AccountModel> accounts = service.findByZeroBalance();
        // Mascarar número antes de retornar
        accounts.forEach(acc -> acc.setNumber(maskAccountNumber(acc.getSecureNumber())));
        return ResponseEntity.ok(accounts);
    }

    // Buscar contas com saldo positivo
    @GetMapping("/positive-balance")
    public ResponseEntity<List<AccountModel>> getByPositiveBalance() {
        List<AccountModel> accounts = service.findByPositiveBalance();
        // Mascarar número antes de retornar
        accounts.forEach(acc -> acc.setNumber(maskAccountNumber(acc.getSecureNumber())));
        return ResponseEntity.ok(accounts);
    }

    // Buscar contas com saldo negativo
    @GetMapping("/negative-balance")
    public ResponseEntity<List<AccountModel>> getByNegativeBalance() {
        List<AccountModel> accounts = service.findByNegativeBalance();
        // Mascarar número antes de retornar
        accounts.forEach(acc -> acc.setNumber(maskAccountNumber(acc.getSecureNumber())));
        return ResponseEntity.ok(accounts);
    }

    // Atualizar o saldo de uma conta por ID
    @PostMapping("/update-balance")
    public ResponseEntity<AccountModel> updateBalance(@RequestParam Long id, @RequestParam BigDecimal newBalance) {
        int updated = service.updateBalanceById(id, newBalance);
        if (updated > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }   
    }

    // Atualizar o número de uma conta por ID
    @PostMapping("/update-number")
    public ResponseEntity<AccountModel> updateNumber(@RequestParam Long id, @RequestParam Integer newNumber) {
        int updated = service.updateNumberById(id, newNumber);
        if (updated > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }   
    }

    // Adicionar valor ao saldo de uma conta
    @PostMapping("/add-to-balance")
    public ResponseEntity<AccountModel> addToBalance(@RequestParam Long id, @RequestParam BigDecimal amount) {
        int updated = service.addToBalance(id, amount);
        if (updated > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }   
    }

    // Subtrair valor do saldo de uma conta
    @PostMapping("/subtract-from-balance")
    public ResponseEntity<AccountModel> subtractFromBalance(@RequestParam Long id, @RequestParam BigDecimal amount) {
        int updated = service.subtractFromBalance(id, amount);
        if (updated > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }   
    }

    // Remover conta por número
    @PostMapping("/delete-by-number")
    public ResponseEntity<Void> deleteByNumber(@RequestParam Integer number) {
        int deleted = service.deleteByNumber(number);
        if (deleted > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }   
    }

    // Remover contas com saldo zero
    @PostMapping("/delete-by-zero-balance") 
    public ResponseEntity<Void> deleteByZeroBalance() {
        int deleted = service.deleteByZeroBalance();
        if (deleted > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }   
    }

    // Remover contas com saldo negativo
    @PostMapping("/delete-by-negative-balance")
    public ResponseEntity<Void> deleteByNegativeBalance() {
        int deleted = service.deleteByNegativeBalance();
        if (deleted > 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }   
    }

    // Calcular saldo total de todas as contas de um usuário
    @GetMapping("/total-balance")  
    public BigDecimal getTotalBalance(@RequestParam Long userId) {
        return service.getTotalBalanceByUserId(userId);
    }

    // Encontrar conta com maior saldo entre todas as contas de um usuário
    @GetMapping("/max-balance")
    public BigDecimal getMaxBalance(@RequestParam Long userId) {
        return service.getMaxBalanceByUserId(userId);
    }

    // Método auxiliar para mascarar o número da conta
    private String maskAccountNumber(String number) {
        if (number == null || number.length() <= 4) {
            return "****" + number; // se for muito curto
        }
        return "****" + number.substring(number.length() - 4); // mantém últimos 4 dígitos
    }
}
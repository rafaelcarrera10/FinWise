package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.AccountModel;
import br.ifsul.finwise.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:5173") // Libera requisições do front-end local
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // -------------------- CRIAÇÃO --------------------

    // Cria / salva uma conta
    @PostMapping("/create")
    public ResponseEntity<AccountModel> createAccount(@RequestBody AccountModel account) {
        if (account == null || account.getNumber() == null || account.getNumber().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (account.getBalance() == null) account.setBalance(BigDecimal.ZERO);
        if (account.getReservation() == null) account.setReservation(BigDecimal.ZERO);
        if (account.getLeisure() == null) account.setLeisure(BigDecimal.ZERO);
        if (account.getLimite() == null) account.setLimite(BigDecimal.ZERO);

        AccountModel saved = accountService.save(account);
        return ResponseEntity.ok(saved);
    }

    // -------------------- CONSULTAS --------------------

    // Busca contas por userId
    @GetMapping("/by-user")
    public ResponseEntity<List<AccountModel>> getAccountsByUserId(@RequestParam Long userId) {
        List<AccountModel> accounts = accountService.findAccountsByUserId(userId);
        if (accounts == null || accounts.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(accounts);
    }

    // Busca conta por número
    @GetMapping("/by-number")
    public ResponseEntity<AccountModel> getByNumber(@RequestParam String number) {
        Optional<AccountModel> account = accountService.findByNumber(number);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Retorna o saldo total das contas do usuário
    @GetMapping("/total-balance")
    public ResponseEntity<BigDecimal> getTotalBalance(@RequestParam Long userId) {
        BigDecimal total = accountService.getTotalBalanceByUserId(userId);
        return ResponseEntity.ok(total == null ? BigDecimal.ZERO : total);
    }

    // Retorna o maior saldo entre as contas do usuário
    @GetMapping("/max-balance")
    public ResponseEntity<BigDecimal> getMaxBalance(@RequestParam Long userId) {
        BigDecimal max = accountService.getMaxBalanceByUserId(userId);
        return ResponseEntity.ok(max == null ? BigDecimal.ZERO : max);
    }

    // Retorna o menor saldo entre as contas do usuário
    @GetMapping("/min-balance")
    public ResponseEntity<BigDecimal> getMinBalance(@RequestParam Long userId) {
        BigDecimal min = accountService.getMinBalanceByUserId(userId);
        return ResponseEntity.ok(min == null ? BigDecimal.ZERO : min);
    }

    // Retorna a média dos saldos das contas do usuário
    @GetMapping("/average-balance")
    public ResponseEntity<BigDecimal> getAverageBalance(@RequestParam Long userId) {
        BigDecimal avg = accountService.getAverageBalanceByUserId(userId);
        return ResponseEntity.ok(avg == null ? BigDecimal.ZERO : avg);
    }

    // Conta quantas contas o usuário possui
    @GetMapping("/count-by-user")
    public ResponseEntity<Long> countAccountsByUser(@RequestParam Long userId) {
        long count = accountService.countAccountsByUserId(userId);
        return ResponseEntity.ok(count);
    }

    // Conta total de contas (global)
    @GetMapping("/count")
    public ResponseEntity<Long> countAll() {
        return ResponseEntity.ok(accountService.count());
    }

    // -------------------- ATUALIZAÇÕES --------------------

    // Atualiza o saldo de uma conta pelo número
    @PutMapping("/update/balance")
    public ResponseEntity<Integer> updateBalance(@RequestParam Long id, @RequestParam BigDecimal newBalance) {
        int updated = accountService.updateBalanceById(id, newBalance);
        if (updated > 0) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    // Atualiza o limite de uma conta pelo id
    @PutMapping("/update/limite")
    public ResponseEntity<Integer> updateLimite(@RequestParam Long id, @RequestParam BigDecimal newLimite) {
        int updated = accountService.updateLimiteById(id, newLimite);
        if (updated > 0) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    // Atualiza o valor de reserva de uma conta pelo número
    @PutMapping("/update/reservation")
    public ResponseEntity<Integer> updateReservation(@RequestParam Long id, @RequestParam BigDecimal newReservation) {
        int updated = accountService.updateReservationById(id, newReservation);
        if (updated > 0) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    // Atualiza o valor de lazer de uma conta pelo número
    @PutMapping("/update/leisure")
    public ResponseEntity<Integer> updateLeisure(@RequestParam Long id, @RequestParam BigDecimal newLeisure) {
        int updated = accountService.updateLeisureById(id, newLeisure);
        if (updated > 0) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }

    // -------------------- EXCLUSÕES --------------------

    // Deleta conta por número
    @DeleteMapping("/delete/by-number")
    public ResponseEntity<Integer> deleteByNumber(@RequestParam String number) {
        int removed = accountService.deleteByNumber(number);
        if (removed > 0) return ResponseEntity.ok(removed);
        return ResponseEntity.notFound().build();
    }

    // Deleta contas com saldo zero
    @DeleteMapping("/delete/zero-balance")
    public ResponseEntity<Integer> deleteZeroBalance() {
        int removed = accountService.deleteByZeroBalance();
        return ResponseEntity.ok(removed);
    }

    // Deleta contas com saldo negativo
    @DeleteMapping("/delete/negative-balance")
    public ResponseEntity<Integer> deleteNegativeBalance() {
        int removed = accountService.deleteByNegativeBalance();
        return ResponseEntity.ok(removed);
    }
}

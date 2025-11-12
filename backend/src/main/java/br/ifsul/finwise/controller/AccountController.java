package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.AccountModel;
import br.ifsul.finwise.model.UserModel;
import br.ifsul.finwise.service.AccountService;
import br.ifsul.finwise.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping("/by-userid")
    public ResponseEntity<List<AccountModel>> getAccountsByUserId(@RequestParam Long userId) {
        List<AccountModel> accounts = accountService.findAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody AccountModel account) {
        Optional<UserModel> userOpt = userService.findById(account.getUser().getId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }
        account.setUser(userOpt.get());
        AccountModel created = accountService.save(account);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/by-number")
    public ResponseEntity<AccountModel> getByNumber(@RequestParam String number) {
        return accountService.findByNumber(number)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/total-balance")
    public BigDecimal getTotalBalance(@RequestParam Long userId) {
        return accountService.getTotalBalanceByUserId(userId);
    }

    @GetMapping("/max-balance")
    public BigDecimal getMaxBalance(@RequestParam Long userId) {
        return accountService.getMaxBalanceByUserId(userId);
    }
}

package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.ContaFinanceiraModelo;
import br.ifsul.finwise.model.UsuarioModelo;
import br.ifsul.finwise.service.ContaFinanceiraService;
import br.ifsul.finwise.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/conta")
@CrossOrigin(origins = "*")
public class ContaFinanceiraController {

    private final ContaFinanceiraService contaService;
    private final UsuarioService usuarioService;

    public ContaFinanceiraController(ContaFinanceiraService contaService, UsuarioService usuarioService) {
        this.contaService = contaService;
        this.usuarioService = usuarioService;
    }

    // Criar nova conta para um usuário
    @PostMapping("/usuario/{userId}")
    public ResponseEntity<?> criar(@PathVariable Integer userId, @RequestBody ContaFinanceiraModelo conta) {

        Optional<UsuarioModelo> usuarioOpt = usuarioService.buscarUsuarioPorId(userId);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        if (contaService.existeParaUsuario(userId)) {
            return ResponseEntity.badRequest().body("Este usuário já possui uma conta financeira.");
        }

        conta.setUsuario(usuarioOpt.get());
        ContaFinanceiraModelo salva = contaService.salvar(conta);
        return ResponseEntity.ok(salva);
    }

    // Buscar conta pelo usuário
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<?> buscarPorUsuario(@PathVariable Integer userId) {
        Optional<ContaFinanceiraModelo> contaOpt = contaService.buscarPorUsuario(userId);
        if (contaOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Conta não encontrada.");
        }
        return ResponseEntity.ok(contaOpt.get());
    }

    // Buscar conta pelo ID da conta
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<ContaFinanceiraModelo> contaOpt = contaService.buscarPorId(id);
        if (contaOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Conta financeira não encontrada.");
        }
        return ResponseEntity.ok(contaOpt.get());
    }

    // Atualizar conta
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Integer id, @RequestBody ContaFinanceiraModelo novaConta) {

        Optional<ContaFinanceiraModelo> contaOpt = contaService.buscarPorId(id);
        if (contaOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Conta não encontrada.");
        }

        ContaFinanceiraModelo conta = contaOpt.get();
        if (novaConta.getNome() != null) conta.setNome(novaConta.getNome());
        if (novaConta.getSaldoAtual() != null) conta.setSaldoAtual(novaConta.getSaldoAtual());
        if (novaConta.getSaldoPrevisto() != null) conta.setSaldoPrevisto(novaConta.getSaldoPrevisto());
        if (novaConta.getReceitaTotal() != null) conta.setReceitaTotal(novaConta.getReceitaTotal());
        if (novaConta.getDespesaTotal() != null) conta.setDespesaTotal(novaConta.getDespesaTotal());
        if (novaConta.getPlano() != null) conta.setPlano(novaConta.getPlano());
        if (novaConta.getCartao() != null) conta.setCartao(novaConta.getCartao());

        ContaFinanceiraModelo atualizada = contaService.salvar(conta);
        return ResponseEntity.ok(atualizada);
    }

    // Deletar conta
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {

        Optional<ContaFinanceiraModelo> contaOpt = contaService.buscarPorId(id);
        if (contaOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Conta não encontrada.");
        }

        contaService.deletarPorId(id);
        return ResponseEntity.ok("Conta deletada com sucesso!");
    }
}

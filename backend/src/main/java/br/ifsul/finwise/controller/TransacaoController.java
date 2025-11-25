package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.TransacaoModelo;
import br.ifsul.finwise.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    // Cria transação
    @PostMapping
    public TransacaoModelo criar(@RequestBody TransacaoModelo transacao) {
        return service.salvar(transacao);
    }

    // Busca transação por id
    @GetMapping("/{id}")
    public Optional<TransacaoModelo> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    // Lista transações de uma conta financeira
    @GetMapping("/conta/{contaId}")
    public List<TransacaoModelo> buscarPorConta(@PathVariable Integer contaId) {
        return service.listarPorConta(contaId);
    }

    // Atualiza transação
    @PutMapping("/{id}")
    public TransacaoModelo atualizar(@PathVariable Integer id, @RequestBody TransacaoModelo transacao) {
        return service.atualizarTransacao(id, transacao);
    }

    // Deleta transação
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}

package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.TransacaoModelo;
import br.ifsul.finwise.service.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    // Cria transação
    @PostMapping
    public TransacaoModelo criar(@RequestBody TransacaoModelo transacao) {
        return service.criar(transacao);
    }

    // Busca transação por id
    @GetMapping("/{id}")
    public TransacaoModelo buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    // Lista transações de uma conta financeira
    @GetMapping("/conta/{contaId}")
    public List<TransacaoModelo> buscarPorConta(@PathVariable Integer contaId) {
        return service.buscarPorConta(contaId);
    }

    // Atualiza transação
    @PutMapping("/{id}")
    public TransacaoModelo atualizar(@PathVariable Integer id, @RequestBody TransacaoModelo transacao) {
        return service.editar(id, transacao);
    }

    // Deleta transação
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}

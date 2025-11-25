package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.ContaFinanceiraModelo;
import br.ifsul.finwise.service.ContaFinanceiraService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
public class ContaFinanceiraController {

    @Autowired
    private ContaFinanceiraService service;

    // Cria conta financeira
    @PostMapping
    public ContaFinanceiraModelo criar(@RequestBody ContaFinanceiraModelo conta) {
        return service.criar(conta);
    }

    // Busca conta pelo id do usuário
    @GetMapping("/usuario/{userId}")
    public Optional<ContaFinanceiraModelo> buscarPorUsuario(@PathVariable Integer userId) {
        return service.buscarPorUsuario(userId);
    }

    // Atualiza conta
    @PutMapping("/{id}")
    public ContaFinanceiraModelo atualizar(@PathVariable Integer id, @RequestBody ContaFinanceiraModelo conta) {
        return service.editar(id, conta);
    }

    // Deleta conta
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}

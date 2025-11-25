package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.DespesaModelo;
import br.ifsul.finwise.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesa")
public class DespesaController {

    @Autowired
    private DespesaService service;

    // Cria despesa
    @PostMapping
    public DespesaModelo criar(@RequestBody DespesaModelo despesa) {
        return service.criar(despesa);
    }

    // Busca despesa por id
    @GetMapping("/{id}")
    public DespesaModelo buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    // Lista despesas de uma conta
    @GetMapping("/conta/{contaId}")
    public List<DespesaModelo> buscarPorConta(@PathVariable Integer contaId) {
        return service.buscarPorConta(contaId);
    }

    // Lista despesas por categoria
    @GetMapping("/categoria/{catId}")
    public List<DespesaModelo> buscarPorCategoria(@PathVariable Long catId) {
        return service.buscarPorCategoria(catId);
    }

    // Atualiza despesa
    @PutMapping("/{id}")
    public DespesaModelo atualizar(@PathVariable Integer id, @RequestBody DespesaModelo despesa) {
        return service.editar(id, despesa);
    }

    // Deleta despesa
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}

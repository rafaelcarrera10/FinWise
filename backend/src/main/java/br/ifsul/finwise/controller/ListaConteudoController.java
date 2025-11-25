package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.ListaConteudoModelo;
import br.ifsul.finwise.service.ListaConteudoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lista-conteudo")
public class ListaConteudoController {

    @Autowired
    private ListaConteudoService service;

    // Cria lista de conteúdo
    @PostMapping
    public ListaConteudoModelo criar(@RequestBody ListaConteudoModelo lista) {
        return service.criar(lista);
    }

    // Busca lista por id
    @GetMapping("/{id}")
    public ListaConteudoModelo buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Lista todo conteúdo
    @GetMapping
    public List<ListaConteudoModelo> listarTudo() {
        return service.listarTodas();
    }

    // Lista conteúdo criado por um professor
    @GetMapping("/professor/{idProf}")
    public List<ListaConteudoModelo> listarPorProfessor(@PathVariable Long idProf) {
        return service.listarPorProfessor(idProf);
    }

    // Atualiza lista
    @PutMapping("/{id}")
    public ListaConteudoModelo atualizar(@PathVariable Long id, @RequestBody ListaConteudoModelo lista) {
        return service.editar(id, lista);
    }

    // Deleta lista
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}

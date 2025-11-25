package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.CategoriaModelo;
import br.ifsul.finwise.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    // Cria categoria
    @PostMapping
    public CategoriaModelo criar(@RequestBody CategoriaModelo categoria) {
        return service.criar(categoria);
    }

    // Lista categorias de um usuário
    @GetMapping("/usuario/{userId}")
    public List<CategoriaModelo> categoriasPorUsuario(@PathVariable Long userId) {
        return service.buscarPorUsuario(userId);
    }

    // Busca categoria por id
    @GetMapping("/{id}")
    public CategoriaModelo buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Atualiza categoria
    @PutMapping("/{id}")
    public CategoriaModelo atualizar(@PathVariable Long id, @RequestBody CategoriaModelo categoria) {
        return service.editar(id, categoria);
    }

    // Deleta categoria
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}

package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.CategoriaModelo;
import br.ifsul.finwise.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    // Cria categoria
    @PostMapping("/")
    public CategoriaModelo criar(@RequestBody CategoriaModelo categoria) {
        return service.salvar(categoria);
    }

    // Lista categorias de um usuário
    @GetMapping("/usuario/{userId}")
    public List<CategoriaModelo> categoriasPorUsuario(@PathVariable Integer userId) {
        return service.listarPorUsuario(userId);
    }

    // Busca categoria por id
    @GetMapping("/{id}")
    public Optional<CategoriaModelo> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }

    // Deleta categoria
    @DeleteMapping("/{name}")
    public void deletar(@PathVariable String name) {
        service.deletarPorNome(name);
    }
}

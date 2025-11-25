package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.CategoriaModelo;
import br.ifsul.finwise.model.UsuarioModelo;
import br.ifsul.finwise.service.CategoriaService;
import br.ifsul.finwise.service.UsuarioService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    public CategoriaController(CategoriaService categoriaService, UsuarioService usuarioService) {
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
    }

    // Criar nova categoria
    @PostMapping("/user/{userId}")
    public ResponseEntity<?> criarCategoria(
            @PathVariable Integer userId,
            @RequestBody CategoriaModelo categoria) {

        UsuarioModelo usuario = usuarioService.buscarUsuarioPorId(userId)
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        // Verifica se o nome já existe para o usuário
        boolean nomeExiste = categoriaService
                .listarPorUsuario(userId)
                .stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(categoria.getName()));

        if (nomeExiste) {
            return ResponseEntity.badRequest().body("Já existe uma categoria com este nome.");
        }

        categoria.setUsuario(usuario);
        CategoriaModelo salva = categoriaService.salvar(categoria);

        return ResponseEntity.ok(salva);
    }

    // Buscar categoria por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        return categoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Listar categorias do usuário
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Integer userId) {

        if (!usuarioService.buscarUsuarioPorId(userId).isPresent()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        return ResponseEntity.ok(categoriaService.listarPorUsuario(userId));
    }

    // Listar ordenadas por nome
    @GetMapping("/user/{userId}/ordenado")
    public ResponseEntity<?> listarOrdenado(@PathVariable Integer userId) {

        if (!usuarioService.buscarUsuarioPorId(userId).isPresent()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }

        return ResponseEntity.ok(categoriaService.listarPorUsuarioOrdenado(userId));
    }

    // Editar categoria
    @PutMapping("/{id}")
    public ResponseEntity<?> editarCategoria(
            @PathVariable Integer id, 
            @RequestBody CategoriaModelo novaCategoria) {

        return categoriaService.buscarPorId(id).map(categoria -> {

            if (novaCategoria.getName() != null && !novaCategoria.getName().isBlank()) {
                categoria.setName(novaCategoria.getName());
            }

            if (novaCategoria.getOrcamento() != null) {
                categoria.setOrcamento(novaCategoria.getOrcamento());
            }

            CategoriaModelo atualizada = categoriaService.salvar(categoria);
            return ResponseEntity.ok(atualizada);

        }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar categoria
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        if (!categoriaService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        categoriaService.deletarPorId(id);
        return ResponseEntity.ok("Categoria deletada com sucesso!");
    }
}

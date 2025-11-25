package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.CartaoCreditoModelo;
import br.ifsul.finwise.service.CartaoCreditoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartao")
@CrossOrigin(origins = "http://localhost:5173")
public class CartaoCreditoController {

    @Autowired
    private CartaoCreditoService cartaoService;

    // Criar cartão
    @PostMapping("/create")
    public ResponseEntity<CartaoCreditoModelo> create(@RequestBody CartaoCreditoModelo cartao) {
        try {
            CartaoCreditoModelo saved = cartaoService.save(cartao);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Listar todos os cartões
    @GetMapping("/all")
    public ResponseEntity<List<CartaoCreditoModelo>> getAll() {
        return ResponseEntity.ok(cartaoService.findAll());
    }

    // Buscar cartão por ID
    @GetMapping("/by-id")
    public ResponseEntity<CartaoCreditoModelo> getById(@RequestParam Integer id) {
        return cartaoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar cartão
    @PutMapping("/update")
    public ResponseEntity<CartaoCreditoModelo> update(@RequestBody CartaoCreditoModelo cartao) {
        try {
            if (cartao.getId() == null) {
                return ResponseEntity.badRequest().build();
            }
            CartaoCreditoModelo updated = cartaoService.save(cartao);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Deletar cartão
    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam Integer id) {
        try {
            cartaoService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

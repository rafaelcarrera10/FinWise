package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.TransacaoModelo;
import br.ifsul.finwise.service.TransacaoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transacoes")
@CrossOrigin(origins = "*")
public class TransacaoController {

    private final TransacaoService service;

    public TransacaoController(TransacaoService service) {
        this.service = service;
    }

    // Cria transação
    @PostMapping("/create")
    public ResponseEntity<TransacaoModelo> criar(@RequestBody TransacaoModelo transacao) {
        try {
            TransacaoModelo t = service.salvar(transacao);
            return ResponseEntity.ok(t);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Busca transação por ID
    @GetMapping("/by-id/{id}")
    public ResponseEntity<TransacaoModelo> buscarPorId(@PathVariable Integer id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Lista transações de uma conta financeira
    @GetMapping("/conta/{contaId}")
    public ResponseEntity<List<TransacaoModelo>> buscarPorConta(@PathVariable Integer contaId) {
        List<TransacaoModelo> lista = service.listarPorConta(contaId);
        return lista.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(lista);
    }

    // Atualiza transação
    @PutMapping("/update/{id}")
    public ResponseEntity<TransacaoModelo> atualizar(@PathVariable Integer id, @RequestBody TransacaoModelo transacao) {
        try {
            TransacaoModelo t = service.atualizarTransacao(id, transacao);
            return ResponseEntity.ok(t);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Deleta transação
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.ok().build();
    }
}

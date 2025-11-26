package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.ContaInvestimentoModel;
import br.ifsul.finwise.repository.ContaInvestimentoRepositorio;
import br.ifsul.finwise.service.ContaInvestimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contas-investimento")
public class ContaInvestimentoController {

    @Autowired
    private ContaInvestimentoService service;

    // Criar nova conta
    @PostMapping
    public ResponseEntity<ContaInvestimentoModel> create(@RequestBody ContaInvestimentoModel conta) {
        ContaInvestimentoModel saved = service.save(conta);
        return ResponseEntity.ok(saved);
    }

    // Listar todas as contas
    @GetMapping
    public ResponseEntity<List<ContaInvestimentoModel>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // Buscar conta por ID
    @GetMapping("/{id}")
    public ResponseEntity<ContaInvestimentoModel> getById(@PathVariable Integer id) {
        Optional<ContaInvestimentoModel> conta = service.findById(id);
        return conta.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/vender/{idAcao}")
public ResponseEntity<?> vender(
        @PathVariable Integer idAcao,
        @RequestParam Integer quantidadeVenda
) {
    try {
        ContaInvestimentoModel resultado = service.venderAcao(idAcao, quantidadeVenda);
        return ResponseEntity.ok(resultado);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
    // Buscar contas por nome da ação
    @GetMapping("/acao/{nome}")
    public ResponseEntity<List<ContaInvestimentoRepositorio>> getByActionName(@PathVariable("nome") String nome) {
        return ResponseEntity.ok(service.findByActionName(nome));
    }

    // Buscar contas por usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ContaInvestimentoRepositorio>> getByUsuarioId(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
    }

    // Atualizar conta
    @PutMapping("/{id}")
    public ResponseEntity<ContaInvestimentoModel> update(@PathVariable Integer id,
                                                         @RequestBody ContaInvestimentoModel conta) {
        conta.setId(id);
        try {
            ContaInvestimentoModel updated = service.update(conta);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar conta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

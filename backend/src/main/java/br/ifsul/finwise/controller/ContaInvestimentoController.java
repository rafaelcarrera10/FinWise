package br.ifsul.finwise.controller;

import br.ifsul.finwise.model.InvestmentAccountModel;
import br.ifsul.finwise.service.ContaInvestimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/investments")
public class ContaInvestimentoController {

    private final ContaInvestimentoService service;

    @Autowired
    public ContaInvestimentoController(ContaInvestimentoService service) {
        this.service = service;
    }

    // -------------------- CRUD --------------------

    // Criar uma nova conta de investimento
    @PostMapping
    public ResponseEntity<InvestmentAccountModel> createInvestment(@RequestBody InvestmentAccountModel investment) {
        InvestmentAccountModel savedInvestment = service.save(investment);
        return ResponseEntity.ok(savedInvestment);
    }

    // Atualizar uma conta de investimento existente
    @PutMapping("/{id}")
    public ResponseEntity<InvestmentAccountModel> updateInvestment(
            @PathVariable Integer id,
            @RequestBody InvestmentAccountModel investment) {

        Optional<InvestmentAccountModel> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        investment.setId(id); // garante que o ID seja o mesmo
        InvestmentAccountModel updated = service.save(investment);
        return ResponseEntity.ok(updated);
    }

    // Buscar todas as contas de investimento
    @GetMapping
    public ResponseEntity<List<InvestmentAccountModel>> getAllInvestments() {
        List<InvestmentAccountModel> investments = service.findAll();
        return ResponseEntity.ok(investments);
    }

    // Buscar conta de investimento por ID
    @GetMapping("/{id}")
    public ResponseEntity<InvestmentAccountModel> getInvestmentById(@PathVariable Integer id) {
        Optional<InvestmentAccountModel> investment = service.findById(id);
        return investment.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar conta de investimento por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvestment(@PathVariable Integer id) {
        Optional<InvestmentAccountModel> existing = service.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // -------------------- MÉTODOS CUSTOMIZADOS --------------------

    // Buscar contas pelo nome da ação
    @GetMapping("/action/{actionName}")
    public ResponseEntity<List<InvestmentAccountModel>> getByActionName(@PathVariable String actionName) {
        List<InvestmentAccountModel> investments = service.findByActionName(actionName);
        return ResponseEntity.ok(investments);
    }

    // Buscar contas de um usuário específico
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<InvestmentAccountModel>> getByUserId(@PathVariable Integer userId) {
        List<InvestmentAccountModel> investments = service.findByUserId(userId);
        return ResponseEntity.ok(investments);
    }
}

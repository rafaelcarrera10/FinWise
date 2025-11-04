package br.ifsul.finwise.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifsul.finwise.model.TransactionsModel;
import br.ifsul.finwise.service.TransactionsService;
import br.ifsul.finwise.service.EncryptionService;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;
    // ÚNICO construtor com ambos os serviços
    public TransactionsController(TransactionsService transactionsService, EncryptionService encryptionService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/encrypt")
    public String encryptUserData(@RequestBody String data) {
        return EncryptionService.encrypt(data);
    }

    @PostMapping("/decrypt")
    public String decryptUserData(@RequestBody String data) {
        return EncryptionService.decrypt(data);
    }

    // Salvar transação
    @PostMapping("/create")
    public ResponseEntity<TransactionsModel> createTransaction(@RequestBody TransactionsModel transaction) {
        TransactionsModel savedTransaction = transactionsService.save(transaction);
        return ResponseEntity.status(201).body(savedTransaction);
    }

    // Buscar transação por ID
    @GetMapping("/by-id")
    public ResponseEntity<TransactionsModel> getById(@RequestParam Long id) {
        Optional<TransactionsModel> opt = transactionsService.findById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(opt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Buscar transações por tipo
    @GetMapping("/by-type")
    public ResponseEntity<List<TransactionsModel>> getByType(@RequestParam TransactionsModel.TransactionType type) {
        return ResponseEntity.ok(transactionsService.findByType(type));
    }

    // Buscar transações com valor maior que o especificado
    @GetMapping("/value-greater-than")
    public ResponseEntity<List<TransactionsModel>> getByValueGreaterThan(@RequestParam BigDecimal minValue) {
        return ResponseEntity.ok(transactionsService.findByValueGreaterThan(minValue));
    }

    // Buscar transações com valor menor que o especificado
    @GetMapping("/value-less-than")
    public ResponseEntity<List<TransactionsModel>> getByValueLessThan(@RequestParam BigDecimal maxValue) {
        return ResponseEntity.ok(transactionsService.findByValueLessThan(maxValue));
    }

    // Buscar transações com valor entre dois valores
    @GetMapping("/value-between")
    public ResponseEntity<List<TransactionsModel>> getByValueBetween(
            @RequestParam BigDecimal minValue,
            @RequestParam BigDecimal maxValue) {
        return ResponseEntity.ok(transactionsService.findByValueBetween(minValue, maxValue));
    }

    // Buscar transações por descrição
    @GetMapping("/by-description")
    public ResponseEntity<List<TransactionsModel>> getByDescription(@RequestParam String text) {
        return ResponseEntity.ok(transactionsService.findByDescriptionContaining(text));
    }

    // Buscar transações por tipo e valor maior que o especificado
    @GetMapping("/by-type-and-value-greater-than")
    public ResponseEntity<List<TransactionsModel>> getByTypeAndValueGreaterThan(
            @RequestParam TransactionsModel.TransactionType type,
            @RequestParam BigDecimal minValue) {
        return ResponseEntity.ok(transactionsService.findByTypeAndValueGreaterThan(type, minValue));
    }

    // Contar transações por tipo
    @GetMapping("/count-by-type")
    public ResponseEntity<Long> countByType(@RequestParam TransactionsModel.TransactionType type) {
        return ResponseEntity.ok(transactionsService.countByType(type));
    }

    // Buscar transações em ordem decrescente por valor
    @GetMapping("/order-by-value-desc")
    public ResponseEntity<List<TransactionsModel>> getAllOrderByValueDesc() {
        return ResponseEntity.ok(transactionsService.findAllOrderByValueDesc());
    }

    // Buscar transações em ordem crescente por valor
    @GetMapping("/order-by-value-asc")
    public ResponseEntity<List<TransactionsModel>> getAllOrderByValueAsc() {
        return ResponseEntity.ok(transactionsService.findAllOrderByValueAsc());
    }

    // Buscar transações ordenadas por tipo
    @GetMapping("/order-by-type")
    public ResponseEntity<List<TransactionsModel>> getAllOrderByType() {
        return ResponseEntity.ok(transactionsService.findAllOrderByType());
    }

    // Buscar transações ordenadas por ID
    @GetMapping("/order-by-id")
    public ResponseEntity<List<TransactionsModel>> getAllOrderById() {
        return ResponseEntity.ok(transactionsService.findAllOrderById());
    }

    // Buscar Transações por múltiplos tipos
    @GetMapping("/by-multiple-types")
    public ResponseEntity<List<TransactionsModel>> getByTypes(@RequestParam List<TransactionsModel.TransactionType> types) {
        return ResponseEntity.ok(transactionsService.findByTypes(types));
    }

    // Atualizar valor da transação por ID
    @PostMapping("/update-value")
    public ResponseEntity<TransactionsModel> updateTransactionValue(
            @RequestParam Long id,
            @RequestParam BigDecimal newValue,
            @RequestParam String newDescription) {
        int updatedRows = transactionsService.updateValueById(id, newValue, newDescription);
        if (updatedRows > 0) {
            Optional<TransactionsModel> opt = transactionsService.findById(id);
            return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar tipo da transação por ID
    @PostMapping("/update-type")
    public ResponseEntity<TransactionsModel> updateTransactionType(
            @RequestParam Long id,
            @RequestParam TransactionsModel.TransactionType newType) {
        int updatedRows = transactionsService.updateTypeById(id, newType);
        if (updatedRows > 0) {
            Optional<TransactionsModel> opt = transactionsService.findById(id);
            return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar a descrição da transação por ID
    @PostMapping("/update-description")
    public ResponseEntity<TransactionsModel> updateTransactionDescription(
            @RequestParam Long id,
            @RequestParam String newDescription) {
        int updatedRows = transactionsService.updateDescriptionById(id, newDescription);
        if (updatedRows > 0) {
            Optional<TransactionsModel> opt = transactionsService.findById(id);
            return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remover transação por ID
    @DeleteMapping("/delete-by-id")
    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        transactionsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Calcular soma de todas as transações
    @GetMapping("/sum-all")
    public ResponseEntity<BigDecimal> sumAllTransactions() {
        return ResponseEntity.ok(transactionsService.sumAllTransactions());
    }

    // Calcular soma de transações por tipo
    @GetMapping("/sum-by-type")
    public ResponseEntity<BigDecimal> sumTransactionsByType(@RequestParam TransactionsModel.TransactionType type) {
        return ResponseEntity.ok(transactionsService.sumTransactionsByType(type));
    }

    // Buscar Transações por página
    @GetMapping("/by-page")
    public ResponseEntity<List<TransactionsModel>> getAllTransactionsByPage(
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(transactionsService.findAllTrasactionsPage(page, size).getContent());
    }

    // Buscar Transações agrupadas por tipo
    @GetMapping("/grouped-by-type")
    public ResponseEntity<List<TransactionsModel>> getTransactionsGroupedByType() {
        return ResponseEntity.ok(transactionsService.findTransactionsGroupedByType());
    }

    // Buscar as últimas transações
    @GetMapping("/latest")
    public ResponseEntity<List<TransactionsModel>> getLatestTransactions(@RequestParam int limit) {
        return ResponseEntity.ok(transactionsService.findLatestTransactions(limit));
    }
}

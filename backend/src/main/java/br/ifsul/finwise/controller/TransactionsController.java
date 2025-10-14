package br.ifsul.finwise.controller;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifsul.finwise.model.TransactionsModel;
import br.ifsul.finwise.service.TransactionsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/transactions")
public class TransactionsController {
        
    @Autowired
    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    // Salvar transação
    @PostMapping("/create")
    public ResponseEntity<TransactionsModel> createTransaction(@RequestBody TransactionsModel transaction) {
        TransactionsModel savedTransaction = transactionsService.save(transaction);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    // Buscar transação por ID
    @PostMapping("/by-id")
    public ResponseEntity<TransactionsModel> getById(@RequestBody Long id) {
        return (ResponseEntity<TransactionsModel>) transactionsService.findById(id)
                .map(transaction -> ResponseEntity.ok(transaction))
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar transações por tipo
    @PostMapping("/by-type")
    public ResponseEntity<List<TransactionsModel>> getByType(@RequestBody TransactionsModel.TransactionType type) {
        List<TransactionsModel> transactions = transactionsService.findByType(type);
        return ResponseEntity.ok(transactions);
    }

    // Buscar transações com valor maior que o especificado
    @PostMapping("/value-greater-than")
    public ResponseEntity<List<TransactionsModel>> getByValueGreaterThan(@RequestBody BigDecimal minValue) {
        List<TransactionsModel> transactions = transactionsService.findByValueGreaterThan(minValue);
        return ResponseEntity.ok(transactions);
    }
    
    // Buscar transações com valor menor que o especificado
    @PostMapping("/value-less-than")
    public ResponseEntity<List<TransactionsModel>> getByValueLessThan(@RequestBody BigDecimal maxValue) {
        List<TransactionsModel> transactions = transactionsService.findByValueLessThan(maxValue);
        return ResponseEntity.ok(transactions);
    }

    // Buscar transações com valor entre dois valores
    @PostMapping("/value-between")
    public ResponseEntity<List<TransactionsModel>> getByValueBetween(@RequestBody BigDecimal minValue, @RequestBody BigDecimal maxValue) {
        List<TransactionsModel> transactions = transactionsService.findByValueBetween(minValue, maxValue);
        return ResponseEntity.ok(transactions);
    }

    // Buscar transações por descrição
    @PostMapping("/by-description")
    public ResponseEntity<List<TransactionsModel>> getByDescription(@RequestBody String text) {
        List<TransactionsModel> transactions = transactionsService.findByDescriptionContaining(text);
        return ResponseEntity.ok(transactions);
    }

    // Buscar transações por tipo e valor maior que o especificado
    @PostMapping("/by-type-and-value-greater-than")
    public ResponseEntity<List<TransactionsModel>> getByTypeAndValueGreaterThan(@RequestBody TransactionsModel.TransactionType type, @RequestBody BigDecimal minValue) {
        List<TransactionsModel> transactions = transactionsService.findByTypeAndValueGreaterThan(type, minValue);
        return ResponseEntity.ok(transactions);
    }

    // Contar transações por tipo
    @PostMapping("/count-by-type") 
    public ResponseEntity<Long> countByType(@RequestBody TransactionsModel.TransactionType type) {
        long count = transactionsService.countByType(type);
        return ResponseEntity.ok(count);
    }

    // Buscar transações em ordem decrescente por valor
    @PostMapping("/order-by-value-desc")
    public ResponseEntity<List<TransactionsModel>> getAllOrderByValueDesc() {
        List<TransactionsModel> transactions = transactionsService.findAllOrderByValueDesc();
        return ResponseEntity.ok(transactions);
    }

    // Buscar transações em ordem crescente por valor
    @PostMapping("/order-by-value-asc")
    public ResponseEntity<List<TransactionsModel>> getAllOrderByValueAsc() {
        List<TransactionsModel> transactions = transactionsService.findAllOrderByValueAsc();
        return ResponseEntity.ok(transactions);
    }

    // Buscar transações ordenadas por tipo
    @PostMapping("/order-by-type")
    public ResponseEntity<List<TransactionsModel>> getAllOrderByType() {
        List<TransactionsModel> transactions = transactionsService.findAllOrderByType();
        return ResponseEntity.ok(transactions);
    }

    // Buscar transações ordenadas por ID
    @PostMapping("/order-by-id")
    public ResponseEntity<List<TransactionsModel>> getAllOrderById() {
        List<TransactionsModel> transactions = transactionsService.findAllOrderById();
        return ResponseEntity.ok(transactions);
    }

    // Buscar Transações por multiplos tipos
    @PostMapping("/by-multiple-types")
    public ResponseEntity<List<TransactionsModel>> getByTypes(@RequestBody List<TransactionsModel.TransactionType> types) {
        List<TransactionsModel> transactions = transactionsService.findByTypes(types);
        return ResponseEntity.ok(transactions);
    }

    // Atualizar valor da transação por ID
    @PostMapping("/update-value")
    public ResponseEntity<TransactionsModel> updateTransactionValue(@RequestBody Long id, @RequestBody BigDecimal newValue, @RequestBody String newDescription) {
        int updatedRows = transactionsService.updateValueById(id, newValue, newDescription);
        if (updatedRows > 0) {
            return (ResponseEntity<TransactionsModel>) transactionsService.findById(id)
                    .map(updatedTransaction -> ResponseEntity.ok(updatedTransaction))
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar tipo da transação por ID
    @PostMapping("/update-type")
    public ResponseEntity<TransactionsModel> updateTransactionType(@RequestBody Long id, @RequestBody TransactionsModel.TransactionType newType) {
        int updatedRows = transactionsService.updateTypeById(id, newType);
        if (updatedRows > 0) {
            return (ResponseEntity<TransactionsModel>) transactionsService.findById(id)
                    .map(updatedTransaction -> ResponseEntity.ok(updatedTransaction))
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Atualizar a descrição da transação por ID
    @PostMapping("/update-description")
    public ResponseEntity<TransactionsModel> updateTransactionDescription(@RequestBody Long id, @RequestBody String newDescription) {
        int updatedRows = transactionsService.updateDescriptionById(id, newDescription);
        if (updatedRows > 0) {
            return (ResponseEntity<TransactionsModel>) transactionsService.findById(id)
                    .map(updatedTransaction -> ResponseEntity.ok(updatedTransaction))
                    .orElse(ResponseEntity.notFound().build());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Remover transação por ID
    @PostMapping("/delete-by-id")
    public ResponseEntity<Void> deleteById(@RequestBody Long id) {
        transactionsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Calcular soma de todas as transações
    @PostMapping("/sum-all")
    public ResponseEntity<BigDecimal> sumAllTransactions() {
        BigDecimal total = transactionsService.sumAllTransactions();
        return ResponseEntity.ok(total);
    }

    // Calcular soma de transações por tipo
    @PostMapping("/sum-by-type")
    public ResponseEntity<BigDecimal> sumTransactionsByType(@RequestBody TransactionsModel.TransactionType type) {
        BigDecimal total = transactionsService.sumTransactionsByType(type);
        return ResponseEntity.ok(total);
    }

    // Buscar Transações por página
    @PostMapping("/by-page")
    public ResponseEntity<List<TransactionsModel>> getAllTransactionsByPage(@RequestBody int page, @RequestBody int size) {
        List<TransactionsModel> transactions = transactionsService.findAllTrasactionsPage(page, size).getContent();
        return ResponseEntity.ok(transactions);
    }

    // Buscar Transações agrupadas por tipo
    @PostMapping("/grouped-by-type")
    public ResponseEntity<List<TransactionsModel>> getTransactionsGroupedByType() {
        List<TransactionsModel> transactions = transactionsService.findTransactionsGroupedByType();
        return ResponseEntity.ok(transactions);
    }

    // Buscar as últimas transações
    @PostMapping("/latest")
    public ResponseEntity<List<TransactionsModel>> getLatestTransactions(@RequestBody int limit) {
        List<TransactionsModel> transactions = transactionsService.findLatestTransactions(limit);
        return ResponseEntity.ok(transactions);
    }

}
    


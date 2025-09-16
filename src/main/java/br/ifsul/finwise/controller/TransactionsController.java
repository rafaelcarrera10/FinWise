package br.ifsul.finwise.controller;

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
    private TransactionsService transactionsService;

    @PostMapping
    public ResponseEntity<TransactionsModel> createTransaction(@RequestBody TransactionsModel transaction) {
        // Antes de salvar, o serviço deve lidar com a criptografia de campos sensíveis
        // e a validação de dados.
        TransactionsModel savedTransaction = transactionsService.save(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTransaction);
    }
    



}
    


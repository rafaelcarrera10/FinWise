package br.ifsul.finwise.service;

import br.ifsul.finwise.model.TransactionsModel;
import br.ifsul.finwise.repository.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    // Salvar Transação
    public TransactionsModel save(TransactionsModel transaction) {

        
        return transactionsRepository.save(transaction);
    }
    
}
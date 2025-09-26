package br.ifsul.finwise.service;

import br.ifsul.finwise.model.TransactionsModel;
import br.ifsul.finwise.repository.TransactionsRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    // Salvar Transação
    public TransactionsModel save(TransactionsModel transaction) {
        return transactionsRepository.save(transaction);
    }

    // Buscar

    // Buscar todas as Transações por ID
    public Optional findById(Long id) {
        return transactionsRepository.findById(id);
    }

    // Buscar Transações por Tipo
    public List<TransactionsModel> findByType(TransactionsModel.TransactionType type) {
        return transactionsRepository.findByType(type);
    }

    // Buscar Transações por Valor maior que um valor específico 
    public List<TransactionsModel> findByValueGreaterThan(java.math.BigDecimal minValue) {
        return transactionsRepository.findByValueGreaterThan(minValue);
    }

    // Buscar Transaçôes por valor menor que um valor específico
    public List<TransactionsModel> findByValueLessThan(java.math.BigDecimal maxValue) {
        return transactionsRepository.findByValueLessThan(maxValue);
    }

    // Buscar Transaçôes com valor entre dois valores
    public List<TransactionsModel> findByValueBetween(java.math.BigDecimal minValue, java.math.BigDecimal maxValue) {
        return transactionsRepository.findByValueBetween(minValue, maxValue);
    }

    // Buscar Transação por descrição
    public List<TransactionsModel> findByDescriptionContaining(String text) {
        return transactionsRepository.findByDescriptionContaining(text);
    }

    // Buscar Transações por tipo e valor maior que um valor específico
    public List<TransactionsModel> findByTypeAndValueGreaterThan(TransactionsModel.TransactionType type, java.math.BigDecimal minValue) {
        return transactionsRepository.findByTypeAndValueGreaterThan(type, minValue);
    }
    // Contar Transações por tipo
    public long countByType(TransactionsModel.TransactionType type) {
        return transactionsRepository.countByType(type);
    }

    // Buscar Transações em ordem decrescente por valor
    public List<TransactionsModel> findAllOrderByValueDesc() {
        return transactionsRepository.findAllOrderByValueDesc();
    }

    // Buscar Transações em ordem crescente por valor
    public List<TransactionsModel> findAllOrderByValueAsc() {
        return transactionsRepository.findAllOrderByValueAsc();
    }

    // Buscar Transações ordenadas por tipo
    public List<TransactionsModel> findAllOrderByType() {
        return transactionsRepository.findAllOrderByType();
    }

    // Buscar Transações ordenadas por ID
    public List<TransactionsModel> findAllOrderById() {
        return transactionsRepository.findAllOrderByIdDesc();
    }

    // Buscar Transações por multiplos tipos
    public List<TransactionsModel> findByTypes(List<TransactionsModel.TransactionType> types) {
        return transactionsRepository.findByTypes(types);
    }

    // Atualizar
    
    // Atualizar valor da Transação por ID
    public int updateValueById(Long id, java.math.BigDecimal newValue, String newDescription) {
        return transactionsRepository.updateValueById(id, newValue);
    }

    // Atualizar tipo da Transação por ID
    public int updateTypeById(Long id, TransactionsModel.TransactionType newType) {
        return transactionsRepository.updateTypeById(id, newType);
    }

    // Atualizar a descrição da Transação por ID
    public int updateDescriptionById(Long id, String newDescription) {   
        return transactionsRepository.updateDescriptionById(id, newDescription);
    }

    // Remover

    // Remover Transação por ID
    public void deleteById(Long id) {
        transactionsRepository.deleteById(id);
    }

    // Consultas de Relatórios

    // Calcular soma de todas as transações
    public java.math.BigDecimal sumAllTransactions() {
        return transactionsRepository.getTotalValue();
    }

    // Calcular soma de transações por tipo
    public java.math.BigDecimal sumTransactionsByType(TransactionsModel.TransactionType type) {
        return transactionsRepository.getTotalValueByType(type);
    }

    // Buscar Transações por página
    public Page<TransactionsModel> findAllTrasactionsPage (int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return transactionsRepository.findAllTransactions(pageable);
    }

    // Buscar Transações agrupadas por tipo
    public List<TransactionsModel> findTransactionsGroupedByType() {
        return transactionsRepository.findTransactionsGroupedByType();
    }

    // Buscar as últimas transações
    public List<TransactionsModel> findLatestTransactions(int limit) {
        return transactionsRepository.findLatestTransactions(limit);
    }

}
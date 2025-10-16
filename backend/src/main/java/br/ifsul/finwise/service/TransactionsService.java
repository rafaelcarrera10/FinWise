package br.ifsul.finwise.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.ifsul.finwise.model.TransactionsModel;
import br.ifsul.finwise.repository.TransactionsRepository;

@Service
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final EncryptionService encryptionService;

    // ÚNICO construtor que inicializa todos os campos final
    @Autowired
    public TransactionsService(TransactionsRepository transactionsRepository, EncryptionService encryptionService) {
        this.transactionsRepository = transactionsRepository;
        this.encryptionService = encryptionService;
    }

    // Salvar transação
    public TransactionsModel save(TransactionsModel transaction) {
        return transactionsRepository.save(transaction);
    }

    // Buscar por id
    public Optional<TransactionsModel> findById(Long id) {
        return transactionsRepository.findById(id);
    }

    // Buscar por tipo
    public List<TransactionsModel> findByType(TransactionsModel.TransactionType type) {
        return transactionsRepository.findAll()
                .stream()
                .filter(t -> t.getType() == type)
                .collect(Collectors.toList());
    }

    // Valor maior que
    public List<TransactionsModel> findByValueGreaterThan(BigDecimal minValue) {
        return transactionsRepository.findAll()
                .stream()
                .filter(t -> t.getSecureValue() != null && t.getSecureValue().compareTo(minValue) > 0)
                .collect(Collectors.toList());
    }

    // Valor menor que
    public List<TransactionsModel> findByValueLessThan(BigDecimal maxValue) {
        return transactionsRepository.findAll()
                .stream()
                .filter(t -> t.getSecureValue() != null && t.getSecureValue().compareTo(maxValue) < 0)
                .collect(Collectors.toList());
    }

    // Valor entre
    public List<TransactionsModel> findByValueBetween(BigDecimal minValue, BigDecimal maxValue) {
        return transactionsRepository.findAll()
                .stream()
                .filter(t -> {
                    BigDecimal v = t.getSecureValue();
                    return v != null && v.compareTo(minValue) >= 0 && v.compareTo(maxValue) <= 0;
                })
                .collect(Collectors.toList());
    }

    // Descrição contendo
    public List<TransactionsModel> findByDescriptionContaining(String text) {
        if (text == null) return List.of();
        String lower = text.toLowerCase();
        return transactionsRepository.findAll()
                .stream()
                .filter(t -> t.getDescription() != null && t.getDescription().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    // Tipo e valor maior
    public List<TransactionsModel> findByTypeAndValueGreaterThan(TransactionsModel.TransactionType type, BigDecimal minValue) {
        return transactionsRepository.findAll()
                .stream()
                .filter(t -> t.getType() == type && t.getSecureValue() != null && t.getSecureValue().compareTo(minValue) > 0)
                .collect(Collectors.toList());
    }

    // Conta por tipo
    public long countByType(TransactionsModel.TransactionType type) {
        return transactionsRepository.findAll()
                .stream()
                .filter(t -> t.getType() == type)
                .count();
    }

    // Ordenações
    public List<TransactionsModel> findAllOrderByValueDesc() {
        return transactionsRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(TransactionsModel::getSecureValue, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .collect(Collectors.toList());
    }

    public List<TransactionsModel> findAllOrderByValueAsc() {
        return transactionsRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(TransactionsModel::getSecureValue, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    public List<TransactionsModel> findAllOrderByType() {
        return transactionsRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(TransactionsModel::getType, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    public List<TransactionsModel> findAllOrderById() {
        return transactionsRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(TransactionsModel::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    // Buscar por múltiplos tipos
    public List<TransactionsModel> findByTypes(List<TransactionsModel.TransactionType> types) {
        if (types == null || types.isEmpty()) return List.of();
        return transactionsRepository.findAll()
                .stream()
                .filter(t -> t.getType() != null && types.contains(t.getType()))
                .collect(Collectors.toList());
    }

    // Atualizar valor/descrição/tipo por id (retorna 1 se atualizado, 0 caso contrário)
    public int updateValueById(Long id, BigDecimal newValue, String newDescription) {
        Optional<TransactionsModel> opt = transactionsRepository.findById(id);
        if (opt.isPresent()) {
            TransactionsModel t = opt.get();
            t.setValue(newValue);
            if (newDescription != null) t.setDescription(newDescription);
            transactionsRepository.save(t);
            return 1;
        }
        return 0;
    }

    public int updateTypeById(Long id, TransactionsModel.TransactionType newType) {
        Optional<TransactionsModel> opt = transactionsRepository.findById(id);
        if (opt.isPresent()) {
            TransactionsModel t = opt.get();
            t.setType(newType);
            transactionsRepository.save(t);
            return 1;
        }
        return 0;
    }

    public int updateDescriptionById(Long id, String newDescription) {
        Optional<TransactionsModel> opt = transactionsRepository.findById(id);
        if (opt.isPresent()) {
            TransactionsModel t = opt.get();
            t.setDescription(newDescription);
            transactionsRepository.save(t);
            return 1;
        }
        return 0;
    }

    // Deletar por id
    public void deleteById(Long id) {
        transactionsRepository.deleteById(id);
    }

    // Somatórios
    public BigDecimal sumAllTransactions() {
        return transactionsRepository.findAll()
                .stream()
                .map(TransactionsModel::getSecureValue)
                .filter(v -> v != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal sumTransactionsByType(TransactionsModel.TransactionType type) {
        return transactionsRepository.findAll()
                .stream()
                .filter(t -> t.getType() == type)
                .map(TransactionsModel::getSecureValue)
                .filter(v -> v != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Paginação (método com o nome exato que o controller chama)
    public Page<TransactionsModel> findAllTrasactionsPage(int page, int size) {
        Pageable p = PageRequest.of(page, size);
        List<TransactionsModel> all = transactionsRepository.findAll();
        int start = Math.min((int)p.getOffset(), all.size());
        int end = Math.min(start + p.getPageSize(), all.size());
        List<TransactionsModel> sub = all.subList(start, end);
        return new PageImpl<>(sub, p, all.size());
    }

    // Agrupamento (retorna lista — implementação simples que devolve todos; ajuste se precisar de agregação específica)
    public List<TransactionsModel> findTransactionsGroupedByType() {
        // Se quiser agregações reais, implementa query no repository. Aqui devolvemos todos (o controller só exibe)
        return transactionsRepository.findAll();
    }

    // Últimas transações (por id decrescente)
    public List<TransactionsModel> findLatestTransactions(int limit) {
        return transactionsRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(TransactionsModel::getId, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(limit)
                .collect(Collectors.toList());
    }
}

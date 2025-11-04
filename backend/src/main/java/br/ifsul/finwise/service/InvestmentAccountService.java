package br.ifsul.finwise.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import br.ifsul.finwise.model.InvestmentAccountModel;
import br.ifsul.finwise.repository.InvestmentAccountRepository;



@Service
public class InvestmentAccountService {
    
    private final InvestmentAccountRepository investmentRepository;
    public InvestmentAccountService(InvestmentAccountRepository investmentRepository,
                                    EncryptionService encryptionService) {
        this.investmentRepository = investmentRepository;
    }

    public String encryptUserPassword(String password) {
        return EncryptionService.encrypt(password);
    }

    public String decryptUserPassword(String encrypted) {
        return EncryptionService.decrypt(encrypted);
    }

    // Buscar

    // Buscar investimento por ID
    public Optional<InvestmentAccountModel> findById(Long id) {
        return investmentRepository.findById(id);
    }

    // Buscar investimentos por nome da ação (case insensitive)
    public List<InvestmentAccountModel> findByActionNameIgnoreCase(String actionName) {
        return investmentRepository.findByActionNameIgnoreCase(actionName);
    }

    // Verifica se existe investimento com a ação especificada
    public boolean existsByActionName(String actionName) {
        return investmentRepository.existsByActionName(actionName);
    }

    // Buscar investimentos com valor maior que o especificado
    public List<InvestmentAccountModel> findByValueGreaterThan(BigDecimal minValue) {
        return investmentRepository.findByValueGreaterThan(minValue);
    }

    // Buscar investimentos com valor menor que o especificado
    public List<InvestmentAccountModel> findByValueLessThan(BigDecimal maxValue) {
        return investmentRepository.findByValueLessThan(maxValue);
    }

    // Buscar investimentos com valor entre dois valores
    public List<InvestmentAccountModel> findByValueBetween(BigDecimal minValue, BigDecimal maxValue) {
        return investmentRepository.findByValueBetween(minValue, maxValue);
    }

    // Buscar investimentos com quantidade maior que a especificada
    public List<InvestmentAccountModel> findByQuantityGreaterThan(Integer minQuantity) {
        return investmentRepository.findByQuantityGreaterThan(minQuantity);
    }

    // Buscar investimentos com quantidade menor que a especificada
    public List<InvestmentAccountModel> findByQuantityLessThan(Integer maxQuantity) {
        return investmentRepository.findByQuantityLessThan(maxQuantity);
    }

    // Buscar investimentos com quantidade entre dois valores
    public List<InvestmentAccountModel> findByQuantityBetween(Integer minQuantity, Integer maxQuantity) {
        return investmentRepository.findByQuantityBetween(minQuantity, maxQuantity);
    }

    // Contar total de investimentos
    public long count() {
        return investmentRepository.count();
    }

    // Buscar todos os investimentos ordenados por nome da ação
    public List<InvestmentAccountModel> findAllOrderByActionNameAsc() {
        return investmentRepository.findAllOrderByActionNameAsc();
    }

    // Buscar todos os investimentos ordenados por valor (decrescente)
    public List<InvestmentAccountModel> findAllOrderByValueDesc() {
        return investmentRepository.findAllOrderByValueDesc();
    }

    // Buscar todos os investimentos ordenados por quantidade (decrescente)
    public List<InvestmentAccountModel> findAllOrderByQuantityDesc() {
        return investmentRepository.findAllOrderByQuantityDesc();
    }

    // Buscar investimentos com paginação
    public Page<InvestmentAccountModel> findAllInvestmentsPage(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return investmentRepository.findAllInvestments(pageable);
    }
    
    // Buscar investimentos agrupados por ação
    public List<InvestmentAccountModel> findInvestmentsGroupedByAction() {
        return investmentRepository.findInvestmentsGroupedByAction();
    }

    // Atualizar

    // Atualiza o valor de um investimento por ID
    public int updateValueById(Long id, BigDecimal newValue) {
        return investmentRepository.updateValueById(id, newValue);
    }

    // Atualiza a quantidade de um investimento por ID
    public int updateQuantityById(Long id, Integer newQuantity) {
        return investmentRepository.updateQuantityById(id, newQuantity);
    }

    // Atualiza o nome da ação de um investimento por ID
    public int updateActionNameById(Long id, String newActionName) {
        return investmentRepository.updateActionNameById(id, newActionName);
    }

    // Adiciona quantidade a um investimento
    public int addToQuantity(Long id, Integer amount) {
        return investmentRepository.addToQuantity(id, amount);
    }

    // Subtrai quantidade de um investimento
    public int subtractFromQuantity(Long id, Integer amount) {
        return investmentRepository.subtractFromQuantity(id, amount);
    }

    // Deletar

    // Remove investimentos por nome da ação
    public int deleteByActionName(String actionName) {
        return investmentRepository.deleteByActionName(actionName);
    }

    // Relatórios / Estatísticas

    // Calcula o valor total de todos os investimentos por usuário
    public BigDecimal getTotalInvestmentValueByUserId(Long userId) {
        return investmentRepository.getTotalInvestmentValueByUserId(userId);
    }

    // Calcula a quantidade total de ações por usuário
    public Integer getTotalQuantityByUserId(Long userId) {
        return investmentRepository.getTotalQuantityByUserId(userId);
    }

    // Encontra o maior valor entre todos os investimentos por usuário
    public BigDecimal getMaxValueByUserId(Long userId) {
        return investmentRepository.getMaxValueByUserId(userId);
    }

    // Encontra o menor valor entre todos os investimentos por usuário
    public BigDecimal getMinValueByUserId(Long userId) {
        return investmentRepository.getMinValueByUserId(userId);
    }

    // Encontra a maior quantidade entre todos os investimentos por usuário
    public Integer getMaxQuantityByUserId(Long userId) {
        return investmentRepository.getMaxQuantityByUserId(userId);
    }

    // Encontra a menor quantidade entre todos os investimentos por usuário
    public Integer getMinQuantityByUserId(Long userId) {
        return investmentRepository.getMinQuantityByUserId(userId);
    }
}
package br.ifsul.finwise.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import br.ifsul.finwise.model.InvestmentAccountModel;
import br.ifsul.finwise.service.InvestmentAccountService;
import br.ifsul.finwise.service.EncryptionService;

@RestController
@RequestMapping("/investment-accounts")
public class InvestmentAccountController {

    private final InvestmentAccountService service;
    // ÚNICO construtor com ambos os serviços
    public InvestmentAccountController(InvestmentAccountService service, EncryptionService encryptionService) {
        this.service = service;
    }

    @PostMapping("/encrypt")
    public String encryptUserData(@RequestBody String data) {
        return EncryptionService.encrypt(data); // chave usada automaticamente
    }

    @PostMapping("/decrypt")
    public String decryptUserData(@RequestBody String data) {
        return EncryptionService.decrypt(data); // chave usada automaticamente
    }

    // Buscar investimentos por nome da ação (case insensitive)
    @GetMapping("/by-action-name")
    public List<InvestmentAccountModel> getByActionName(@RequestParam String actionName) {
        return service.findByActionNameIgnoreCase(actionName);
    }

    // Verifica se existe investimento com a ação especificada
    @GetMapping("/exists-by-action-name")
    public boolean existsByActionName(@RequestParam String actionName) {
        return service.existsByActionName(actionName);
    }

    // Buscar investimentos com valor maior que o especificado
    @GetMapping("/value-greater-than")
    public List<InvestmentAccountModel> getByValueGreaterThan(@RequestParam BigDecimal minValue) {
        return service.findByValueGreaterThan(minValue);
    }

    // Buscar investimentos com valor menor que o especificado
    @GetMapping("/value-less-than")
    public List<InvestmentAccountModel> getByValueLessThan(@RequestParam BigDecimal maxValue) {
        return service.findByValueLessThan(maxValue);
    }

    // Buscar investimentos com valor entre dois valores
    @GetMapping("/value-between")
    public List<InvestmentAccountModel> getByValueBetween(@RequestParam BigDecimal minValue, @RequestParam BigDecimal maxValue) {
        return service.findByValueBetween(minValue, maxValue);
    }

    // Buscar investimentos com quantidade maior que a especificada
    @GetMapping("/quantity-greater-than")
    public List<InvestmentAccountModel> getByQuantityGreaterThan(@RequestParam Integer minQuantity) {
        return service.findByQuantityGreaterThan(minQuantity);
    }

    // Buscar investimentos com quantidade menor que a especificada
    @GetMapping("/quantity-less-than")
    public List<InvestmentAccountModel> getByQuantityLessThan(@RequestParam Integer maxQuantity) {
        return service.findByQuantityLessThan(maxQuantity);
    }

    // Buscar investimentos com quantidade entre dois valores
    @GetMapping("/quantity-between")
    public List<InvestmentAccountModel> getByQuantityBetween(@RequestParam Integer minQuantity, @RequestParam Integer maxQuantity) {
        return service.findByQuantityBetween(minQuantity, maxQuantity);
    }

    // Contar total de investimentos
    @GetMapping("/count")
    public long countInvestments() {
        return service.count();
    }

    // Buscar todos os investimentos ordenados por nome da ação
    @GetMapping("/order-by-action-name-asc")
    public List<InvestmentAccountModel> getAllOrderByActionNameAsc() {
        return service.findAllOrderByActionNameAsc();
    }

    // Buscar todos os investimentos ordenados por valor (decrescente)
    @GetMapping("/order-by-value-desc")
    public List<InvestmentAccountModel> getAllOrderByValueDesc() {
        return service.findAllOrderByValueDesc();
    }

    // Buscar todos os investimentos ordenados por quantidade (decrescente)
    @GetMapping("/order-by-quantity-desc")
    public List<InvestmentAccountModel> getAllOrderByQuantityDesc() {
        return service.findAllOrderByQuantityDesc();
    }

    // Buscar investimentos com paginação
    @GetMapping("/paginated")
    public List<InvestmentAccountModel> getPaginated(@RequestParam Integer page, @RequestParam Integer size) {
        return (List<InvestmentAccountModel>) service.findAllInvestmentsPage(page, size);
    }

    // Buscar investimentos agrupados por ação
    @GetMapping("/grouped-by-action")
    public List<InvestmentAccountModel> getInvestmentsGroupedByAction() {
        return service.findInvestmentsGroupedByAction();
    }

    // Atualizar o valor de um investimento por ID
    @GetMapping("/update-value")
    public InvestmentAccountModel updateInvestmentValue(@RequestParam Long id, @RequestParam BigDecimal newValue) {
        int updatedRows = service.updateValueById(id, newValue);
        if (updatedRows > 0) {
            return service.findById(id).orElse(null);
        } else {
            return null; // Ou lançar uma exceção apropriada
        }
    }

    // Atualizar a quantidade de um investimento por ID
    @GetMapping("/update-quantity")
    public InvestmentAccountModel updateInvestmentQuantity(@RequestParam Long id, @RequestParam Integer newQuantity) {
        int updatedRows = service.updateQuantityById(id, newQuantity);
        if (updatedRows > 0) {
            return service.findById(id).orElse(null);
        } else {
            return null; // Ou lançar uma exceção apropriada
        }
    }

    // Atualizar o nome da ação de um investimento por ID
    @GetMapping("/update-action-name")
    public InvestmentAccountModel updateInvestmentActionName(@RequestParam Long id, @RequestParam String newActionName) {
        int updatedRows = service.updateActionNameById(id, newActionName);
        if (updatedRows > 0) {
            return service.findById(id).orElse(null);
        } else {
            return null; // Ou lançar uma exceção apropriada
        }
    }

    // Adiciona quantidade a um investimento
    @GetMapping("/add-to-quantity")
    public InvestmentAccountModel addToInvestmentQuantity(@RequestParam Long id, @RequestParam Integer amount) {
        int updatedRows = service.addToQuantity(id, amount);
        if (updatedRows > 0) {
            return service.findById(id).orElse(null);
        } else {
            return null; // Ou lançar uma exceção apropriada
        }
    }

    // Subtrai quantidade de um investimento
    @GetMapping("/subtract-from-quantity")
    public InvestmentAccountModel subtractFromInvestmentQuantity(@RequestParam Long id, @RequestParam Integer amount) {
        int updatedRows = service.subtractFromQuantity(id, amount);
        if (updatedRows > 0) {
            return service.findById(id).orElse(null);
        } else {
            return null; // Ou lançar uma exceção apropriada
        }
    }

    // Remove investimento por nome da ação
    @GetMapping("/delete-by-action-name")
    public int deleteByActionName(@RequestParam String actionName) {
        return service.deleteByActionName(actionName);
    }

    // Calcula o valor total investido por usuário
    @GetMapping("/total-value-by-user") 
    public BigDecimal getTotalInvestmentValueByUserId(@RequestParam Long userId) {
        return service.getTotalInvestmentValueByUserId(userId);
    }

    // Calcula a quantidade total de ações por usuário
    @GetMapping("/total-quantity-by-user")
    public Integer getTotalQuantityByUserId(@RequestParam Long userId) {
        return service.getTotalQuantityByUserId(userId);
    }

    // Encontra o maior valor entre todos os investimentos por usuário
    @GetMapping("/max-value-by-user")
    public BigDecimal getMaxValueByUserId(@RequestParam Long userId) {
        return service.getMaxValueByUserId(userId);
    }

    // Encontra o menor valor entre todos os investimentos por usuário
    @GetMapping("/min-value-by-user")
    public BigDecimal getMinValueByUserId(@RequestParam Long userId) {
        return service.getMinValueByUserId(userId);
    }

    // Encontra a maior quantidade entre todos os investimentos por usuário
    @GetMapping("/max-quantity-by-user")
    public Integer getMaxQuantityByUserId(@RequestParam Long userId) {
        return service.getMaxQuantityByUserId(userId);
    }

    // Encontra a menor quantidade entre todos os investimentos por usuário
    @GetMapping("/min-quantity-by-user")
    public Integer getMinQuantityByUserId(@RequestParam Long userId) {
        return service.getMinQuantityByUserId(userId);
    }
}

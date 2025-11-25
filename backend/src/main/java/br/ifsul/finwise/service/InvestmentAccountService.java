package br.ifsul.finwise.service;

import br.ifsul.finwise.model.InvestmentAccountModel;
import br.ifsul.finwise.repository.InvestmentAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvestmentAccountService {

    private final InvestmentAccountRepository repository;

    @Autowired
    public InvestmentAccountService(InvestmentAccountRepository repository) {
        this.repository = repository;
    }

    // -------------------- CRUD --------------------

    // Criar ou atualizar uma conta de investimento
    public InvestmentAccountModel save(InvestmentAccountModel investmentAccount) {
        return repository.save(investmentAccount);
    }

    // Buscar todas as contas de investimento
    public List<InvestmentAccountModel> findAll() {
        return repository.findAll();
    }

    // Buscar uma conta por ID
    public Optional<InvestmentAccountModel> findById(Integer id) {
        return repository.findById(id);
    }

    // Deletar uma conta por ID
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    // -------------------- MÉTODOS CUSTOMIZADOS --------------------

    // Buscar contas pelo nome da ação
    public List<InvestmentAccountModel> findByActionName(String actionName) {
        return repository.findByActionName(actionName);
    }

    // Buscar contas de um usuário específico
    public List<InvestmentAccountModel> findByUserId(Integer userId) {
        return repository.findByUserId(userId);
    }
}

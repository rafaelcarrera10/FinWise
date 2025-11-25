package br.ifsul.finwise.repository;

import br.ifsul.finwise.model.InvestmentAccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentAccountRepository extends JpaRepository<InvestmentAccountModel, Integer> {

    // Buscar contas de investimento por nome da ação
    List<InvestmentAccountModel> findByActionName(String actionName);

    // Buscar contas de investimento por usuário
    List<InvestmentAccountModel> findByUsuario_Id(Integer usuarioId);


}

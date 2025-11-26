package br.ifsul.finwise.repository;

import br.ifsul.finwise.model.ContaInvestimentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaInvestimentoRepositorio extends JpaRepository<ContaInvestimentoModel, Integer> {

    // Buscar contas de investimento por nome da ação
    List<ContaInvestimentoRepositorio> findByActionName(String actionName);

    // Buscar contas de investimento por usuário
    List<ContaInvestimentoRepositorio> findByUsuario_Id(Integer usuarioId);


}

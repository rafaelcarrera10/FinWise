package br.ifsul.finwise.service;

import br.ifsul.finwise.model.ContaFinanceiraModelo;
import br.ifsul.finwise.model.ContaInvestimentoModel;
import br.ifsul.finwise.repository.ContaFinanceiraRepositorio;
import br.ifsul.finwise.repository.ContaInvestimentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaInvestimentoService {

    @Autowired
    private ContaInvestimentoRepositorio repositorio;

    @Autowired
    private ContaFinanceiraRepositorio contaFinanceiraRepositorio;

    // Salvar nova conta de investimento
    public ContaInvestimentoModel save(ContaInvestimentoModel conta) {
        return repositorio.save(conta);
    }

    // Buscar todas as contas
    public List<ContaInvestimentoModel> findAll() {
        return repositorio.findAll();
    }

    @Transactional
    public ContaInvestimentoModel venderAcao(Integer idAcao, Integer quantidadeVenda) {

        // Buscar ação
        ContaInvestimentoModel acao = repositorio.findById(idAcao)
                .orElseThrow(() -> new RuntimeException("Ação não encontrada."));

        int quantidadeAtual = acao.getQuantity();

        if (quantidadeVenda <= 0) {
            throw new RuntimeException("Quantidade de venda inválida.");
        }

        if (quantidadeVenda > quantidadeAtual) {
            throw new RuntimeException("Quantidade maior do que a disponível.");
        }

        // Valor total da venda → PELA QUANTIDADE VENDIDA
        Double totalVenda = acao.getValue()
        .multiply(BigDecimal.valueOf(quantidadeVenda))  // continua como BigDecimal
        .doubleValue();                                  // converte pra Double

        // Buscar conta financeira do usuário
        ContaFinanceiraModelo conta = contaFinanceiraRepositorio
        .findByUsuarioId(acao.getusuario().getId())
        .orElseThrow(() -> new RuntimeException("Conta financeira não encontrada."));

        // Atualizar saldo (Double)
        conta.setSaldoAtual(conta.getSaldoAtual() + totalVenda);

        // Salvar conta atualizada
        contaFinanceiraRepositorio.save(conta);


        // --- PROCESSAR VENDA ---

        // Venda total → DELETE
        if (quantidadeVenda == quantidadeAtual) {
            repositorio.delete(acao);
            return null;
        }

        // Venda parcial → UPDATE
        acao.setQuantity(quantidadeAtual - quantidadeVenda);
        return repositorio.save(acao);
    }


    // Buscar por ID
    public Optional<ContaInvestimentoModel> findById(Integer id) {
        return repositorio.findById(id);
    }

    // Buscar por nome da ação
    public List<ContaInvestimentoRepositorio> findByActionName(String actionName) {
        return repositorio.findByActionName(actionName);
    }

    // Buscar por ID do usuário
    public List<ContaInvestimentoRepositorio> findByUsuarioId(Integer usuarioId) {
        return repositorio.findByUsuario_Id(usuarioId);
    }

    // Atualizar conta
    public ContaInvestimentoModel update(ContaInvestimentoModel conta) {
        if (conta.getId() != null && repositorio.existsById(conta.getId())) {
            return repositorio.save(conta);
        } else {
            throw new RuntimeException("Conta de investimento não encontrada para atualização");
        }
    }

    // Deletar conta
    public void deleteById(Integer id) {
        repositorio.deleteById(id);
    }
}

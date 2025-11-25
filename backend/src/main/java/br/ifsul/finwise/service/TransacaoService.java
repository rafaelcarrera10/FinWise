package br.ifsul.finwise.service;

import br.ifsul.finwise.model.TransacaoModelo;
import br.ifsul.finwise.repository.TransacaoRepositorio;
import br.ifsul.finwise.repository.CategoriaRepositorio;
import br.ifsul.finwise.repository.ContaFinanceiraRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    private final TransacaoRepositorio transacaoRepositorio;
    private final ContaFinanceiraRepositorio contaRepositorio;
    private final CategoriaRepositorio categoriaRepositorio;

    @Autowired
    public TransacaoService(
            TransacaoRepositorio transacaoRepositorio,
            ContaFinanceiraRepositorio contaRepositorio,
            CategoriaRepositorio categoriaRepositorio) {
        this.transacaoRepositorio = transacaoRepositorio;
        this.contaRepositorio = contaRepositorio;
        this.categoriaRepositorio = categoriaRepositorio;
    }

    // Salvar transação
    public TransacaoModelo salvar(TransacaoModelo transacao) {
        if (!transacao.isValida()) {
            throw new IllegalArgumentException("Transação inválida");
        }
        return transacaoRepositorio.save(transacao);
    }

    // Buscar por ID
    public Optional<TransacaoModelo> buscarPorId(Integer id) {
        return transacaoRepositorio.findById(id);
    }

    // Listar transações de uma conta
    public List<TransacaoModelo> listarPorConta(Integer contaId) {
        return transacaoRepositorio.findByContaId(contaId);
    }

    // Listar transações por categoria
    public List<TransacaoModelo> listarPorCategoria(Integer categoriaId) {
        return transacaoRepositorio.findByCategoriaId(categoriaId);
    }

    // Listar transações ativas
    public List<TransacaoModelo> listarAtivas() {
        return transacaoRepositorio.findAtivas();
    }

    // Atualizar transação
    public TransacaoModelo atualizarTransacao(Integer id, TransacaoModelo dados) {
        TransacaoModelo transacao = transacaoRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada"));

        // Atualiza campos enviados
        if (dados.getValor() != null) {
            if (dados.getValor() <= 0)
                throw new IllegalArgumentException("O valor deve ser maior que zero.");
            transacao.setValor(dados.getValor());
        }

        if (dados.getDataInicial() != null) {
            transacao.setDataInicial(dados.getDataInicial());
        }

        if (dados.getDataFinal() != null) {
            if (dados.getDataFinal().before(transacao.getDataInicial()))
                throw new IllegalArgumentException("A data final não pode ser antes da data inicial.");
            transacao.setDataFinal(dados.getDataFinal());
        }

        if (dados.getDescricao() != null) {
            if (dados.getDescricao().isBlank())
                throw new IllegalArgumentException("A descrição não pode ser vazia.");
            transacao.setDescricao(dados.getDescricao());
        }

        if (dados.getObservacao() != null) {
            transacao.setObservacao(dados.getObservacao());
        }

        if (dados.getRepeticao() != null) {
            transacao.setRepeticao(dados.getRepeticao());
        }

        if (dados.getCategoria() != null && dados.getCategoria().getId() != null) {
            var categoria = categoriaRepositorio.findById(dados.getCategoria().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria inválida"));
            transacao.setCategoria(categoria);
        }

        if (dados.getConta() != null && dados.getConta().getId() != null) {
            var conta = contaRepositorio.findById(dados.getConta().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Conta inválida"));
            transacao.setConta(conta);
        }

        if (!transacao.isValida()) {
            throw new IllegalArgumentException("A transação possui dados inválidos.");
        }

        return transacaoRepositorio.save(transacao);
    }

    // Deletar transação
    public void deletar(Integer id) {
        transacaoRepositorio.deleteById(id);
    }
}

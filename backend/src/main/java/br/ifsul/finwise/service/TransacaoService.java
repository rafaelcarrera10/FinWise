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

    @Autowired
    private final TransacaoRepositorio repositorio;

    @Autowired
    private ContaFinanceiraRepositorio ContaFinanceiraRepositorio;

    @Autowired
    private CategoriaRepositorio CategoriaRepositorio;

    public TransacaoService(TransacaoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    // Salvar transação genérica
    public TransacaoModelo salvar(TransacaoModelo transacao) {

        // Regra: validar antes de salvar
        if (!transacao.isValida()) {
            throw new IllegalArgumentException("Transação inválida");
        }

        return repositorio.save(transacao);
    }

    // Buscar por ID
    public Optional<TransacaoModelo> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    // Buscar por conta
    public List<TransacaoModelo> listarPorConta(Integer contaId) {
        return repositorio.findByContaId(contaId);
    }

    // Buscar por categoria
    public List<TransacaoModelo> listarPorCategoria(Integer categoriaId) {
        return repositorio.findByCategoriaId(categoriaId);
    }

    // Buscar transações ativas
    public List<TransacaoModelo> listarAtivas() {
        return repositorio.findAtivas();
    }

    // Atualizar transação
    public TransacaoModelo atualizarTransacao(Integer id, TransacaoModelo dados) {

        // Busca a transação no banco
        TransacaoModelo transacao = repositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada"));
    
        // Atualiza somente os campos enviados
    
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
    
        // Atualiza categoria se enviada
        if (dados.getCategoria() != null && dados.getCategoria().getId() != null) {
            var categoria = CategoriaRepositorio.findById(dados.getCategoria().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoria inválida"));
            transacao.setCategoria(categoria);
        }
    
        // Atualiza conta se enviada
        if (dados.getConta() != null && dados.getConta().getId() != null) {
            var conta = ContaFinanceiraRepositorio.findById(dados.getConta().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Conta inválida"));
            transacao.setConta(conta);
        }
    
        // Validação final
        if (!transacao.isValida()) {
            throw new IllegalArgumentException("A transação possui dados inválidos.");
        }
    
        // Salva no banco
        return repositorio.save(transacao);
    }
    

    // Deletar por ID
    public void deletar(Integer id) {
        repositorio.deleteById(id);
    }
}

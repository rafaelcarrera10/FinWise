package br.ifsul.finwise.service;

import br.ifsul.finwise.model.TransacaoModelo;
import br.ifsul.finwise.repository.TransacaoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    private final TransacaoRepositorio repositorio;

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
    public List<TransacaoModelo> listarPorCategoria(Long categoriaId) {
        return repositorio.findByCategoriaId(categoriaId);
    }

    // Buscar transações ativas
    public List<TransacaoModelo> listarAtivas() {
        return repositorio.findAtivas();
    }

    // Deletar por ID
    public void deletar(Integer id) {
        repositorio.deleteById(id);
    }
}

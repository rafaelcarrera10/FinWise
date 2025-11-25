package br.ifsul.finwise.service;

import br.ifsul.finwise.model.DespesaModelo;
import br.ifsul.finwise.repository.DespesaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DespesaService {

    private final DespesaRepositorio repositorio;

    public DespesaService(DespesaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    // Criar despesa
    public DespesaModelo criar(DespesaModelo despesa) {

        // Validar limite de 30 despesas por conta
        List<DespesaModelo> despesasConta = repositorio.findByContaId(despesa.getConta().getId());
        if (despesasConta.size() >= 30) {
            throw new IllegalArgumentException("Limite de 30 despesas por conta atingido.");
        }

        if (!despesa.isValida()) {
            throw new IllegalArgumentException("Despesa inválida.");
        }

        return repositorio.save(despesa);
    }

    // Buscar por ID
    public Optional<DespesaModelo> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    // Listar todas as despesas de uma conta
    public List<DespesaModelo> buscarPorConta(Integer contaId) {
        return repositorio.findByContaId(contaId);
    }

    // Listar despesas por categoria
    public List<DespesaModelo> buscarPorCategoria(Integer categoriaId) {
        return repositorio.findByCategoriaId(categoriaId);
    }

    // Atualizar despesa parcialmente
    public DespesaModelo editar(Integer id, DespesaModelo despesa) {
        DespesaModelo existente = repositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Despesa não encontrada."));

        if (despesa.getValor() != null) existente.setValor(despesa.getValor());
        if (despesa.getDataInicial() != null) existente.setDataInicial(despesa.getDataInicial());
        if (despesa.getDataFinal() != null) existente.setDataFinal(despesa.getDataFinal());
        if (despesa.getDescricao() != null) existente.setDescricao(despesa.getDescricao());
        if (despesa.getObservacao() != null) existente.setObservacao(despesa.getObservacao());
        if (despesa.getRepeticao() != null) existente.setRepeticao(despesa.getRepeticao());
        if (despesa.getCategoria() != null) existente.setCategoria(despesa.getCategoria());
        if (despesa.getConta() != null) existente.setConta(despesa.getConta());
        if (despesa.getStatus() != null) existente.setStatus(despesa.getStatus());
        if (despesa.getCartao() != null) existente.setCartao(despesa.getCartao());

        return repositorio.save(existente);
    }

    // Deletar despesa
    public void deletar(Integer id) {
        repositorio.deleteById(id);
    }
}

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

    // Salvar despesa
    public DespesaModelo salvar(DespesaModelo despesa) {

        // Regra: valida se a transação é válida
        if (!despesa.isValida()) {
            throw new IllegalArgumentException("Despesa inválida");
        }

        return repositorio.save(despesa);
    }

    // Buscar todos
    public List<DespesaModelo> buscarTodos() {
        return repositorio.findAll();
    }

    // Buscar por conta
    public List<DespesaModelo> buscarPorConta(Integer contaId) {
        return repositorio.findByContaId(contaId);
    }

    // Buscar por categoria
    public List<DespesaModelo> buscarPorCategoria(Integer categoriaId) {
        return repositorio.findByCategoriaId(categoriaId);
    }

    // Buscar por ID
    public Optional<DespesaModelo> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    // Listar despesas de uma conta
    public List<DespesaModelo> listarPorConta(Integer contaId) {
        return repositorio.findByContaId(contaId);
    }

    // Listar despesas por categoria
    public List<DespesaModelo> listarPorCategoria(Integer categoriaId) {
        return repositorio.findByCategoriaId(categoriaId);
    }

    // Atualizar despesa
    public DespesaModelo editar(Integer id, DespesaModelo despesa) {
        return repositorio.save(despesa);
    }

    // Deletar por ID
    public void deletar(Integer id) {
        repositorio.deleteById(id);
    }
}

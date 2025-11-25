package main.java.br.ifsul.finwise.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.ifsul.finwise.model.ReceitaModelo;
import br.ifsul.finwise.repository.ReceitaRepository;
import jakarta.transaction.Transactional;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;

    public ReceitaService(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    // ------------------ CREATE ------------------
    @Transactional
    public ReceitaModelo salvar(ReceitaModelo receita) {
        if (!receita.isValida()) {
            throw new IllegalArgumentException("Receita inválida.");
        }

        return receitaRepository.save(receita);
    }

    // ------------------ READ ------------------
    public List<ReceitaModelo> listarTodas() {
        return receitaRepository.findAll();
    }

    public ReceitaModelo buscarPorId(Integer id) {
        Optional<ReceitaModelo> receita = receitaRepository.findById(id);
        return receita.orElse(null);
    }

    public List<ReceitaModelo> buscarPorConta(Integer contaId) {
        return receitaRepository.findByContaId(contaId);
    }

    public List<ReceitaModelo> buscarPorCategoria(Integer categoriaId) {
        return receitaRepository.findByCategoriaId(categoriaId);
    }

    // ------------------ UPDATE ------------------
    @Transactional
    public ReceitaModelo atualizar(Integer id, ReceitaModelo novaReceita) {

        ReceitaModelo existente = receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada."));

        if (!novaReceita.isValida()) {
            throw new IllegalArgumentException("Receita inválida.");
        }

        existente.setValor(novaReceita.getValor());
        existente.setDescricao(novaReceita.getDescricao());
        existente.setObservacao(novaReceita.getObservacao());
        existente.setDataInicial(novaReceita.getDataInicial());
        existente.setDataFinal(novaReceita.getDataFinal());
        existente.setRepeticao(novaReceita.getRepeticao());
        existente.setCategoria(novaReceita.getCategoria());
        existente.setConta(novaReceita.getConta());

        return receitaRepository.save(existente);
    }

    // ------------------ DELETE ------------------
    @Transactional
    public void deletar(Integer id) {
        if (!receitaRepository.existsById(id)) {
            throw new RuntimeException("Receita não encontrada.");
        }
        receitaRepository.deleteById(id);
    }
}

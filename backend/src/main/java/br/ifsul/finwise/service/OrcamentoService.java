package br.ifsul.finwise.service;

import br.ifsul.finwise.model.OrcamentoModelo;
import br.ifsul.finwise.repository.OrcamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService {

    @Autowired
    private OrcamentoRepositorio orcamentoRepositorio;

    // Criar ou atualizar orçamento
    public OrcamentoModelo save(OrcamentoModelo orcamento) {
        if (orcamento.getValor() == null || orcamento.getValor() <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
        return orcamentoRepositorio.save(orcamento);
    }

    // Buscar todos os orçamentos
    public List<OrcamentoModelo> findAll() {
        return orcamentoRepositorio.findAll();
    }

    // Buscar por ID
    public Optional<OrcamentoModelo> findById(Integer id) {
        return orcamentoRepositorio.findById(id);
    }

    // Deletar por ID
    @Transactional
    public void deleteById(Integer id) {
        if (!orcamentoRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Orçamento não encontrado");
        }
        orcamentoRepositorio.deleteById(id);
    }
}

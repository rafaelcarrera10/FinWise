package br.ifsul.finwise.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.ifsul.finwise.model.PlanoOrcamentarioModelo;
import br.ifsul.finwise.repository.PlanoOrcamentarioRepository;

@Service
public class PlanoOrcamentarioService {

    private final PlanoOrcamentarioRepository planoRepository;

    public PlanoOrcamentarioService(PlanoOrcamentarioRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    public PlanoOrcamentarioModelo save(PlanoOrcamentarioModelo plano) {
        return planoRepository.save(plano);
    }

    public PlanoOrcamentarioModelo update(Integer id, PlanoOrcamentarioModelo plano) {
        Optional<PlanoOrcamentarioModelo> existing = planoRepository.findById(id);
        if (existing.isEmpty()) return null;

        plano.setId(id);
        return planoRepository.save(plano);
    }

    public List<PlanoOrcamentarioModelo> findAll() {
        return planoRepository.findAll();
    }

    public Optional<PlanoOrcamentarioModelo> findById(Integer id) {
        return planoRepository.findById(id);
    }

    public boolean delete(Integer id) {
        Optional<PlanoOrcamentarioModelo> existing = planoRepository.findById(id);
        if (existing.isEmpty()) return false;

        planoRepository.deleteById(id);
        return true;
    }

    public List<PlanoOrcamentarioModelo> findByNome(String nome) {
        return planoRepository.findByNome(nome);
    }

    // ❗ Serviço corrigido
    public List<PlanoOrcamentarioModelo> findByContaId(Integer contaFinanceiraId) {
        return planoRepository.findByContaFinanceira_Id(contaFinanceiraId);
    }

    public List<PlanoOrcamentarioModelo> findByCondicao(Boolean condicao) {
        return planoRepository.findByCondicao(condicao);
    }
}

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

    // CREATE
    public PlanoOrcamentarioModelo save(PlanoOrcamentarioModelo plano) {
        return planoRepository.save(plano);
    }

    // UPDATE
    public PlanoOrcamentarioModelo update(Integer id, PlanoOrcamentarioModelo plano) {
        Optional<PlanoOrcamentarioModelo> existing = planoRepository.findById(id);
        if (existing.isEmpty()) {
            return null;
        }
        plano.setId(id); // garante que atualiza o mesmo ID
        return planoRepository.save(plano);
    }

    // READ ALL
    public List<PlanoOrcamentarioModelo> findAll() {
        return planoRepository.findAll();
    }

    // READ BY ID
    public Optional<PlanoOrcamentarioModelo> findById(Integer id) {
        return planoRepository.findById(id);
    }

    // DELETE
    public boolean delete(Integer id) {
        Optional<PlanoOrcamentarioModelo> existing = planoRepository.findById(id);
        if (existing.isEmpty()) {
            return false;
        }
        planoRepository.deleteById(id);
        return true;
    }

    // MÉTODOS OPCIONAIS
    public List<PlanoOrcamentarioModelo> findByNome(String nome) {
        return planoRepository.findByNome(nome);
    }

    public List<PlanoOrcamentarioModelo> findByContaId(Integer contaFinanceira) {
        return planoRepository.findByContaId(contaFinanceira);
    }

    public List<PlanoOrcamentarioModelo> findByCondicao(Boolean condicao) {
        return planoRepository.findByCondicao(condicao);
    }
}

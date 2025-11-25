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

    // CREATE / UPDATE
    public PlanoOrcamentarioModelo save(PlanoOrcamentarioModelo plano) {
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

    // READ BY NOME
    public List<PlanoOrcamentarioModelo> findByNome(String nome) {
        return planoRepository.findByNome(nome);
    }

    // READ BY CONTA
    public List<PlanoOrcamentarioModelo> findByContaId(Integer contaFinanceira) {
        return planoRepository.findByContaId(contaFinanceira);
    }

    // READ BY CONDICAO
    public List<PlanoOrcamentarioModelo> findByCondicao(Boolean condicao) {
        return planoRepository.findByCondicao(condicao);
    }

    // DELETE
    public void deleteById(Integer id) {
        planoRepository.deleteById(id);
    }
}


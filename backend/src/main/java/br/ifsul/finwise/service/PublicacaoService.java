package br.ifsul.finwise.service;

import org.springframework.stereotype.Service;

import br.ifsul.finwise.model.ProfessorModelo;
import br.ifsul.finwise.model.PublicacaoModelo;
import br.ifsul.finwise.model.TagEnum;
import br.ifsul.finwise.repository.PublicacaoRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class PublicacaoService {

    private final PublicacaoRepositorio publicacaoRepositorio;

    public PublicacaoService(PublicacaoRepositorio publicacaoRepositorio) {
        this.publicacaoRepositorio = publicacaoRepositorio;
    }

    public PublicacaoModelo save(PublicacaoModelo publicacao) {
        return publicacaoRepositorio.save(publicacao);
    }

    public List<PublicacaoModelo> findAll() {
        return publicacaoRepositorio.findAll();
    }

    public Optional<PublicacaoModelo> findById(Integer id) {
        return publicacaoRepositorio.findById(id);
    }

    public Optional<PublicacaoModelo> findByTitulo(String titulo) {
        return publicacaoRepositorio.findByTitulo(titulo);
    }

    public List<PublicacaoModelo> searchByTitulo(String palavraChave) {
        return publicacaoRepositorio.findByTituloContainingIgnoreCase(palavraChave);
    }

    public List<PublicacaoModelo> findByTag(TagEnum tag) {
        return publicacaoRepositorio.findByTag(tag);
    }

    public List<PublicacaoModelo> findByProfessor(ProfessorModelo professor) {
        return publicacaoRepositorio.findByProfessor(professor);
    }

    public void deleteById(Integer id) {
        publicacaoRepositorio.deleteById(id);
    }
}



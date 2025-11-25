package br.ifsul.finwise.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import br.ifsul.finwise.model.PublicacaoModelo;
import br.ifsul.finwise.model.TagEnum;
import br.ifsul.finwise.repository.PublicacaoRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class PublicacaoService {

    @Autowired
    private PublicacaoRepositorio publicacaoRepositorio;

    // Criar ou atualizar publicação
    public PublicacaoModelo save(PublicacaoModelo publicacao) {
        return publicacaoRepositorio.save(publicacao);
    }

    // Buscar todas as publicações
    public List<PublicacaoModelo> findAll() {
        return publicacaoRepositorio.findAll();
    }

    // Buscar por ID
    public Optional<PublicacaoModelo> findById(Integer id) {
        return publicacaoRepositorio.findById(id);
    }

    // Buscar por título
    public Optional<PublicacaoModelo> findByTitulo(String titulo) {
        return publicacaoRepositorio.findByTitulo(titulo);
    }

    // Buscar por título parcialmente
    public List<PublicacaoModelo> searchByTitulo(String palavraChave) {
        return publicacaoRepositorio.findByTituloContainingIgnoreCase(palavraChave);
    }

    // Buscar por tag
    public List<PublicacaoModelo> findByTag(TagEnum tag) {
        return publicacaoRepositorio.findByTag(tag);
    }

    // Deletar publicação
    public void deleteById(Integer id) {
        publicacaoRepositorio.deleteById(id);
    }
}


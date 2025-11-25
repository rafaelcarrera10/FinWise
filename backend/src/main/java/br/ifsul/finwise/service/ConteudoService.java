package br.ifsul.finwise.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import br.ifsul.finwise.model.ConteudoModelo;
import br.ifsul.finwise.repository.ConteudoRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class ConteudoService {

    @Autowired
    private ConteudoRepositorio conteudoRepositorio;

    // Criar ou atualizar conteúdo
    public ConteudoModelo save(ConteudoModelo conteudo) {
        return conteudoRepositorio.save(conteudo);
    }

    // Buscar todos
    public List<ConteudoModelo> findAll() {
        return conteudoRepositorio.findAll();
    }

    // Buscar por ID
    public Optional<ConteudoModelo> findById(Integer id) {
        return conteudoRepositorio.findById(id);
    }

    // Buscar por título
    public Optional<ConteudoModelo> findByTitulo(String titulo) {
        return conteudoRepositorio.findByTitulo(titulo);
    }

    // Buscar por título parcialmente
    public List<ConteudoModelo> searchByTitulo(String palavraChave) {
        return conteudoRepositorio.findByTituloContainingIgnoreCase(palavraChave);
    }

    // Buscar conteúdos de um professor
    public List<ConteudoModelo> buscarPorProfessor(Integer professorId) {
        return conteudoRepositorio.findByProfessorId(professorId);
    }

    // Deletar
    public void deleteById(Integer id) {
        conteudoRepositorio.deleteById(id);
    }
}

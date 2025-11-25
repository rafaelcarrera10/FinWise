package br.ifsul.finwise.service;

import br.ifsul.finwise.model.ConteudoModelo;
import br.ifsul.finwise.repository.ConteudoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConteudoService {

    @Autowired
    private ConteudoRepositorio conteudoRepositorio;

    /**
     * Salva ou atualiza um conteúdo.
     */
    public ConteudoModelo save(ConteudoModelo conteudo) {
        validarCamposObrigatorios(conteudo);
        return conteudoRepositorio.save(conteudo);
    }

    /**
     * Retorna todos os conteúdos cadastrados.
     */
    public List<ConteudoModelo> findAll() {
        return conteudoRepositorio.findAll();
    }

    /**
     * Busca um conteúdo pelo ID.
     */
    public Optional<ConteudoModelo> findById(Integer id) {
        return conteudoRepositorio.findById(id);
    }

    /**
     * Remove um conteúdo pelo ID.
     * Lança exceção se o ID não for encontrado.
     */
    public void deleteById(Integer id) {
        if (!conteudoRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Conteúdo não encontrado para exclusão.");
        }
        conteudoRepositorio.deleteById(id);
    }

    /**
     * Busca conteúdos contendo parte do título.
     */
    public List<ConteudoModelo> searchByTitulo(String titulo) {
        return conteudoRepositorio.findByTituloContainingIgnoreCase(titulo);
    }

    /**
     * Busca conteúdos pertencentes a um professor.
     */
    public List<ConteudoModelo> buscarPorProfessor(Integer professorId) {
        return conteudoRepositorio.findByProfessorId(professorId);
    }

    /**
     * Valida campos obrigatórios do conteúdo antes de salvar.
     */
    private void validarCamposObrigatorios(ConteudoModelo conteudo) {
        if (conteudo.getTitulo() == null || conteudo.getTitulo().isBlank()) {
            throw new IllegalArgumentException("Título é obrigatório.");
        }

        if (conteudo.getDescricao() == null || conteudo.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição é obrigatória.");
        }

        if (conteudo.getProfessor() == null) {
            throw new IllegalArgumentException("O conteúdo deve estar associado a um professor.");
        }
    }
}

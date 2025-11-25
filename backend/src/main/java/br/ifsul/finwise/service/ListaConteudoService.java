package br.ifsul.finwise.service;

import br.ifsul.finwise.model.ListaConteudoModelo;
import br.ifsul.finwise.model.TagEnum;
import br.ifsul.finwise.repository.ListaConteudoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListaConteudoService {

    private final ListaConteudoRepositorio repositorio;

    public ListaConteudoService(ListaConteudoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    // Salvar lista
    public ListaConteudoModelo salvar(ListaConteudoModelo lista) {
        return repositorio.save(lista);
    }

    // Buscar por ID
    public Optional<ListaConteudoModelo> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    // Buscar todos do professor
    public List<ListaConteudoModelo> buscarPorProfessor(Integer professorId) {
        return repositorio.findByProfessorId(professorId);
    }

    // Buscar listas que contenham uma tag específica
    public List<ListaConteudoModelo> buscarPorTag(String tag) {
        return repositorio.findByTag(TagEnum.valueOf(tag.toUpperCase()));
    }

    // Buscar por nome
    public List<ListaConteudoModelo> buscarPorNome(String nome) {
        return repositorio.findByNomeContainingIgnoreCase(nome);
    }

    // Deletar por ID
    public void deletar(Integer id) {
        repositorio.deleteById(id);
    }
}

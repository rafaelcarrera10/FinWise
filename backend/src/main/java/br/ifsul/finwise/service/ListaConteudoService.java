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
        // Aqui você pode adicionar regras de validação da lista, se necessário
        return repositorio.save(lista);
    }

    // Buscar por ID
    public Optional<ListaConteudoModelo> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    // Listar todas
    public List<ListaConteudoModelo> listarTodas() {
        return repositorio.findAll();
    }

    // Buscar por professor
    public List<ListaConteudoModelo> buscarPorProfessor(Integer professorId) {
        return repositorio.findByProfessorId(professorId);
    }

    // Buscar por tag
    public List<ListaConteudoModelo> buscarPorTag(String tag) {
        return repositorio.findByTag(TagEnum.valueOf(tag.toUpperCase()));
    }

    // Buscar por nome
    public List<ListaConteudoModelo> buscarPorNome(String nome) {
        return repositorio.findByNomeContainingIgnoreCase(nome);
    }

    // Atualizar lista
    public ListaConteudoModelo editar(Integer id, ListaConteudoModelo lista) {
        // Aqui você pode mesclar os campos que quiser atualizar
        lista.setId(id);
        return repositorio.save(lista);
    }

    // Deletar por ID
    public void deletar(Integer id) {
        repositorio.deleteById(id);
    }
}

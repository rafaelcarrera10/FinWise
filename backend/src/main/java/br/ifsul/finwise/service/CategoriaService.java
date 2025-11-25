package br.ifsul.finwise.service;

import br.ifsul.finwise.model.CategoriaModelo;
import br.ifsul.finwise.repository.CategoriaRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepositorio repositorio;

    public CategoriaService(CategoriaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    // Salvar categoria
    public CategoriaModelo salvar(CategoriaModelo categoria) {
        return repositorio.save(categoria);
    }

    // Buscar por ID
    public Optional<CategoriaModelo> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    // Buscar por nome exato
    public Optional<CategoriaModelo> buscarPorNome(String nome) {
        return repositorio.findByName(nome);
    }

    // Listar categorias de um usuário
    public List<CategoriaModelo> listarPorUsuario(Integer userId) {
        return repositorio.findByUsuarioId(userId);
    }

    // Listar categorias de um usuário ordenadas pelo nome
    public List<CategoriaModelo> listarPorUsuarioOrdenado(Integer userId) {
        return repositorio.findByUsuarioIdOrderByNameAsc(userId);
    }

    // Deletar categoria pelo nome
    public int deletarPorNome(String nome) {
        return repositorio.deleteByName(nome);
    }

    // Deletar por ID
    public void deletarPorId(Integer id) {
        repositorio.deleteById(id);
    }
}

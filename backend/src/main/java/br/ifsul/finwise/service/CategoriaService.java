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

    public CategoriaModelo salvar(CategoriaModelo categoria) {
        return repositorio.save(categoria);
    }

    public Optional<CategoriaModelo> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    public Optional<CategoriaModelo> buscarPorNome(String nome) {
        return repositorio.findByName(nome);
    }

    public List<CategoriaModelo> listarPorUsuario(Integer userId) {
        return repositorio.findByUsuarioId(userId);
    }

    public List<CategoriaModelo> listarPorUsuarioOrdenado(Integer userId) {
        return repositorio.findByUsuarioIdOrderByNameAsc(userId);
    }

    public int deletarPorNome(String nome) {
        return repositorio.deleteByName(nome);
    }

    public void deletarPorId(Integer id) {
        repositorio.deleteById(id);
    }
}

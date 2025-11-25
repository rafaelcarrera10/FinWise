package br.ifsul.finwise.service;

import br.ifsul.finwise.model.ContaFinanceiraModelo;
import br.ifsul.finwise.repository.ContaFinanceiraRepositorio;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaFinanceiraService {

    private final ContaFinanceiraRepositorio repositorio;

    public ContaFinanceiraService(ContaFinanceiraRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public ContaFinanceiraModelo salvar(ContaFinanceiraModelo conta) {
        return repositorio.save(conta);
    }

    public Optional<ContaFinanceiraModelo> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    public Optional<ContaFinanceiraModelo> buscarPorUsuario(Integer usuarioId) {
        return repositorio.findByUsuarioId(usuarioId);
    }

    public boolean existeParaUsuario(Integer usuarioId) {
        return repositorio.existsByUsuarioId(usuarioId);
    }

    public void deletarPorId(Integer id) {
        repositorio.deleteById(id);
    }
}

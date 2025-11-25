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

    // Salvar conta financeira
    public ContaFinanceiraModelo salvar(ContaFinanceiraModelo conta) {
        return repositorio.save(conta);
    }

    // Buscar por ID
    public Optional<ContaFinanceiraModelo> buscarPorId(Integer id) {
        return repositorio.findById(id);
    }

    // Buscar conta por usuário
    public Optional<ContaFinanceiraModelo> buscarPorUsuario(Long usuarioId) {
        return repositorio.findByUsuarioId(usuarioId);
    }

    // Deletar por ID
    public void deletar(Integer id) {
        repositorio.deleteById(id);
    }
}

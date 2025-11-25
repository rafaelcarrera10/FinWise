package br.ifsul.finwise.service;

import br.ifsul.finwise.model.CartaoCreditoModelo;
import br.ifsul.finwise.repository.CartaoCreditoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoCreditoService {

    @Autowired
    private CartaoCreditoRepositorio cartaoRepositorio;

    // Criar ou atualizar cartão
    public CartaoCreditoModelo save(CartaoCreditoModelo cartao) {

        if (cartao.getDescricao() == null || cartao.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição não pode ser nula ou vazia");
        }

        if (cartao.getDiaVencimento() == null || cartao.getDiaFechamento() == null) {
            throw new IllegalArgumentException("Dia de vencimento e fechamento são obrigatórios");
        }

        if (cartao.getFaturaAtual() == null || cartao.getFaturaAtual() < 0) {
            cartao.setFaturaAtual(0.0);
        }

        if (cartao.getFaturaSeguinte() == null || cartao.getFaturaSeguinte() < 0) {
            cartao.setFaturaSeguinte(0.0);
        }

        // Calcula a fatura com base nas despesas
        if (cartao.getListaDespesa() != null) {
            cartao.calcularFatura(cartao.getListaDespesa());
        }

        return cartaoRepositorio.save(cartao);
    }

    // Buscar todos os cartões
    public List<CartaoCreditoModelo> findAll() {
        return cartaoRepositorio.findAll();
    }

    // Buscar por ID
    public Optional<CartaoCreditoModelo> findById(Integer id) {
        return cartaoRepositorio.findById(id);
    }

    // Deletar por ID
    @Transactional
    public void deleteById(Integer id) {
        if (!cartaoRepositorio.existsById(id)) {
            throw new IllegalArgumentException("Cartão não encontrado");
        }
        cartaoRepositorio.deleteById(id);
    }
}

package br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.CartaoCreditoModelo;

import java.util.Optional;

@Repository
public interface CartaoCreditoRepositorio extends JpaRepository<CartaoCreditoModelo, Integer> {

    Optional<CartaoCreditoModelo> findById(Integer id);

}

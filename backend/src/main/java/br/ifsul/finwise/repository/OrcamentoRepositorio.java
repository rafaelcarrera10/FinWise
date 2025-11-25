package br.ifsul.finwise.repository;

import br.ifsul.finwise.model.OrcamentoModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrcamentoRepositorio extends JpaRepository<OrcamentoModelo, Integer> {

    Optional<OrcamentoModelo> findById(Integer id);

}

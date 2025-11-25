package main.java.br.ifsul.finwise.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.PlanoOrcamentarioModelo;

@Repository
public interface PlanoOrcamentarioRepository extends JpaRepository<PlanoOrcamentarioModelo, Integer> {

    List<PlanoOrcamentarioModelo> findByNome(String nome);

    List<PlanoOrcamentarioModelo> findByContaId(Integer contaFinanceira);

    List<PlanoOrcamentarioModelo> findByCondicao(Boolean condicao);


}
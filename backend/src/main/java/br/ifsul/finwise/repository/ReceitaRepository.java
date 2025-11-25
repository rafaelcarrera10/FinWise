package main.java.br.ifsul.finwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifsul.finwise.model.ReceitaModelo;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<ReceitaModelo, Integer> {

    // Buscar receitas por conta
    List<ReceitaModelo> findByContaId(Integer contaId);

    // Buscar receitas por categoria
    List<ReceitaModelo> findByCategoriaId(Integer categoriaId);

    // Buscar receitas ativas
    List<ReceitaModelo> findByDataFinalIsNull();

    // Buscar receitas dentro de um intervalo de datas
    List<ReceitaModelo> findByDataInicialBetween(java.sql.Date inicio, java.sql.Date fim);
}

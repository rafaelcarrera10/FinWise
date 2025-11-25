package br.ifsul.finwise.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contaFinanceira")
public class ContaFinanceiraModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    // Relacionamentos

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "usuario_id")
    private UsuarioModelo usuario;

    @OneToMany(mappedBy = "contaFinanceira", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransacaoModelo> listaTransacao = new ArrayList<>();

    
}

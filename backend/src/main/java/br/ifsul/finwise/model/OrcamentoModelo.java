package br.ifsul.finwise.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "orcamento")
public class OrcamentoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor", nullable = false)
    @NotNull(message = "valor não pode ser nulo")
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "plano_orcamentario_id", nullable = false)
    private PlanoOrcamentarioModelo plano;

    @OneToOne(mappedBy = "orcamento")
    private CategoriaModelo categoria;

    public OrcamentoModelo() {}

    public OrcamentoModelo(Integer id, Double valor) {
        this.id = id;
        this.valor = valor;
    }

    public OrcamentoModelo(Integer id, Double valor, PlanoOrcamentarioModelo plano, CategoriaModelo categoria) {
        this.id = id;
        this.valor = valor;
        this.plano = plano;
        this.categoria = categoria;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public PlanoOrcamentarioModelo getPlano() { return plano; }
    public void setPlano(PlanoOrcamentarioModelo plano) { this.plano = plano; }

    public CategoriaModelo getCategoria() { return categoria; }
    public void setCategoria(CategoriaModelo categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return "OrcamentoModelo [id=" + id + ", valor=" + valor + "]";
    }
}

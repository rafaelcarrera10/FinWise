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
@Table(name = "Orcamento")
public class OrcamentoModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor", nullable = false)
    @NotNull(message = "valor não pode ser nulo")
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "planoOrcamentario_id", nullable = false)
    private PlanoOrcamentarioModelo plano;

    @OneToOne(mappedBy = "Orcamento")
    private CategoriaModelo categoria;

    public OrcamentoModelo() {
    }

    public OrcamentoModelo(Integer id, @NotNull(message = "valor não pode ser nulo") Double valor) {
        this.id = id;
        this.valor = valor;
    }

    public OrcamentoModelo(Integer id, @NotNull(message = "valor não pode ser nulo") Double valor,
            PlanoOrcamentarioModelo plano, CategoriaModelo categoria) {
        this.id = id;
        this.valor = valor;
        this.plano = plano;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public Double getValor() {
        return valor;
    }

    public PlanoOrcamentarioModelo getPlano() {
        return plano;
    }

    public CategoriaModelo getCategoria() {
        return categoria;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setPlano(PlanoOrcamentarioModelo plano) {
        this.plano = plano;
    }

    public void setCategoria(CategoriaModelo categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "OrcamentoModelo [id=" + id + ", valor=" + valor + ", plano=" + plano + ", categoria=" + categoria + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((valor == null) ? 0 : valor.hashCode());
        result = prime * result + ((plano == null) ? 0 : plano.hashCode());
        result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrcamentoModelo other = (OrcamentoModelo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (valor == null) {
            if (other.valor != null)
                return false;
        } else if (!valor.equals(other.valor))
            return false;
        if (plano == null) {
            if (other.plano != null)
                return false;
        } else if (!plano.equals(other.plano))
            return false;
        if (categoria == null) {
            if (other.categoria != null)
                return false;
        } else if (!categoria.equals(other.categoria))
            return false;
        return true;
    }

    
    
}

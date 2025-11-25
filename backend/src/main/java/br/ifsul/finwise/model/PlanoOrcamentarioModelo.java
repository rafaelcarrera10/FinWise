package br.ifsul.finwise.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "PlanoOrcamentario")
public class PlanoOrcamentarioModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "condicao", nullable = false)
    @NotNull(message = "condicao não pode ser nulo")
    private Boolean condicao;

    @Column(name = "nome", nullable = false)
    @NotNull(message = "nome nao pode ser nulo")
    private String nome;

    @Column(name = "valorGastar", nullable = false)
    @NotNull(message = "valor para gastar não pode ser nulo")
    private Double valorGastar;

    @Column(name = "valorGuardar", nullable = false)
    @NotNull(message = "valor para guardar não pode ser nulo")
    private Double valorGuardar;

    @Column(name = "valor", nullable = false)
    @NotNull(message = "valor não pode ser nulo")
    private Double valor;

    @Column(name = "descricao", nullable = false)
    @NotNull(message = "descricao não pode ser nula")
    private String descricao;

    @Column(name = "dataFinal", nullable = false)
    @NotNull(message = "dataFinal não pode ser nula")
    private Date dataFinal;

    @Column(name = "dataInicial", nullable = false)
    @NotNull(message = "dataInicial não pode ser nula")
    private Date dataInicial;

    @OneToMany(mappedBy = "PlanoOrcamentario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrcamentoModelo> listaOrcamento = new ArrayList<>();

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "contaFinanceira_id")
    private ContaFinanceiraModelo conta;

    public PlanoOrcamentarioModelo() {
    }

    public PlanoOrcamentarioModelo(Integer id, @NotNull(message = "condicao não pode ser nulo") Boolean condicao,
            @NotNull(message = "nome nao pode ser nulo") String nome,
            @NotNull(message = "valor para gastar não pode ser nulo") Double valorGastar,
            @NotNull(message = "valor para guardar não pode ser nulo") Double valorGuardar,
            @NotNull(message = "valor não pode ser nulo") Double valor,
            @NotNull(message = "descricao não pode ser nula") String descricao,
            @NotNull(message = "dataFinal não pode ser nula") Date dataFinal,
            @NotNull(message = "dataInicial não pode ser nula") Date dataInicial) {
        this.id = id;
        this.condicao = condicao;
        this.nome = nome;
        this.valorGastar = valorGastar;
        this.valorGuardar = valorGuardar;
        this.valor = valor;
        this.descricao = descricao;
        this.dataFinal = dataFinal;
        this.dataInicial = dataInicial;
    }

    public PlanoOrcamentarioModelo(Integer id, @NotNull(message = "condicao não pode ser nulo") Boolean condicao,
            @NotNull(message = "nome nao pode ser nulo") String nome,
            @NotNull(message = "valor para gastar não pode ser nulo") Double valorGastar,
            @NotNull(message = "valor para guardar não pode ser nulo") Double valorGuardar,
            @NotNull(message = "valor não pode ser nulo") Double valor,
            @NotNull(message = "descricao não pode ser nula") String descricao,
            @NotNull(message = "dataFinal não pode ser nula") Date dataFinal,
            @NotNull(message = "dataInicial não pode ser nula") Date dataInicial, List<OrcamentoModelo> listaOrcamento,
            ContaFinanceiraModelo conta) {
        this.id = id;
        this.condicao = condicao;
        this.nome = nome;
        this.valorGastar = valorGastar;
        this.valorGuardar = valorGuardar;
        this.valor = valor;
        this.descricao = descricao;
        this.dataFinal = dataFinal;
        this.dataInicial = dataInicial;
        this.listaOrcamento = listaOrcamento;
        this.conta = conta;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getCondicao() {
        return condicao;
    }

    public String getNome() {
        return nome;
    }

    public Double getValorGastar() {
        return valorGastar;
    }

    public Double getValorGuardar() {
        return valorGuardar;
    }

    public Double getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public List<OrcamentoModelo> getListaOrcamento() {
        return listaOrcamento;
    }

    public ContaFinanceiraModelo getConta() {
        return conta;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCondicao(Boolean condicao) {
        this.condicao = condicao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValorGastar(Double valorGastar) {
        this.valorGastar = valorGastar;
    }

    public void setValorGuardar(Double valorGuardar) {
        this.valorGuardar = valorGuardar;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public void setListaOrcamento(List<OrcamentoModelo> listaOrcamento) {
        this.listaOrcamento = listaOrcamento;
    }

    public void setConta(ContaFinanceiraModelo conta) {
        this.conta = conta;
    }

    public boolean ultrapassouOrcamento() {
        if (listaOrcamento == null || listaOrcamento.isEmpty()) {
        return false; // Nada foi gasto ainda
    }

        double soma = listaOrcamento.stream()
        .mapToDouble(OrcamentoModelo::getValor)
        .sum();

        return soma > this.valorGastar;
}
    public double calcularSaldoOrcamento() {
        double soma = listaOrcamento.stream()
        .mapToDouble(OrcamentoModelo::getValor)
        .sum();

        return this.valorGastar - soma; 
        // se negativo, significa que ultrapassou
}
    public boolean dataInvalida() {
        if (dataInicial == null || dataFinal == null) {
        return false; // ou lançar exceção, dependendo da regra de negócio
    }
        return dataInicial.after(dataFinal);
}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((condicao == null) ? 0 : condicao.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((valorGastar == null) ? 0 : valorGastar.hashCode());
        result = prime * result + ((valorGuardar == null) ? 0 : valorGuardar.hashCode());
        result = prime * result + ((valor == null) ? 0 : valor.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((dataFinal == null) ? 0 : dataFinal.hashCode());
        result = prime * result + ((dataInicial == null) ? 0 : dataInicial.hashCode());
        result = prime * result + ((listaOrcamento == null) ? 0 : listaOrcamento.hashCode());
        result = prime * result + ((conta == null) ? 0 : conta.hashCode());
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
        PlanoOrcamentarioModelo other = (PlanoOrcamentarioModelo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (condicao == null) {
            if (other.condicao != null)
                return false;
        } else if (!condicao.equals(other.condicao))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (valorGastar == null) {
            if (other.valorGastar != null)
                return false;
        } else if (!valorGastar.equals(other.valorGastar))
            return false;
        if (valorGuardar == null) {
            if (other.valorGuardar != null)
                return false;
        } else if (!valorGuardar.equals(other.valorGuardar))
            return false;
        if (valor == null) {
            if (other.valor != null)
                return false;
        } else if (!valor.equals(other.valor))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (dataFinal == null) {
            if (other.dataFinal != null)
                return false;
        } else if (!dataFinal.equals(other.dataFinal))
            return false;
        if (dataInicial == null) {
            if (other.dataInicial != null)
                return false;
        } else if (!dataInicial.equals(other.dataInicial))
            return false;
        if (listaOrcamento == null) {
            if (other.listaOrcamento != null)
                return false;
        } else if (!listaOrcamento.equals(other.listaOrcamento))
            return false;
        if (conta == null) {
            if (other.conta != null)
                return false;
        } else if (!conta.equals(other.conta))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PlanoOrcamentarioModelo [id=" + id + ", condicao=" + condicao + ", nome=" + nome + ", valorGastar="
                + valorGastar + ", valorGuardar=" + valorGuardar + ", valor=" + valor + ", descricao=" + descricao
                + ", dataFinal=" + dataFinal + ", dataInicial=" + dataInicial + ", listaOrcamento=" + listaOrcamento
                + ", conta=" + conta + "]";
    }
    

    
}

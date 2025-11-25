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
@Table(name = "plano_orcamentario")
public class PlanoOrcamentarioModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "condicao", nullable = false)
    @NotNull(message = "condicao não pode ser nulo")
    private Boolean condicao;

    @Column(name = "nome", nullable = false)
    @NotNull(message = "nome não pode ser nulo")
    private String nome;

    @Column(name = "valor_gastar", nullable = false)
    @NotNull(message = "valor para gastar não pode ser nulo")
    private Double valorGastar;

    @Column(name = "valor_guardar", nullable = false)
    @NotNull(message = "valor para guardar não pode ser nulo")
    private Double valorGuardar;

    @Column(name = "valor", nullable = false)
    @NotNull(message = "valor não pode ser nulo")
    private Double valor;

    @Column(name = "descricao", nullable = false)
    @NotNull(message = "descricao não pode ser nula")
    private String descricao;

    @Column(name = "data_final", nullable = false)
    @NotNull(message = "dataFinal não pode ser nula")
    private Date dataFinal;

    @Column(name = "data_inicial", nullable = false)
    @NotNull(message = "dataInicial não pode ser nula")
    private Date dataInicial;

    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrcamentoModelo> listaOrcamento = new ArrayList<>();

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "conta_financeira_id")
    private ContaFinanceiraModelo contaFinanceira;

    public PlanoOrcamentarioModelo() {}

    // Construtores, Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Boolean getCondicao() { return condicao; }
    public void setCondicao(Boolean condicao) { this.condicao = condicao; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getValorGastar() { return valorGastar; }
    public void setValorGastar(Double valorGastar) { this.valorGastar = valorGastar; }

    public Double getValorGuardar() { return valorGuardar; }
    public void setValorGuardar(Double valorGuardar) { this.valorGuardar = valorGuardar; }

    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Date getDataFinal() { return dataFinal; }
    public void setDataFinal(Date dataFinal) { this.dataFinal = dataFinal; }

    public Date getDataInicial() { return dataInicial; }
    public void setDataInicial(Date dataInicial) { this.dataInicial = dataInicial; }

    public List<OrcamentoModelo> getListaOrcamento() { return listaOrcamento; }
    public void setListaOrcamento(List<OrcamentoModelo> listaOrcamento) { this.listaOrcamento = listaOrcamento; }

    public ContaFinanceiraModelo getContaFinanceira() { return contaFinanceira; }
    public void setContaFinanceira(ContaFinanceiraModelo contaFinanceira) { this.contaFinanceira = contaFinanceira; }

    // Métodos auxiliares
    public boolean ultrapassouOrcamento() {
        if (listaOrcamento == null || listaOrcamento.isEmpty()) return false;
        double soma = listaOrcamento.stream().mapToDouble(OrcamentoModelo::getValor).sum();
        return soma > this.valorGastar;
    }

    public double calcularSaldoOrcamento() {
        double soma = listaOrcamento.stream().mapToDouble(OrcamentoModelo::getValor).sum();
        return this.valorGastar - soma;
    }

    public boolean dataInvalida() {
        if (dataInicial == null || dataFinal == null) return false;
        return dataInicial.after(dataFinal);
    }

    @Override
    public String toString() {
        return "PlanoOrcamentarioModelo [id=" + id + ", nome=" + nome + "]";
    }
}

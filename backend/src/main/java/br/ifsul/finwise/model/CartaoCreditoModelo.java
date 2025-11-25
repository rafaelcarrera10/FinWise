package br.ifsul.finwise.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "CartaoCredito")
public class CartaoCreditoModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vencimento", nullable = false)
    @NotNull(message = "Dia de vencimento não pode ser nulo")
    private Date diaVencimento;

    @Column(name = "fechamento", nullable = false)
    @NotNull(message = "Dia de fechamento não pode ser nulo")
    private Date diaFechamento;

    @Column(name = "descricao", nullable = false)
    @NotNull(message = "A descricao não pode ser nula")
    private String descricao;

    @Column(name = "faturaAtual", nullable = false)
    @NotNull(message = "Fatura não pode ser nula")
    private Double faturaAtual;

    @Column(name = "faturaSeguinte", nullable = false)
    @NotNull(message = "Fatura seguinte não pode ser nula")
    private Double faturaSeguinte;

    // Relacionamento OneToMany com DespesaModelo
    @OneToMany(mappedBy = "cartaoCredito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DespesaModelo> listaDespesa = new ArrayList<>();

    // Relacionamento OneToOne com ContaFinanceiraModelo (bidirecional)
    @OneToOne(mappedBy = "cartao", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private ContaFinanceiraModelo conta;

    public CartaoCreditoModelo() {
    }

    public CartaoCreditoModelo(Integer id, Date diaVencimento, Date diaFechamento, String descricao, Double faturaAtual, Double faturaSeguinte) {
        this.id = id;
        this.diaVencimento = diaVencimento;
        this.diaFechamento = diaFechamento;
        this.descricao = descricao;
        this.faturaAtual = faturaAtual;
        this.faturaSeguinte = faturaSeguinte;
    }

    public CartaoCreditoModelo(Integer id, Date diaVencimento, Date diaFechamento, String descricao, Double faturaAtual, Double faturaSeguinte,
                               List<DespesaModelo> listaDespesa, ContaFinanceiraModelo conta) {
        this(id, diaVencimento, diaFechamento, descricao, faturaAtual, faturaSeguinte);
        this.listaDespesa = listaDespesa;
        this.conta = conta;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Date getDiaVencimento() { return diaVencimento; }
    public void setDiaVencimento(Date diaVencimento) { this.diaVencimento = diaVencimento; }

    public Date getDiaFechamento() { return diaFechamento; }
    public void setDiaFechamento(Date diaFechamento) { this.diaFechamento = diaFechamento; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getFaturaAtual() { return faturaAtual; }
    public void setFaturaAtual(Double faturaAtual) { this.faturaAtual = faturaAtual; }

    public Double getFaturaSeguinte() { return faturaSeguinte; }
    public void setFaturaSeguinte(Double faturaSeguinte) { this.faturaSeguinte = faturaSeguinte; }

    public List<DespesaModelo> getListaDespesa() { return listaDespesa; }
    public void setListaDespesa(List<DespesaModelo> listaDespesa) { this.listaDespesa = listaDespesa; }

    public ContaFinanceiraModelo getConta() { return conta; }
    public void setConta(ContaFinanceiraModelo conta) { this.conta = conta; }

    // Método auxiliar para calcular fatura
    public void calcularFatura(List<DespesaModelo> despesas) {
        double total = 0.0;
        for (DespesaModelo d : despesas) {
            total += d.getValor();
        }
        this.faturaAtual = total;
    }

    @Override
    public String toString() {
        return "CartaoCreditoModelo [id=" + id + ", diaVencimento=" + diaVencimento + ", diaFechamento=" + diaFechamento
                + ", descricao=" + descricao + ", faturaAtual=" + faturaAtual + ", faturaSeguinte=" + faturaSeguinte
                + ", listaDespesa=" + listaDespesa + ", conta=" + conta + "]";
    }
}

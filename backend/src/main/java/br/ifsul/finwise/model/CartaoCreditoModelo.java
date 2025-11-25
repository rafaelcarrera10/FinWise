package br.ifsul.finwise.model;


import java.math.BigDecimal;
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

    @OneToMany(mappedBy = "CartaoCredito", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DespesaModelo> listaDespesa = new ArrayList<>();

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "contaFinanceira_id")
    private ContaFinanceiraModelo conta;

    public CartaoCreditoModelo() {
    }

    public CartaoCreditoModelo(Integer id, @NotNull(message = "Dia de vencimento não pode ser nulo") Date diaVencimento,
            @NotNull(message = "Dia de fechamento não pode ser nulo") Date diaFechamento,
            @NotNull(message = "A descricao não pode ser nula") String descricao,
            @NotNull(message = "Fatura não pode ser nula") Double faturaAtual,
            @NotNull(message = "Fatura seguinte não pode ser nula") Double faturaSeguinte) {
        this.id = id;
        this.diaVencimento = diaVencimento;
        this.diaFechamento = diaFechamento;
        this.descricao = descricao;
        this.faturaAtual = faturaAtual;
        this.faturaSeguinte = faturaSeguinte;
    }

    public CartaoCreditoModelo(Integer id, @NotNull(message = "Dia de vencimento não pode ser nulo") Date diaVencimento,
            @NotNull(message = "Dia de fechamento não pode ser nulo") Date diaFechamento,
            @NotNull(message = "A descricao não pode ser nula") String descricao,
            @NotNull(message = "Fatura não pode ser nula") Double faturaAtual,
            @NotNull(message = "Fatura seguinte não pode ser nula") Double faturaSeguinte,
            List<DespesaModelo> listaDespesa, ContaFinanceiraModelo conta) {
        this.id = id;
        this.diaVencimento = diaVencimento;
        this.diaFechamento = diaFechamento;
        this.descricao = descricao;
        this.faturaAtual = faturaAtual;
        this.faturaSeguinte = faturaSeguinte;
        this.listaDespesa = listaDespesa;
        this.conta = conta;
    }

    public Integer getId() {
        return id;
    }

    public Date getDiaVencimento() {
        return diaVencimento;
    }

    public Date getDiaFechamento() {
        return diaFechamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public Double getFaturaAtual() {
        return faturaAtual;
    }

    public Double getFaturaSeguinte() {
        return faturaSeguinte;
    }

    public List<DespesaModelo> getListaDespesa() {
        return listaDespesa;
    }

    public ContaFinanceiraModelo getConta() {
        return conta;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDiaVencimento(Date diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public void setDiaFechamento(Date diaFechamento) {
        this.diaFechamento = diaFechamento;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setFaturaAtual(Double faturaAtual) {
        this.faturaAtual = faturaAtual;
    }

    public void setFaturaSeguinte(Double faturaSeguinte) {
        this.faturaSeguinte = faturaSeguinte;
    }

    public void setListaDespesa(List<DespesaModelo> listaDespesa) {
        this.listaDespesa = listaDespesa;
    }

    public void setConta(ContaFinanceiraModelo conta) {
        this.conta = conta;
    }

    public void calcularFatura(List<DespesaModelo> despesas) {

    double total = 0.0;

    for (DespesaModelo d : despesas) {
        total += d.getValor();
    }

    this.faturaAtual = total;
}


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((diaVencimento == null) ? 0 : diaVencimento.hashCode());
        result = prime * result + ((diaFechamento == null) ? 0 : diaFechamento.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((faturaAtual == null) ? 0 : faturaAtual.hashCode());
        result = prime * result + ((faturaSeguinte == null) ? 0 : faturaSeguinte.hashCode());
        result = prime * result + ((listaDespesa == null) ? 0 : listaDespesa.hashCode());
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
        CartaoCreditoModelo other = (CartaoCreditoModelo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (diaVencimento == null) {
            if (other.diaVencimento != null)
                return false;
        } else if (!diaVencimento.equals(other.diaVencimento))
            return false;
        if (diaFechamento == null) {
            if (other.diaFechamento != null)
                return false;
        } else if (!diaFechamento.equals(other.diaFechamento))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (faturaAtual == null) {
            if (other.faturaAtual != null)
                return false;
        } else if (!faturaAtual.equals(other.faturaAtual))
            return false;
        if (faturaSeguinte == null) {
            if (other.faturaSeguinte != null)
                return false;
        } else if (!faturaSeguinte.equals(other.faturaSeguinte))
            return false;
        if (listaDespesa == null) {
            if (other.listaDespesa != null)
                return false;
        } else if (!listaDespesa.equals(other.listaDespesa))
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
        return "CartaoCreditoModelo [id=" + id + ", diaVencimento=" + diaVencimento + ", diaFechamento=" + diaFechamento
                + ", descricao=" + descricao + ", faturaAtual=" + faturaAtual + ", faturaSeguinte=" + faturaSeguinte
                + ", listaDespesa=" + listaDespesa + ", conta=" + conta + "]";
    }

    

}

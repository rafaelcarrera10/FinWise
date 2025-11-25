package br.ifsul.finwise.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Transacao")
public abstract class TransacaoModelo {

    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "valor", nullable = false)
    @NotNull(message = "valor não pode ser nulo")
    private Double valor;

    @Column(name = "data_inicial", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "dataInicial não pode ser nula")
    private Date dataInicial;

    @Column(name = "data_final", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dataFinal;

    @Column(name = "descricao", nullable = false, length = 255)
    @NotNull(message = "descricao não pode ser nula")
    private String descricao;

    @Column(name = "observacao", nullable = true, length = 500)
    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "repeticao", nullable = false)
    @NotNull(message = "repeticao não pode ser nulo")
    private RepeticaoEnum repeticao;

     // Relacionamentos

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaModelo categoria;

    @ManyToOne
    @JoinColumn(name = "contaFinanceira_id", nullable = false)
    private ContaFinanceiraModelo conta;

    public TransacaoModelo() {
    }

    public TransacaoModelo(Integer id, @NotNull(message = "valor não pode ser nulo") Double valor,
            @NotNull(message = "dataInicial não pode ser nula") Date dataInicial, Date dataFinal,
            @NotNull(message = "descricao não pode ser nula") String descricao, String observacao,
            @NotNull(message = "repeticao não pode ser nulo") RepeticaoEnum repeticao) {
        this.id = id;
        this.valor = valor;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.descricao = descricao;
        this.observacao = observacao;
        this.repeticao = repeticao;
    }

    public TransacaoModelo(Integer id, @NotNull(message = "valor não pode ser nulo") Double valor,
            @NotNull(message = "dataInicial não pode ser nula") Date dataInicial, Date dataFinal,
            @NotNull(message = "descricao não pode ser nula") String descricao, String observacao,
            @NotNull(message = "repeticao não pode ser nulo") RepeticaoEnum repeticao, CategoriaModelo categoria,
            ContaFinanceiraModelo conta) {
        this.id = id;
        this.valor = valor;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.descricao = descricao;
        this.observacao = observacao;
        this.repeticao = repeticao;
        this.categoria = categoria;
        this.conta = conta;
    }

    public Integer getId() {
        return id;
    }

    public Double getValor() {
        return valor;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public RepeticaoEnum getRepeticao() {
        return repeticao;
    }

    public CategoriaModelo getCategoria() {
        return categoria;
    }

    public ContaFinanceiraModelo getConta() {
        return conta;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setRepeticao(RepeticaoEnum repeticao) {
        this.repeticao = repeticao;
    }

    public void setCategoria(CategoriaModelo categoria) {
        this.categoria = categoria;
    }

    public void setConta(ContaFinanceiraModelo conta) {
        this.conta = conta;
    }

    /**
     * Valida regras básicas da transação.
     * @return true se estiver válida, false caso contrário
     */
    public boolean isValida() {
        if (valor == null || valor <= 0) return false;
        if (descricao == null || descricao.isBlank()) return false;
        if (repeticao == null) return false;
        if (dataInicial == null) return false;

        // despesa única deve ter dataFinal = null ou igual a dataInicial
        if (repeticao == RepeticaoEnum.UNICA) {
            if (dataFinal != null && !dataFinal.equals(dataInicial)) {
                return false;
            }
        }

        if (dataFinal != null && dataFinal.before(dataInicial)) {
            return false;
        }

        return true;
    }

    /**
     * Verifica se a despesa ainda está ativa (não ultrapassou a data final).
     * @return true se a despesa está vigente, false se já passou
     */
    public boolean estaAtiva() {
        if (dataFinal == null) return true; // despesa única ou sem fim definido

        LocalDate hoje = LocalDate.now();
        LocalDate fim = dataFinal.toLocalDate();

        return !hoje.isAfter(fim);
    }

    @Override
    public String toString() {
        return "TransacaoModelo [id=" + id + ", valor=" + valor + ", dataInicial=" + dataInicial + ", dataFinal="
                + dataFinal + ", descricao=" + descricao + ", observacao=" + observacao + ", repeticao=" + repeticao
                + ", categoria=" + categoria + ", conta=" + conta + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((valor == null) ? 0 : valor.hashCode());
        result = prime * result + ((dataInicial == null) ? 0 : dataInicial.hashCode());
        result = prime * result + ((dataFinal == null) ? 0 : dataFinal.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
        result = prime * result + ((repeticao == null) ? 0 : repeticao.hashCode());
        result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
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
        TransacaoModelo other = (TransacaoModelo) obj;
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
        if (dataInicial == null) {
            if (other.dataInicial != null)
                return false;
        } else if (!dataInicial.equals(other.dataInicial))
            return false;
        if (dataFinal == null) {
            if (other.dataFinal != null)
                return false;
        } else if (!dataFinal.equals(other.dataFinal))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (observacao == null) {
            if (other.observacao != null)
                return false;
        } else if (!observacao.equals(other.observacao))
            return false;
        if (repeticao != other.repeticao)
            return false;
        if (categoria == null) {
            if (other.categoria != null)
                return false;
        } else if (!categoria.equals(other.categoria))
            return false;
        if (conta == null) {
            if (other.conta != null)
                return false;
        } else if (!conta.equals(other.conta))
            return false;
        return true;
    }

    
}

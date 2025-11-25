package br.ifsul.finwise.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Transacao")
public abstract class TransacaoModelo {

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

    @Column(name = "data_final")
    @Temporal(TemporalType.DATE)
    private Date dataFinal;

    @Column(name = "descricao", nullable = false, length = 255)
    @NotNull(message = "descricao não pode ser nula")
    private String descricao;

    @Column(name = "observacao", length = 500)
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
    @JoinColumn(name = "conta_id", nullable = false)
    private ContaFinanceiraModelo conta;

    public TransacaoModelo() {}

    public TransacaoModelo(Integer id, Double valor, Date dataInicial, Date dataFinal,
        String descricao, String observacao, RepeticaoEnum repeticao) {
        this.id = id;
        this.valor = valor;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.descricao = descricao;
        this.observacao = observacao;
        this.repeticao = repeticao;
    }


    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public Date getDataInicial() { return dataInicial; }
    public void setDataInicial(Date dataInicial) { this.dataInicial = dataInicial; }
    public Date getDataFinal() { return dataFinal; }
    public void setDataFinal(Date dataFinal) { this.dataFinal = dataFinal; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public RepeticaoEnum getRepeticao() { return repeticao; }
    public void setRepeticao(RepeticaoEnum repeticao) { this.repeticao = repeticao; }
    public CategoriaModelo getCategoria() { return categoria; }
    public void setCategoria(CategoriaModelo categoria) { this.categoria = categoria; }
    public ContaFinanceiraModelo getConta() { return conta; }
    public void setConta(ContaFinanceiraModelo conta) { this.conta = conta; }

    public boolean isValida() {
        if (valor == null || valor <= 0) return false;
        if (descricao == null || descricao.isBlank()) return false;
        if (repeticao == null) return false;
        if (dataInicial == null) return false;
        if (repeticao == RepeticaoEnum.UNICA) {
            if (dataFinal != null && !dataFinal.equals(dataInicial)) return false;
        }
        if (dataFinal != null && dataFinal.before(dataInicial)) return false;
        return true;
    }

    public boolean estaAtiva() {
        if (dataFinal == null) return true;
        LocalDate hoje = LocalDate.now();
        LocalDate fim = dataFinal.toLocalDate();
        return !hoje.isAfter(fim);
    }
}

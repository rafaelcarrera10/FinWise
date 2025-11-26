package br.ifsul.finwise.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "contaFinanceira")
public class ContaFinanceiraModelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    @NotNull(message = "Nome não pode ser nulo")
    private String nome;

    @Column(name = "saldo_atual", nullable = false)
    @NotNull(message = "Saldo atual não pode ser nulo")
    private Double saldoAtual;

    @Column(name = "saldo_previsto")
    private Double saldoPrevisto;

    @Column(name = "receita_total")
    private Double receitaTotal;

    @Column(name = "despesa_total")
    private Double despesaTotal;

    // Relacionamentos
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JsonManagedReference
    @JoinColumn(name = "usuario_id")
    private UsuarioModelo usuario;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransacaoModelo> listaTransacao = new ArrayList<>();

    @OneToOne(mappedBy = "conta", cascade = CascadeType.ALL)
    private CartaoCreditoModelo cartao;

    @OneToOne(mappedBy = "contaFinanceira")
    private PlanoOrcamentarioModelo plano;

    public ContaFinanceiraModelo() {}

    public ContaFinanceiraModelo(Integer id, String nome, Double saldoAtual, Double saldoPrevisto,
                                 Double receitaTotal, Double despesaTotal) {
        this.id = id;
        this.nome = nome;
        this.saldoAtual = saldoAtual;
        this.saldoPrevisto = saldoPrevisto;
        this.receitaTotal = receitaTotal;
        this.despesaTotal = despesaTotal;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Double getSaldoAtual() { return saldoAtual; }
    public void setSaldoAtual(Double saldoAtual) { this.saldoAtual = saldoAtual; }
    public Double getSaldoPrevisto() { return saldoPrevisto; }
    public void setSaldoPrevisto(Double saldoPrevisto) { this.saldoPrevisto = saldoPrevisto; }
    public Double getReceitaTotal() { return receitaTotal; }
    public void setReceitaTotal(Double receitaTotal) { this.receitaTotal = receitaTotal; }
    public Double getDespesaTotal() { return despesaTotal; }
    public void setDespesaTotal(Double despesaTotal) { this.despesaTotal = despesaTotal; }
    public UsuarioModelo getUsuario() { return usuario; }
    public void setUsuario(UsuarioModelo usuario) { this.usuario = usuario; }
    public List<TransacaoModelo> getListaTransacao() { return listaTransacao; }
    public void setListaTransacao(List<TransacaoModelo> listaTransacao) { this.listaTransacao = listaTransacao; }
    public CartaoCreditoModelo getCartao() { return cartao; }
    public void setCartao(CartaoCreditoModelo cartao) { this.cartao = cartao; }
    public PlanoOrcamentarioModelo getPlano() { return plano; }
    public void setPlano(PlanoOrcamentarioModelo plano) { this.plano = plano; }

    public void calcularTotais(List<TransacaoModelo> transacoes) {
        double totalReceitas = 0.0;
        double totalDespesas = 0.0;

        for (TransacaoModelo t : transacoes) {
            if (t instanceof ReceitaModelo) {
                totalReceitas += t.getValor();
            } else if (t instanceof DespesaModelo) {
                totalDespesas += t.getValor();
            }
        }

        this.receitaTotal = totalReceitas;
        this.despesaTotal = totalDespesas;
    }

}

package br.ifsul.finwise.model;

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

    @Column(name = "saldo_previsto", nullable = true)
    private Double saldoPrevisto;

    @Column(name = "receita_total", nullable = true)
    private Double receitaTotal;

    @Column(name = "despesaTotal", nullable = true)
    private Double despesaTotal;

    // Relacionamentos

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "usuario_id")
    private UsuarioModelo usuario;

    @OneToMany(mappedBy = "contaFinanceira", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransacaoModelo> listaTransacao = new ArrayList<>();

    @OneToOne(mappedBy = "contaFinanceira")
    private CartaoCreditoModelo cartao;

    @OneToOne(mappedBy = "contaFinanceira")
    private PlanoOrcamentarioModelo plano;

    public ContaFinanceiraModelo() {
    }

    public ContaFinanceiraModelo(Integer id, @NotNull(message = "Nome não pode ser nulo") String nome,
            @NotNull(message = "Saldo atual não pode ser nulo") Double saldoAtual, Double saldoPrevisto,
            Double receitaTotal, Double despesaTotal) {
        this.id = id;
        this.nome = nome;
        this.saldoAtual = saldoAtual;
        this.saldoPrevisto = saldoPrevisto;
        this.receitaTotal = receitaTotal;
        this.despesaTotal = despesaTotal;
    }

    public ContaFinanceiraModelo(Integer id, @NotNull(message = "Nome não pode ser nulo") String nome,
            @NotNull(message = "Saldo atual não pode ser nulo") Double saldoAtual, Double saldoPrevisto,
            Double receitaTotal, Double despesaTotal, UsuarioModelo usuario, List<TransacaoModelo> listaTransacao,
            CartaoCreditoModelo cartao, PlanoOrcamentarioModelo plano) {
        this.id = id;
        this.nome = nome;
        this.saldoAtual = saldoAtual;
        this.saldoPrevisto = saldoPrevisto;
        this.receitaTotal = receitaTotal;
        this.despesaTotal = despesaTotal;
        this.usuario = usuario;
        this.listaTransacao = listaTransacao;
        this.cartao = cartao;
        this.plano = plano;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Double getSaldoAtual() {
        return saldoAtual;
    }

    public Double getSaldoPrevisto() {
        return saldoPrevisto;
    }

    public Double getReceitaTotal() {
        return receitaTotal;
    }

    public Double getDespesaTotal() {
        return despesaTotal;
    }

    public UsuarioModelo getUsuario() {
        return usuario;
    }

    public List<TransacaoModelo> getListaTransacao() {
        return listaTransacao;
    }

    public CartaoCreditoModelo getCartao() {
        return cartao;
    }

    public PlanoOrcamentarioModelo getPlano() {
        return plano;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSaldoAtual(Double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public void setSaldoPrevisto(Double saldoPrevisto) {
        this.saldoPrevisto = saldoPrevisto;
    }

    public void setReceitaTotal(Double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public void setDespesaTotal(Double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }

    public void setUsuario(UsuarioModelo usuario) {
        this.usuario = usuario;
    }

    public void setListaTransacao(List<TransacaoModelo> listaTransacao) {
        this.listaTransacao = listaTransacao;
    }

    public void setCartao(CartaoCreditoModelo cartao) {
        this.cartao = cartao;
    }

    public void setPlano(PlanoOrcamentarioModelo plano) {
        this.plano = plano;
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((saldoAtual == null) ? 0 : saldoAtual.hashCode());
        result = prime * result + ((saldoPrevisto == null) ? 0 : saldoPrevisto.hashCode());
        result = prime * result + ((receitaTotal == null) ? 0 : receitaTotal.hashCode());
        result = prime * result + ((despesaTotal == null) ? 0 : despesaTotal.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result + ((listaTransacao == null) ? 0 : listaTransacao.hashCode());
        result = prime * result + ((cartao == null) ? 0 : cartao.hashCode());
        result = prime * result + ((plano == null) ? 0 : plano.hashCode());
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
        ContaFinanceiraModelo other = (ContaFinanceiraModelo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (saldoAtual == null) {
            if (other.saldoAtual != null)
                return false;
        } else if (!saldoAtual.equals(other.saldoAtual))
            return false;
        if (saldoPrevisto == null) {
            if (other.saldoPrevisto != null)
                return false;
        } else if (!saldoPrevisto.equals(other.saldoPrevisto))
            return false;
        if (receitaTotal == null) {
            if (other.receitaTotal != null)
                return false;
        } else if (!receitaTotal.equals(other.receitaTotal))
            return false;
        if (despesaTotal == null) {
            if (other.despesaTotal != null)
                return false;
        } else if (!despesaTotal.equals(other.despesaTotal))
            return false;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        if (listaTransacao == null) {
            if (other.listaTransacao != null)
                return false;
        } else if (!listaTransacao.equals(other.listaTransacao))
            return false;
        if (cartao == null) {
            if (other.cartao != null)
                return false;
        } else if (!cartao.equals(other.cartao))
            return false;
        if (plano == null) {
            if (other.plano != null)
                return false;
        } else if (!plano.equals(other.plano))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ContaFinanceiraModelo [id=" + id + ", nome=" + nome + ", saldoAtual=" + saldoAtual + ", saldoPrevisto="
                + saldoPrevisto + ", receitaTotal=" + receitaTotal + ", despesaTotal=" + despesaTotal + ", usuario="
                + usuario + ", listaTransacao=" + listaTransacao + ", cartao=" + cartao + ", plano=" + plano + "]";
    }

}

    

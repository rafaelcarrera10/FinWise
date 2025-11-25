package br.ifsul.finwise.model;

import java.sql.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Despesa")
public class DespesaModelo extends TransacaoModelo {

    @Column(name = "status", nullable = false)
    @NotNull(message = "Status não pode ser nulo")
    private Boolean status;

    // Relacionamento ManyToOne com CartaoCreditoModelo
    @ManyToOne
    @JoinColumn(name = "cartaoCredito_id", nullable = false)
    private CartaoCreditoModelo cartaoCredito;

    public DespesaModelo() { }

    public DespesaModelo(Integer id, Double valor, Date dataInicial, Date dataFinal, String descricao,
                         String observacao, RepeticaoEnum repeticao, Boolean status) {
        super(id, valor, dataInicial, dataFinal, descricao, observacao, repeticao);
        this.status = status;
    }

    public DespesaModelo(Integer id, Double valor, Date dataInicial, Date dataFinal, String descricao,
                         String observacao, RepeticaoEnum repeticao, CategoriaModelo categoria,
                         ContaFinanceiraModelo conta, Boolean status, CartaoCreditoModelo cartaoCredito) {
        super(id, valor, dataInicial, dataFinal, descricao, observacao, repeticao, categoria, conta);
        this.status = status;
        this.cartaoCredito = cartaoCredito;
    }

    // Getters e Setters
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public CartaoCreditoModelo getCartaoCredito() { return cartaoCredito; }
    public void setCartaoCredito(CartaoCreditoModelo cartaoCredito) { this.cartaoCredito = cartaoCredito; }

    @Override
    public String toString() {
        return "DespesaModelo [status=" + status + ", cartaoCredito=" + cartaoCredito + ", toString()=" + super.toString() + "]";
    }
}

package br.ifsul.finwise.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Transacao")
public class DespesaModelo extends TransacaoModelo {
    
    
    // Atributos

    @Column(name = "status", nullable = false)
    @NotNull(message = "Status não pode ser nulo")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "cartaoCredito_id", nullable = false)
    private CartaoCreditoModelo cartao;

    public DespesaModelo() {
    }

    public DespesaModelo(Integer id, @NotNull(message = "valor não pode ser nulo") Double valor,
            @NotNull(message = "dataInicial não pode ser nula") Date dataInicial, Date dataFinal,
            @NotNull(message = "descricao não pode ser nula") String descricao, String observacao,
            @NotNull(message = "repeticao não pode ser nulo") RepeticaoEnum repeticao,
            @NotNull(message = "Status não pode ser nulo") Boolean status) {
        super(id, valor, dataInicial, dataFinal, descricao, observacao, repeticao);
        this.status = status;
    }

    public DespesaModelo(Integer id, @NotNull(message = "valor não pode ser nulo") Double valor,
            @NotNull(message = "dataInicial não pode ser nula") Date dataInicial, Date dataFinal,
            @NotNull(message = "descricao não pode ser nula") String descricao, String observacao,
            @NotNull(message = "repeticao não pode ser nulo") RepeticaoEnum repeticao, CategoriaModelo categoria,
            ContaFinanceiraModelo conta, @NotNull(message = "Status não pode ser nulo") Boolean status,
            CartaoCreditoModelo cartao) {
        super(id, valor, dataInicial, dataFinal, descricao, observacao, repeticao, categoria, conta);
        this.status = status;
        this.cartao = cartao;
    }

    public Boolean getStatus() {
        return status;
    }

    public CartaoCreditoModelo getCartao() {
        return cartao;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setCartao(CartaoCreditoModelo cartao) {
        this.cartao = cartao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((cartao == null) ? 0 : cartao.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DespesaModelo other = (DespesaModelo) obj;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (cartao == null) {
            if (other.cartao != null)
                return false;
        } else if (!cartao.equals(other.cartao))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "DespesaModelo [status=" + status + ", cartao=" + cartao + ", toString()=" + super.toString() + "]";
    }

    
    
}

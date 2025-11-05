package br.ifsul.finwise.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "transactions")
public class TransactionsModel {

    // Variáveis
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //ID da transação

    @Column(name = "value", precision = 19, scale = 2, nullable = false)
    private BigDecimal value; //Valor da transação

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type; //Tipo da transação

    @Column(name = "description", nullable = false)
    private String description; //Descrição da transação

    // RELACIONAMENTOS
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account", nullable = false)
    private AccountModel account;

    // Construtores
    public TransactionsModel() {}

    public TransactionsModel(BigDecimal value, TransactionType type, String description) {
        this.value = value;
        this.type = type;
        this.description = description;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return null; // Por segurança, não expõe o valor diretamente
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getSecureValue() {
        return this.value; // Método seguro para obter o valor
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }

    // Métodos ToString, HashCode e Equals
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TransactionsModel: id=").append(id != null ? id : "null");
        sb.append(", value=****, type=").append(type != null ? type : "null");
        sb.append(", description=****");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TransactionsModel other = (TransactionsModel) obj;
        return Objects.equals(id, other.id);
    }

    // Enum para o tipo de transação
    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER
    }
}

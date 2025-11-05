package br.ifsul.finwise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Entity
@Table(name = "account")
public class AccountModel {

    // Variáveis
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID da conta

    @Column(name = "number", nullable = false)
    @NotNull
    private String number; // Número da conta

    @Column(name = "balance", precision = 19, scale = 2, nullable = false)
    @NotNull   
    @DecimalMin("0.00")
    private BigDecimal balance; // Saldo da conta

    // Relacionamentos
    @ManyToOne
    @JoinColumn(name = "user")
    private UserModel user; // Referência ao usuário

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionsModel> transactions = new ArrayList<>();

    // Construtores
    public AccountModel() {}

    public AccountModel(@NotNull String number, BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(@NotNull String number) {
        this.number = number;
    }

    public String getSecureNumber() {
        return this.number;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getSecureBalance() {
        return this.balance;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<TransactionsModel> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionsModel> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(TransactionsModel transaction) {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
        transaction.setAccount(this);
    }

    public void removeTransaction(TransactionsModel transaction) {
        if (transactions != null) {
            transactions.remove(transaction);
            transaction.setAccount(null);
        }
    }

    // ToString, HashCode e Equals
    @Override
    public String toString() {
        return "AccountModel: id=" + (id != null ? id : "null") + ", number=****, balance=****";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Não incluir number e balance
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccountModel accountModel = (AccountModel) obj;
        return Objects.equals(id, accountModel.id);
    }

    // Método de remoção genérico (opcional, se realmente necessário)
    public void remove(AccountModel account) {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
}

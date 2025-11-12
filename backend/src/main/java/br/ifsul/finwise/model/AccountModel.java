package br.ifsul.finwise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "account")
public class AccountModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String number;

    @Column(nullable = false, precision = 19, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionsModel> transactions = new ArrayList<>();

    public AccountModel() {}

    public AccountModel(@NotNull String number, BigDecimal balance, UserModel user) {
        this.number = number;
        this.balance = balance;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public UserModel getUser() { return user; }
    public void setUser(UserModel user) { this.user = user; }

    public List<TransactionsModel> getTransactions() { return transactions; }
    public void setTransactions(List<TransactionsModel> transactions) { this.transactions = transactions; }

    public void addTransaction(TransactionsModel transaction) {
        transactions.add(transaction);
        transaction.setAccount(this);
    }

    public void removeTransaction(TransactionsModel transaction) {
        transactions.remove(transaction);
        transaction.setAccount(null);
    }

    @Override
    public String toString() {
        return "AccountModel: id=" + id + ", number=" + number + ", balance=" + balance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccountModel that = (AccountModel) obj;
        return Objects.equals(id, that.id);
    }
}

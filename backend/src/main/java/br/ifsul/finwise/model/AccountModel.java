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
    @DecimalMin(value = "0", inclusive = true)
    private BigDecimal balance;

    @Column(nullable = false, precision = 19, scale = 2)
    @NotNull
    @DecimalMin(value = "0", inclusive = true)
    private BigDecimal reservation;

    @Column(nullable = false, precision = 19, scale = 2)
    @NotNull
    @DecimalMin(value = "0", inclusive = true)
    private BigDecimal leisure;

    @Column(nullable = false, precision = 19, scale = 2)
    @NotNull
    @DecimalMin(value = "0", inclusive = true)
    private BigDecimal limite;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionsModel> transactions = new ArrayList<>();

    public AccountModel() {}

    public AccountModel(@NotNull String number, BigDecimal balance, UserModel user,
                        BigDecimal reservation, BigDecimal leisure, BigDecimal limite) {
        this.number = number;
        this.balance = balance;
        this.user = user;
        this.reservation = reservation;
        this.leisure = leisure;
        this.limite = limite;
    }

    // ID
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Número da conta
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    // Saldo principal
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    // Valor reservado
    public BigDecimal getReservation() { return reservation; }
    public void setReservation(BigDecimal reservation) { this.reservation = reservation; }

    // Valor destinado a lazer
    public BigDecimal getLeisure() { return leisure; }
    public void setLeisure(BigDecimal leisure) { this.leisure = leisure; }

    // Limite de crédito
    public BigDecimal getLimite() { return limite; }
    public void setLimite(BigDecimal limite) { this.limite = limite; }

    // Usuário vinculado à conta
    public UserModel getUser() { return user; }
    public void setUser(UserModel user) { this.user = user; }

    // Lista de transações
    public List<TransactionsModel> getTransactions() { return transactions; }
    public void setTransactions(List<TransactionsModel> transactions) { this.transactions = transactions; }

    // Adiciona uma transação à conta
    public void addTransaction(TransactionsModel transaction) {
        transactions.add(transaction);
        transaction.setAccount(this);
    }

    // Remove uma transação da conta
    public void removeTransaction(TransactionsModel transaction) {
        transactions.remove(transaction);
        transaction.setAccount(null);
    }

    @Override
    public String toString() {
        return "AccountModel{" +
               "id=" + id +
               ", number='" + number + '\'' +
               ", balance=" + balance +
               ", limite=" + limite +
               ", leisure=" + leisure +
               ", reservation=" + reservation +
               '}';
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

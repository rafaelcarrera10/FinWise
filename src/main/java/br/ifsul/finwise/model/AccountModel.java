package br.ifsul.finwise.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import javax.crypto.SecretKey;
import br.ifsul.finwise.service.EncryptionService;

@Entity
@Table(name = "account")
public class AccountModel {
    
    //Variaveis

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //ID da conta
    
    @Column(name = "number", nullable = false)
    private Integer number; //Número da conta

    @Column(name = "balance", precision = 19, scale = 2, nullable = false)
    private BigDecimal balance; //Saldo da conta

    // Relacionamento
    
    /**
     * Relacionamento ManyToOne com UserModel
     * Uma conta podem pertencer a um usuário
     */
    @ManyToOne
    @JoinColumn(name = "user_id") // Relacionamento com o usuário
    private UserModel user; // Referência ao usuário
    
    /**
     * Relacionamento OneToMany com TransactionsModel
     * Uma conta pode ter múltiplas transações
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionsModel> transactions = new ArrayList<>();

    // Chave secreta para a criptografia AES
    private static final SecretKey key;
    
    static {
        try {
            key = EncryptionService.generateKey();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar chave de criptografia", e);
        }
    }

    // Construtores
    public AccountModel() {
    }

    public AccountModel(Integer number, BigDecimal balance) {
        this.number = number;
        this.balance = balance;
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        // NUNCA retornar o número da conta descriptografado por questões de segurança
        return null; // Retorna null por segurança
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
    
    /**
     * Obtém o número da conta de forma segura
     * @return Número da conta descriptografado
     */
    public Integer getSecureNumber() {
        return this.number;
    }

    public BigDecimal getBalance() {
        // NUNCA retornar o saldo descriptografado por questões de segurança
        return null; // Retorna null por segurança
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    /**
     * Obtém o saldo da conta de forma segura
     * @return Saldo da conta
     */
    public BigDecimal getSecureBalance() {
        return this.balance;
    }
    
    // ========== GETTERS E SETTERS DOS RELACIONAMENTOS ==========
    
    /**
     * Obtém o usuário proprietário da conta
     * @return Usuário proprietário
     */
    public UserModel getUser() {
        return user;
    }
    
    /**
     * Define o usuário proprietário da conta
     * @param user Usuário proprietário
     */
    public void setUser(UserModel user) {
        this.user = user;
    }
    
    /**
     * Obtém a lista de transações da conta
     * @return Lista de transações
     */
    public List<TransactionsModel> getTransactions() {
        return transactions;
    }
    
    /**
     * Define a lista de transações da conta
     * @param transactions Lista de transações
     */
    public void setTransactions(List<TransactionsModel> transactions) {
        this.transactions = transactions;
    }
    
    /**
     * Adiciona uma transação à conta
     * @param transaction Transação a ser adicionada
     */
    public void addTransaction(TransactionsModel transaction) {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        transactions.add(transaction);
        transaction.setAccount(this);
    }
    
    /**
     * Remove uma transação da conta
     * @param transaction Transação a ser removida
     */
    public void removeTransaction(TransactionsModel transaction) {
        if (transactions != null) {
            transactions.remove(transaction);
            transaction.setAccount(null);
        }
    }

    // Métodos ToString, HashCode e Equals
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AccountModel: id=").append(id != null ? id : "null");
        sb.append(", number=****, balance=****");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
        // NÃO incluir number e balance por questões de segurança
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AccountModel accountModel = (AccountModel) obj;
        return Objects.equals(id, accountModel.id);
    }

    public void remove(AccountModel account) {
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
}
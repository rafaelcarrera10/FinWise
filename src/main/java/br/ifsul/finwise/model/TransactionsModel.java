package br.ifsul.finwise.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import javax.crypto.SecretKey;
import br.ifsul.finwise.service.EncryptionService;

@Entity
@Table(name = "transactions")
public class TransactionsModel {
    
    //Variaveis

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //ID da transação
    
    @Column(name = "value", precision = 19, scale = 2, nullable = false)
    private BigDecimal value; //Valor da transação

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type; //Tipo da transação (Depósito, Saque, Transferência)
    
    @Column(name = "description", nullable = false)
    private String description; //Descrição da transação

    // RELACIONAMENTOS
    
    /**
     * Relacionamento ManyToOne com AccountModel
     * Múltiplas transações podem pertencer a uma conta
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountModel account;

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
    public TransactionsModel() {
    }

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
        // NUNCA retornar o valor da transação descriptografado por questões de segurança
        return null; // Retorna null por segurança
    }
    
    public void setValue(BigDecimal value) {
        this.value = value;
    }
    
    /**
     * Obtém o valor da transação de forma segura
     * @return Valor da transação
     */
    public BigDecimal getSecureValue() {
        return this.value;
    }

    public TransactionType getType() {
        return type; // Tipo pode ser exposto (não é sensível)
    }
    
    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        // NUNCA retornar a descrição descriptografada por questões de segurança
        return "****"; // Retorna asteriscos por segurança
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Obtém a descrição da transação de forma segura
     * @return Descrição da transação
     */
    public String getSecureDescription() {
        return this.description;
    }
    
    // GETTERS E SETTERS DOS RELACIONAMENTOS 
    
    /**
     * Obtém a conta da transação
     * @return Conta da transação
     */
    public AccountModel getAccount() {
        return account;
    }
    
    /**
     * Define a conta da transação
     * @param account Conta da transação
     */
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
        // NÃO incluir value e description por questões de segurança
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TransactionsModel transactionsModel = (TransactionsModel) obj;
        return Objects.equals(id, transactionsModel.id);
    }

    // Enum para o tipo de transação
    public enum TransactionType {
        DEPOSIT,    // Depósito
        WITHDRAWAL, // Saque
        TRANSFER   // Transferência
    }
}
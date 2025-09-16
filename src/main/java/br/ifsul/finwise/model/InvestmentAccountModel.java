package br.ifsul.finwise.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import javax.crypto.SecretKey;
import br.ifsul.finwise.service.EncryptionService;


@Entity
@Table(name = "investment_account")
public class InvestmentAccountModel {


    //Variaveis
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //ID da conta de investimento

    @Column(name = "action_name", nullable = false)
    private String actionName; //Nome da ação

    @Column(name = "value", precision = 19, scale = 2, nullable = false)
    private BigDecimal value; //Valor da ação

    @Column(name = "quantity", nullable = false)
    private Integer quantity; //Quantidade de ações

    // RELACIONAMENTOS
    
    /**
     * Relacionamento ManyToOne com UserModel
     * Múltiplos investimentos podem pertencer a um usuário
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

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
    public InvestmentAccountModel() {
    }

    public InvestmentAccountModel(String actionName, BigDecimal value, Integer quantity) {
        this.actionName = actionName;
        this.value = value;
        this.quantity = quantity;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActionName() {
        // NUNCA retornar o nome da ação descriptografado por questões de segurança
        return "****"; // Retorna asteriscos por segurança
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
    
    /**
     * Obtém o nome da ação de forma segura
     * @return Nome da ação
     */
    public String getSecureActionName() {
        return this.actionName;
    }

    public BigDecimal getValue() {
        // NUNCA retornar o valor descriptografado por questões de segurança
        return null; // Retorna null por segurança
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
    
    /**
     * Obtém o valor da ação de forma segura
     * @return Valor da ação
     */
    public BigDecimal getSecureValue() {
        return this.value;
    }

    public Integer getQuantity() {
        // NUNCA retornar a quantidade descriptografada por questões de segurança
        return null; // Retorna null por segurança
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Obtém a quantidade de ações de forma segura
     * @return Quantidade de ações
     */
    public Integer getSecureQuantity() {
        return this.quantity;
    }
    
    // ========== GETTERS E SETTERS DOS RELACIONAMENTOS ==========
    
    /**
     * Obtém o usuário proprietário do investimento
     * @return Usuário proprietário
     */
    public UserModel getUser() {
        return user;
    }
    
    /**
     * Define o usuário proprietário do investimento
     * @param user Usuário proprietário
     */
    public void setUser(UserModel user) {
        this.user = user;
    }

    // Métodos ToString, HashCode e Equals
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("InvestmentAccountModel: id=").append(id != null ? id : "null");
        sb.append(", actionName=****, value=****, quantity=****");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
        // NÃO incluir actionName, value e quantity por questões de segurança
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InvestmentAccountModel investmentAccountModel = (InvestmentAccountModel) obj;
        return Objects.equals(id, investmentAccountModel.id);
    }
}
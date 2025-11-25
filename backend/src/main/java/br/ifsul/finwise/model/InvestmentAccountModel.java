package br.ifsul.finwise.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "investment_account")
public class InvestmentAccountModel {

    // -------------------- VARIÁVEIS --------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "action_name", nullable = false)
    private String actionName;

    @Column(name = "value", precision = 19, scale = 2, nullable = false)
    private BigDecimal value;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // -------------------- RELACIONAMENTOS --------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModelo usuario;


    // -------------------- CONSTRUTORES --------------------
    public InvestmentAccountModel() {
    }

    public InvestmentAccountModel(String actionName, BigDecimal value, Integer quantity) {
        this.actionName = actionName;
        this.value = value;
        this.quantity = quantity;
    }

    // -------------------- GETTERS E SETTERS --------------------
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UsuarioModelo getusuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModelo usuario) {
        this.usuario = usuario;
    }

    // -------------------- HASHCODE E EQUALS --------------------
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InvestmentAccountModel other = (InvestmentAccountModel) obj;
        return Objects.equals(id, other.id);
    }

    // -------------------- TOSTRING --------------------
    @Override
    public String toString() {
        return "InvestmentAccountModel: id=" + (id != null ? id : "null") +
               ", actionName=" + (actionName != null ? actionName : "null") +
               ", value=****, quantity=****";
    }
}
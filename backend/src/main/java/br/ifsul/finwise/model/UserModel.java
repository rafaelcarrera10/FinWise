package br.ifsul.finwise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import br.ifsul.finwise.service.ValidationService;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "role" // campo no JSON que indica o tipo
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = StudentModel.class, name = "aluno"),
    @JsonSubTypes.Type(value = TeacherModel.class, name = "professor"),
    @JsonSubTypes.Type(value = AdministratorModel.class, name = "admin")
})

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_user", discriminatorType = DiscriminatorType.STRING)
@Table(name = "user")
public abstract class UserModel {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Nome não pode ser nulo")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @NotNull(message = "Email não pode ser nulo")
    @Email(message = "Email inválido")
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull(message = "Senha não pode ser nula")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String password;

    // RELACIONAMENTOS
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountModel> account = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvestmentAccountModel> investments = new ArrayList<>();

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<ContentModel> contentsModel = new ArrayList<>();

    // Construtores
    public UserModel() {}

    public UserModel(String name, String email, String password) {
        ValidationService.ValidationResult validation = validateUserData(name, email);
        if (!validation.isValid()) {
            throw new IllegalArgumentException("Dados inválidos: " + validation.getErrorMessage());
        }
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) {
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.name = name;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<AccountModel> getAccount() { return account; }
    public void setAccount(List<AccountModel> account) { this.account = account; }
    public void addAccount(AccountModel account) {
        if (this.account == null) this.account = new ArrayList<>();
        this.account.add(account);
        account.setUser(this);
    }
    public void removeAccount(AccountModel account) {
        if (this.account != null) {
            this.account.remove(account);
            account.setUser(null);
        }
    }

    public List<InvestmentAccountModel> getInvestments() { return investments; }
    public void setInvestments(List<InvestmentAccountModel> investments) { this.investments = investments; }
    public void addInvestment(InvestmentAccountModel investment) {
        if (investments == null) investments = new ArrayList<>();
        investments.add(investment);
        investment.setUser(this);
    }
    public void removeInvestment(InvestmentAccountModel investment) {
        if (investments != null) {
            investments.remove(investment);
            investment.setUser(null);
        }
    }

    public boolean verifyPassword(String rawPassword) {
        return rawPassword.equals(this.password);
    }

    // Validações
    private ValidationService.ValidationResult validateUserData(String name, String email) {
        ValidationService validationService = new ValidationService();
        return validationService.validateUserData(name, email);
    }

    private boolean isValidName(String name) {
        ValidationService validationService = new ValidationService();
        return validationService.isValidName(name);
    }

    private boolean isValidEmail(String email) {
        ValidationService validationService = new ValidationService();
        return validationService.isValidEmail(email);
    }

    // ToString, hashCode, equals
    @Override
    public String toString() {
        return "UserModel: id=" + id + ", name=" + name + ", email=" + email + ", password=" + password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserModel other = (UserModel) obj;
        return Objects.equals(id, other.id);
    }
}

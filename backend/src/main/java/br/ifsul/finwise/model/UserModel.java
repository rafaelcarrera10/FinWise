package br.ifsul.finwise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import javax.crypto.SecretKey;
import br.ifsul.finwise.service.EncryptionService;
import br.ifsul.finwise.service.ValidationService;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_user", discriminatorType = DiscriminatorType.STRING)
@Table(name = "user")
public abstract class UserModel {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID do usuário

    @Column(name = "name", nullable = false)
    @NotNull(message = "Nome não pode ser nulo")
    private String name; // Nome do usuário

    @Column(name = "email", nullable = false, unique = true)
    @NotNull(message = "Email não pode ser nulo")
    @Email(message = "Email inválido")
    private String email; // Email do usuário

    @Column(name = "password", nullable = false)
    @NotNull(message = "Senha não pode ser nula")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String password; // Senha do usuário

    // RELACIONAMENTOS
    
    /**
     * Relacionamento OneToOne com AccountModel
     * Um usuário pode ter multiplas contas bancarias
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountModel> account = new ArrayList<>();

    
    /**
     * Relacionamento OneToMany com InvestmentAccountModel
     * Um usuário pode ter múltiplos investimentos
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvestmentAccountModel> investments = new ArrayList<>();
    
    /**
     * Relacionamento OneToMany com ContentModel
     * Um usuário(professor) pode ter múltiplos conteudos
     */
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<ContentModel> contentsModel = new ArrayList<>();

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
    public UserModel() {
    }

    public UserModel(String name, String email, String password) {
        // Valida dados antes de criar o usuário
        ValidationService.ValidationResult validation = validateUserData(name, email);
        if (!validation.isValid()) {
            throw new IllegalArgumentException("Dados inválidos: " + validation.getErrorMessage());
        }
        
        this.name = name;
        this.email = email;
        this.password = encryptPassword(password);
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // Valida nome antes de definir
        if (!isValidName(name)) {
            throw new IllegalArgumentException("Nome inválido: deve conter apenas letras, espaços, acentos e hífens. Não pode conter palavrões, palavras soltas ou caracteres inadequados.");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        // Valida email antes de definir
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Email inválido: formato incorreto ou domínio suspeito.");
        }
        this.email = email;
    }

    public String getPassword() {
        // NUNCA retornar a senha descriptografada por questões de segurança
        // A senha deve ser verificada usando o método verifyPassword()
        return "**********"; // Retorna apenas asteriscos por segurança
    }

    public void setPassword(String password) {
        this.password = encryptPassword(password);
    }
    
    /**
    * Obtém a(as) conta do usuário.
    * @return conta
         */
    public List<AccountModel> getAccount() {
        return account;  // Retorna a conta associada ao usuário
    }

    /**
     * Adiciona uma conta ao usuário
     * @param account conta a ser adicionada
     */
    public void setAccount(List<AccountModel> account) {
        this.account = account;
    }

    /** 
     * Adicionar outra conta ao usuario
     * @param account Conta a ser adicionada
     */ 
    public void addAccount(AccountModel account) {
        if (this.account == null) {
            this.account = new ArrayList<>();
        }
        this.account.add(account);
        account.setUser(this);
    }
    
    /**
     * Remove a conta do usuário
     * @param account Conta a ser removida
     */
    public void removeAccount(AccountModel account) {
        if (account != null) {
            account.remove(account);
            account.setUser(null);
        }
    }
    
    /**
     * Obtém a lista de investimentos do usuário
     * @return Lista de investimentos
     */
    public List<InvestmentAccountModel> getInvestments() {
        return investments;
    }
    
    /**
     * Define a lista de investimentos do usuário
     * @param investments Lista de investimentos
     */
    public void setInvestments(List<InvestmentAccountModel> investments) {
        this.investments = investments;
    }
    
    /**
     * Adiciona um investimento ao usuário
     * @param investment Investimento a ser adicionado
     */
    public void addInvestment(InvestmentAccountModel investment) {
        if (investments == null) {
            investments = new ArrayList<>();
        }
        investments.add(investment);
        investment.setUser(this);
    }
    
    /**
     * Remove um investimento do usuário
     * @param investment Investimento a ser removido
     */
    public void removeInvestment(InvestmentAccountModel investment) {
        if (investments != null) {
            investments.remove(investment);
            investment.setUser(null);
        }
    }
    
    /**
     * Verifica se a senha fornecida corresponde à senha armazenada
     * @param rawPassword Senha em texto plano para verificar
     * @return true se a senha está correta, false caso contrário
     */
    public boolean verifyPassword(String rawPassword) {
        try {
            String decryptedPassword = decryptPassword(this.password);
            return rawPassword.equals(decryptedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar senha", e);
        }
    }

    // Métodos de criptografia para senha usando AES
    private String encryptPassword(String password) {
        try {
            return EncryptionService.encrypt(password, key);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criptografar a senha", e);
        }
    }

    private String decryptPassword(String encryptedPassword) {
        try {
            return EncryptionService.decrypt(encryptedPassword, key);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao descriptografar a senha", e);
        }
    }
    
    // Métodos de validação
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

    // Métodos ToString, HashCode e Equals
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserModel: id=").append(id != null ? id : "null");
        sb.append(", name=").append(name != null ? name : "null");
        sb.append(", email=").append(email != null ? email : "null");
        sb.append(", password=**********");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
        // NÃO incluir password por questões de segurança
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserModel userModel = (UserModel) obj;
        return Objects.equals(id, userModel.id);
    }
}
package br.ifsul.finwise.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "role"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = AlunoModelo.class, name = "aluno"),
    @JsonSubTypes.Type(value = ProfessorModelo.class, name = "professor"),
})

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuario")
public abstract class UsuarioModelo {

    // Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Nome não pode ser nulo")
    private String name;

    @Column(name = "senha", nullable = false)
    @NotNull(message = "Senha não pode ser nula")
    @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
    private String senha;

    // Relacionamentos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CategoriaModelo> listaCategoria = new ArrayList<>();

    @OneToOne(mappedBy = "usuario")
    private ContaFinanceiraModelo conta;

    @ManyToMany(mappedBy = "usuariosFavoritos", fetch = FetchType.EAGER)
    private Set<ConteudoModelo> listaFavoritos = new HashSet<>();

    // NOVO RELACIONAMENTO: um usuário tem muitos investimentos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvestmentAccountModel> listaInvestimentos = new ArrayList<>();

    // Construtores
    public UsuarioModelo() {}

    public UsuarioModelo(String name, String senha) {
        this.name = name;
        this.senha = senha;
    }

    public UsuarioModelo(Integer id, String name, String senha,
                         List<CategoriaModelo> listaCategoria, ContaFinanceiraModelo conta,
                         Set<ConteudoModelo> listaFavoritos, List<InvestmentAccountModel> listaInvestimentos) {
        this.id = id;
        this.name = name;
        this.senha = senha;
        this.listaCategoria = listaCategoria;
        this.conta = conta;
        this.listaFavoritos = listaFavoritos;
        this.listaInvestimentos = listaInvestimentos;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public List<CategoriaModelo> getListaCategoria() { return listaCategoria; }
    public void setListaCategoria(List<CategoriaModelo> listaCategoria) { this.listaCategoria = listaCategoria; }

    public ContaFinanceiraModelo getConta() { return conta; }
    public void setConta(ContaFinanceiraModelo conta) { this.conta = conta; }

    public Set<ConteudoModelo> getListaFavoritos() { return listaFavoritos; }
    public void setListaFavoritos(Set<ConteudoModelo> listaFavoritos) { this.listaFavoritos = listaFavoritos; }

    public List<InvestmentAccountModel> getListaInvestimentos() { return listaInvestimentos; }
    public void setListaInvestimentos(List<InvestmentAccountModel> listaInvestimentos) { this.listaInvestimentos = listaInvestimentos; }

    // Método auxiliar para verificar senha
    public boolean verifyPassword(String rawPassword) {
        return rawPassword.equals(this.senha);
    }

    // toString, hashCode e equals (atualizados para incluir investimentos)
    @Override
    public String toString() {
        return "UsuarioModelo [id=" + id + ", name=" + name + ", senha=" + senha
                + ", listaCategoria=" + listaCategoria
                + ", conta=" + conta
                + ", listaFavoritos=" + listaFavoritos
                + ", listaInvestimentos=" + listaInvestimentos + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((senha == null) ? 0 : senha.hashCode());
        result = prime * result + ((listaCategoria == null) ? 0 : listaCategoria.hashCode());
        result = prime * result + ((conta == null) ? 0 : conta.hashCode());
        result = prime * result + ((listaFavoritos == null) ? 0 : listaFavoritos.hashCode());
        result = prime * result + ((listaInvestimentos == null) ? 0 : listaInvestimentos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UsuarioModelo other = (UsuarioModelo) obj;
        if (id == null) { if (other.id != null) return false; }
        else if (!id.equals(other.id)) return false;
        if (name == null) { if (other.name != null) return false; }
        else if (!name.equals(other.name)) return false;
        if (senha == null) { if (other.senha != null) return false; }
        else if (!senha.equals(other.senha)) return false;
        if (listaCategoria == null) { if (other.listaCategoria != null) return false; }
        else if (!listaCategoria.equals(other.listaCategoria)) return false;
        if (conta == null) { if (other.conta != null) return false; }
        else if (!conta.equals(other.conta)) return false;
        if (listaFavoritos == null) { if (other.listaFavoritos != null) return false; }
        else if (!listaFavoritos.equals(other.listaFavoritos)) return false;
        if (listaInvestimentos == null) { if (other.listaInvestimentos != null) return false; }
        else if (!listaInvestimentos.equals(other.listaInvestimentos)) return false;
        return true;
    }

}

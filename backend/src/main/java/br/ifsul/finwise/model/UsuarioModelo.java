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
import com.fasterxml.jackson.annotation.JsonBackReference;


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

    @Column(name = "password", nullable = false)
    @NotNull(message = "password não pode ser nula")
    @Size(min = 6, message = "A password deve ter pelo menos 6 caracteres")
    private String password;

    // Relacionamentos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CategoriaModelo> listaCategoria = new ArrayList<>();

    
    @OneToOne(mappedBy = "usuario")
    @JsonBackReference
    private ContaFinanceiraModelo conta;

    @ManyToMany(mappedBy = "usuariosFavoritos", fetch = FetchType.EAGER)
    private Set<ConteudoModelo> listaFavoritos = new HashSet<>();

    // NOVO RELACIONAMENTO: um usuário tem muitos investimentos
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ContaInvestimentoModel> listaInvestimentos = new ArrayList<>();

    // Construtores
    public UsuarioModelo() {}

    public UsuarioModelo(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public UsuarioModelo(Integer id, String name, String password,
                         List<CategoriaModelo> listaCategoria, ContaFinanceiraModelo conta,
                         Set<ConteudoModelo> listaFavoritos, List<ContaInvestimentoModel> listaInvestimentos) {
        this.id = id;
        this.name = name;
        this.password = password;
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

    public String getpassword() { return password; }
    public void setpassword(String password) { this.password = password; }

    public List<CategoriaModelo> getListaCategoria() { return listaCategoria; }
    public void setListaCategoria(List<CategoriaModelo> listaCategoria) { this.listaCategoria = listaCategoria; }

    public ContaFinanceiraModelo getConta() { return conta; }
    public void setConta(ContaFinanceiraModelo conta) { this.conta = conta; }

    public Set<ConteudoModelo> getListaFavoritos() { return listaFavoritos; }
    public void setListaFavoritos(Set<ConteudoModelo> listaFavoritos) { this.listaFavoritos = listaFavoritos; }

    public List<ContaInvestimentoModel> getListaInvestimentos() { return listaInvestimentos; }
    public void setListaInvestimentos(List<ContaInvestimentoModel> listaInvestimentos) { this.listaInvestimentos = listaInvestimentos; }

    // Método auxiliar para verificar password
    public boolean verifypassword(String rawpassword) {
        return rawpassword.equals(this.password);
    }

    // toString, hashCode e equals (atualizados para incluir investimentos)
    @Override
    public String toString() {
        return "UsuarioModelo [id=" + id + ", name=" + name + ", password=" + password
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
        result = prime * result + ((password == null) ? 0 : password.hashCode());
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
        if (password == null) { if (other.password != null) return false; }
        else if (!password.equals(other.password)) return false;
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

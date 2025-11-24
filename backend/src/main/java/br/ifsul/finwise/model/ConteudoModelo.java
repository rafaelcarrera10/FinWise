package br.ifsul.finwise.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "role" // campo no JSON que indica o tipo
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = VideoModelo.class, name = "video"),
    @JsonSubTypes.Type(value = PublicacaoModelo.class, name = "publicacao"),
})

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "conteudo")
public abstract class ConteudoModelo{
    
    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "descricao", nullable = false)
    @NotNull(message = "descricao não pode ser nulo")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag", nullable = false)
    @NotNull(message = "tag não pode ser nulo")
    private TagEnum tag;

    @Column(name = "titulo", nullable = false)
    @NotNull(message = "titulo não pode ser nulo")
    private String titulo;

    // Relacionamentos

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private ProfessorModelo professor;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
    name = "colecao_conteudoLista",
    joinColumns = @JoinColumn(name = "conteudo_id"),
    inverseJoinColumns = @JoinColumn(name = "listaConteudo_id")
)
    private Set<ConteudoModelo> conteudoLista = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
    name = "favoritos",
    joinColumns = @JoinColumn(name = "conteudo_id"),
    inverseJoinColumns = @JoinColumn(name = "usuario_id")
)
    private Set<UsuarioModelo> favoritoUsuario = new HashSet<>();


    // Construtores

    public ConteudoModelo() {
    }

    public ConteudoModelo(Integer id, @NotNull(message = "descricao não pode ser nulo") String descricao,
            @NotNull(message = "tag não pode ser nulo") TagEnum tag,
            @NotNull(message = "titulo não pode ser nulo") String titulo) {
        this.id = id;
        this.descricao = descricao;
        this.tag = tag;
        this.titulo = titulo;
    }

    public ConteudoModelo(Integer id, @NotNull(message = "descricao não pode ser nulo") String descricao,
            @NotNull(message = "tag não pode ser nulo") TagEnum tag,
            @NotNull(message = "titulo não pode ser nulo") String titulo, ProfessorModelo professor,
            Set<ConteudoModelo> conteudoLista, Set<UsuarioModelo> favoritoUsuario) {
        this.id = id;
        this.descricao = descricao;
        this.tag = tag;
        this.titulo = titulo;
        this.professor = professor;
        this.conteudoLista = conteudoLista;
        this.favoritoUsuario = favoritoUsuario;
    }

    // Getters

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public TagEnum getTag() {
        return tag;
    }

    public String getTitulo() {
        return titulo;
    }

    public ProfessorModelo getProfessor() {
        return professor;
    }

    public Set<ConteudoModelo> getconteudoLista() {
        return conteudoLista;
    }

    public Set<UsuarioModelo> favoritoUsuario() {
        return favoritoUsuario;
    }

    // Setters

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTag(TagEnum tag) {
        this.tag = tag;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setProfessor(ProfessorModelo professor) {
        this.professor = professor;
    }

    public void setconteudoLista(Set<ConteudoModelo> conteudoLista) {
        this.conteudoLista = conteudoLista;
    }

    public void setFavoritoUsuario(Set<UsuarioModelo> favoritoUsuario) {
        this.favoritoUsuario = favoritoUsuario;
    }
    // ToString, hashCode, equals

    @Override
    public String toString() {
        return "ConteudoModelo [id=" + id + ", descricao=" + descricao + ", tag=" + tag + ", titulo=" + titulo
                + ", professor=" + professor + ", conteudoLista=" + conteudoLista + "favoritoUsuario= "+favoritoUsuario+"]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((tag == null) ? 0 : tag.hashCode());
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        result = prime * result + ((professor == null) ? 0 : professor.hashCode());
        result = prime * result + ((conteudoLista == null) ? 0 : conteudoLista.hashCode());
        result = prime * result + ((favoritoUsuario == null) ? 0 : favoritoUsuario.hashCode());
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
        ConteudoModelo other = (ConteudoModelo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (tag != other.tag)
            return false;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        if (professor == null) {
            if (other.professor != null)
                return false;
        } else if (!professor.equals(other.professor))
            return false;
        if (conteudoLista == null) {
            if (other.conteudoLista != null)
                return false;
        } else if (!conteudoLista.equals(other.conteudoLista))
            return false;
        if (favoritoUsuario == null) {
            if (other.favoritoUsuario != null)
                return false;
        } else if (!favoritoUsuario.equals(other.favoritoUsuario))
            return false;
        return true;
    }

    
}

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
    property = "role"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = VideoModelo.class, name = "video"),
    @JsonSubTypes.Type(value = PublicacaoModelo.class, name = "publicacao")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "conteudo")
public abstract class ConteudoModelo {
    
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
        name = "colecao_conteudo_lista",
        joinColumns = @JoinColumn(name = "conteudo_id"),
        inverseJoinColumns = @JoinColumn(name = "lista_conteudo_id")
    )
    private Set<ListaConteudoModelo> conteudoListas = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "favoritos",
        joinColumns = @JoinColumn(name = "conteudo_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<UsuarioModelo> usuariosFavoritos = new HashSet<>();

    // Construtores
    public ConteudoModelo() {}

    public ConteudoModelo(Integer id, String descricao, TagEnum tag, String titulo) {
        this.id = id;
        this.descricao = descricao;
        this.tag = tag;
        this.titulo = titulo;
    }

    public ConteudoModelo(Integer id, String descricao, TagEnum tag, String titulo, ProfessorModelo professor,
                          Set<ListaConteudoModelo> conteudoListas, Set<UsuarioModelo> usuariosFavoritos) {
        this.id = id;
        this.descricao = descricao;
        this.tag = tag;
        this.titulo = titulo;
        this.professor = professor;
        this.conteudoListas = (conteudoListas != null) ? conteudoListas : new HashSet<>();
        this.usuariosFavoritos = (usuariosFavoritos != null) ? usuariosFavoritos : new HashSet<>();
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public TagEnum getTag() { return tag; }
    public void setTag(TagEnum tag) { this.tag = tag; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public ProfessorModelo getProfessor() { return professor; }
    public void setProfessor(ProfessorModelo professor) { this.professor = professor; }
    public Set<ListaConteudoModelo> getConteudoListas() { return conteudoListas; }
    public void setConteudoListas(Set<ListaConteudoModelo> conteudoListas) { this.conteudoListas = conteudoListas; }
    public Set<UsuarioModelo> getUsuariosFavoritos() { return usuariosFavoritos; }
    public void setUsuariosFavoritos(Set<UsuarioModelo> usuariosFavoritos) { this.usuariosFavoritos = usuariosFavoritos; }

    // toString, hashCode, equals
    @Override
    public String toString() {
        return "ConteudoModelo [id=" + id + ", descricao=" + descricao + ", tag=" + tag + ", titulo=" + titulo
                + ", professor=" + professor + ", conteudoListas=" + conteudoListas + ", usuariosFavoritos=" + usuariosFavoritos + "]";
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
        result = prime * result + ((conteudoListas == null) ? 0 : conteudoListas.hashCode());
        result = prime * result + ((usuariosFavoritos == null) ? 0 : usuariosFavoritos.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ConteudoModelo other = (ConteudoModelo) obj;
        return id.equals(other.id);
    }
}

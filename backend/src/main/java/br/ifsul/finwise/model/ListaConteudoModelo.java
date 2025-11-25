package br.ifsul.finwise.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "listaConteudo")
public class ListaConteudoModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    @NotNull(message = "nome não pode ser nulo")
    private String nome;

    @Column(name = "descricao", nullable = false)
    @NotNull(message = "descricao não pode ser nulo")
    private String descricao;

    @ElementCollection(targetClass = TagEnum.class)
    @CollectionTable(
    name = "ListaConteudo_tags",
    joinColumns = @JoinColumn(name = "listaConteudo_id")
)
    @Enumerated(EnumType.STRING)
    @Column(name = "tags", nullable = false)
    @NotNull(message = "tag não pode ser nula")
    private List<TagEnum> tags;

    // Relacionamentos

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private ProfessorModelo professor;
    
    @ManyToMany(mappedBy = "listaConteudo_id", fetch = FetchType.EAGER)
    private Set<ConteudoModelo> livros = new HashSet<>();

    // Construtores

    public ListaConteudoModelo() {
    }

    public ListaConteudoModelo(Integer id, @NotNull(message = "nome não pode ser nulo") String nome,
            @NotNull(message = "descricao não pode ser nulo") String descricao,
            @NotNull(message = "tag não pode ser nula") List<TagEnum> tags) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tags = tags;
    }

    public ListaConteudoModelo(Integer id, @NotNull(message = "nome não pode ser nulo") String nome,
            @NotNull(message = "descricao não pode ser nulo") String descricao,
            @NotNull(message = "tag não pode ser nula") List<TagEnum> tags, ProfessorModelo professor,
            Set<ConteudoModelo> livros) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tags = tags;
        this.professor = professor;
        this.livros = livros;
    }

    // Getters
    
    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public List<TagEnum> getTags() {
        return tags;
    }

    public ProfessorModelo getProfessor() {
        return professor;
    }

    public Set<ConteudoModelo> getLivros() {
        return livros;
    }

    // Setters
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setTags(List<TagEnum> tags) {
        this.tags = tags;
    }

    public void setProfessor(ProfessorModelo professor) {
        this.professor = professor;
    }

    public void setLivros(Set<ConteudoModelo> livros) {
        this.livros = livros;
    }

    // ToString, hashCode, equals
    
    @Override
    public String toString() {
        return "ListaConteudoModelo [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", tags=" + tags
                + ", professor=" + professor + ", livros=" + livros + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        result = prime * result + ((professor == null) ? 0 : professor.hashCode());
        result = prime * result + ((livros == null) ? 0 : livros.hashCode());
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
        ListaConteudoModelo other = (ListaConteudoModelo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (descricao == null) {
            if (other.descricao != null)
                return false;
        } else if (!descricao.equals(other.descricao))
            return false;
        if (tags == null) {
            if (other.tags != null)
                return false;
        } else if (!tags.equals(other.tags))
            return false;
        if (professor == null) {
            if (other.professor != null)
                return false;
        } else if (!professor.equals(other.professor))
            return false;
        if (livros == null) {
            if (other.livros != null)
                return false;
        } else if (!livros.equals(other.livros))
            return false;
        return true;
    }
    
    
}

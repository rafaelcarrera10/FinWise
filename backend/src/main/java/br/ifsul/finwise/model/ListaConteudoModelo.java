package br.ifsul.finwise.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "lista_conteudo")
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
        name = "lista_conteudo_tags",
        joinColumns = @JoinColumn(name = "lista_conteudo_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "tags", nullable = false)
    @NotNull(message = "tag não pode ser nula")
    private List<TagEnum> tags;

    // Relacionamentos
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private ProfessorModelo professor;

    @ManyToMany(mappedBy = "conteudoListas", fetch = FetchType.EAGER)
    private Set<ConteudoModelo> livros = new HashSet<>();

    // Construtores
    public ListaConteudoModelo() {}

    public ListaConteudoModelo(Integer id, String nome, String descricao, List<TagEnum> tags) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tags = tags;
    }

    public ListaConteudoModelo(Integer id, String nome, String descricao, List<TagEnum> tags, 
                               ProfessorModelo professor, Set<ConteudoModelo> livros) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tags = tags;
        this.professor = professor;
        this.livros = livros;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<TagEnum> getTags() { return tags; }
    public void setTags(List<TagEnum> tags) { this.tags = tags; }
    public ProfessorModelo getProfessor() { return professor; }
    public void setProfessor(ProfessorModelo professor) { this.professor = professor; }
    public Set<ConteudoModelo> getLivros() { return livros; }
    public void setLivros(Set<ConteudoModelo> livros) { this.livros = livros; }

    // toString, hashCode, equals
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
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ListaConteudoModelo other = (ListaConteudoModelo) obj;
        return id.equals(other.id);
    }
}

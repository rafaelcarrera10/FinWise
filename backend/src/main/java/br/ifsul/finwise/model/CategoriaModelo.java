package br.ifsul.finwise.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "categoria")
public class CategoriaModelo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Nome não pode ser nulo")
    private String name;

    // Relacionamentos

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModelo usuario;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransacaoModelo> listaTransacao = new ArrayList<>();

    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name = "orcamento_id")
    private OrcamentoModelo orcamento;

    // Construtores
    public CategoriaModelo() {}

    public CategoriaModelo(Integer id, @NotNull(message = "Nome não pode ser nulo") String name) {
        this.id = id;
        this.name = name;
    }

    public CategoriaModelo(Integer id, @NotNull(message = "Nome não pode ser nulo") String name, UsuarioModelo usuario,
            List<TransacaoModelo> listaTransacao, OrcamentoModelo orcamento) {
        this.id = id;
        this.name = name;
        this.usuario = usuario;
        this.listaTransacao = listaTransacao;
        this.orcamento = orcamento;
    }

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public UsuarioModelo getUsuario() { return usuario; }
    public void setUsuario(UsuarioModelo usuario) { this.usuario = usuario; }

    public List<TransacaoModelo> getListaTransacao() { return listaTransacao; }
    public void setListaTransacao(List<TransacaoModelo> listaTransacao) { this.listaTransacao = listaTransacao; }

    public OrcamentoModelo getOrcamento() { return orcamento; }
    public void setOrcamento(OrcamentoModelo orcamento) { this.orcamento = orcamento; }

    // toString, hashCode, equals
    @Override
    public String toString() {
        return "CategoriaModelo [id=" + id + ", name=" + name + ", usuario=" + usuario + ", listaTransacao="
                + listaTransacao + ", orcamento=" + orcamento + "]";
    }
}

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

    public CategoriaModelo() {
    }

    public CategoriaModelo(Integer id, @NotNull(message = "Nome não pode ser nulo") String name) {
        this.id = id;
        this.name = name;
    }

    public CategoriaModelo(Integer id, @NotNull(message = "Nome não pode ser nulo") String name, UsuarioModelo usuario,
            List<TransacaoModelo> listaTransacao) {
        this.id = id;
        this.name = name;
        this.usuario = usuario;
        this.listaTransacao = listaTransacao;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UsuarioModelo getUsuario() {
        return usuario;
    }

    public List<TransacaoModelo> getListaTransacao() {
        return listaTransacao;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsuario(UsuarioModelo usuario) {
        this.usuario = usuario;
    }

    public void setListaTransacao(List<TransacaoModelo> listaTransacao) {
        this.listaTransacao = listaTransacao;
    }

    @Override
    public String toString() {
        return "CategoriaModelo [id=" + id + ", name=" + name + ", usuario=" + usuario + ", listaTransacao="
                + listaTransacao + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result + ((listaTransacao == null) ? 0 : listaTransacao.hashCode());
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
        CategoriaModelo other = (CategoriaModelo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        if (listaTransacao == null) {
            if (other.listaTransacao != null)
                return false;
        } else if (!listaTransacao.equals(other.listaTransacao))
            return false;
        return true;
    }

    

}

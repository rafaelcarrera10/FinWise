package br.ifsul.finwise.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "professor")
public class ProfessorModelo extends UsuarioModelo {
    
    
    // Atributos

    @Column(name = "status", nullable = false)
    @NotNull(message = "Status não pode ser nulo")
    private Boolean status;

    @Column(name = "fotoPerfil", nullable = false)
    @NotNull(message = "Foto de perfil não pode ser nula")
    private String fotoPerfil;

    @Column(name = "biografia", nullable = false)
    @NotNull(message = "Biografia não pode ser nulo")
    private String biografia;
    

    // Relacionamentos

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConteudoModelo> listaConteudo = new ArrayList<>();

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ListaConteudoModelo> colecaoListaConteudo = new ArrayList<>();


    // Construtores

    public ProfessorModelo() {
    }

    public ProfessorModelo(String name, String senha, @NotNull(message = "Status não pode ser nulo") Boolean status,
            @NotNull(message = "Foto de perfil não pode ser nula") String fotoPerfil,
            @NotNull(message = "Biografia não pode ser nulo") String biografia) {
        super(name, senha);
        this.status = status;
        this.fotoPerfil = fotoPerfil;
        this.biografia = biografia;
    }

    public ProfessorModelo(Integer id, @NotNull(message = "Nome não pode ser nulo") String name,
            @NotNull(message = "Senha não pode ser nula") @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres") String senha,
            List<CategoriaModelo> listaCategoria, ContaFinanceiraModelo conta, Set<ConteudoModelo> listaFavoritos,
            @NotNull(message = "Status não pode ser nulo") Boolean status,
            @NotNull(message = "Foto de perfil não pode ser nula") String fotoPerfil,
            @NotNull(message = "Biografia não pode ser nulo") String biografia, List<ConteudoModelo> listaConteudo,
            List<ListaConteudoModelo> colecaoListaConteudo) {
        super(id, name, senha, listaCategoria, conta, listaFavoritos);
        this.status = status;
        this.fotoPerfil = fotoPerfil;
        this.biografia = biografia;
        this.listaConteudo = listaConteudo;
        this.colecaoListaConteudo = colecaoListaConteudo;
    }

    // Getters

    public Boolean getStatus() {
        return status;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public String getBiografia() {
        return biografia;
    }

    public List<ConteudoModelo> getListaConteudo() {
        return listaConteudo;
    }

    public List<ListaConteudoModelo> getColecaoListaConteudo() {
        return colecaoListaConteudo;
    }

    // Setters
    
    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setListaConteudo(List<ConteudoModelo> listaConteudo) {
        this.listaConteudo = listaConteudo;
    }

    public void setColecaoListaConteudo(List<ListaConteudoModelo> colecaoListaConteudo) {
        this.colecaoListaConteudo = colecaoListaConteudo;
    }

    // ToString, hashCode, equals

    @Override
    public String toString() {
        return "ProfessorModelo [status=" + status + ", fotoPerfil=" + fotoPerfil + ", biografia=" + biografia
                + ", listaConteudo=" + listaConteudo + ", colecaoListaConteudo=" + colecaoListaConteudo
                + ", getClass()=" + getClass() + ", getId()=" + getId() + ", getName()=" + getName() + ", getStatus()="
                + getStatus() + ", getSenha()=" + getSenha() + ", getFotoPerfil()=" + getFotoPerfil()
                + ", getListaCategoria()=" + getListaCategoria() + ", getBiografia()=" + getBiografia()
                + ", getConta()=" + getConta() + ", getListaConteudo()=" + getListaConteudo() + ", getListaFavoritos()="
                + getListaFavoritos() + ", getColecaoListaConteudo()=" + getColecaoListaConteudo() + ", toString()="
                + super.toString() + ", hashCode()=" + hashCode() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((fotoPerfil == null) ? 0 : fotoPerfil.hashCode());
        result = prime * result + ((biografia == null) ? 0 : biografia.hashCode());
        result = prime * result + ((listaConteudo == null) ? 0 : listaConteudo.hashCode());
        result = prime * result + ((colecaoListaConteudo == null) ? 0 : colecaoListaConteudo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProfessorModelo other = (ProfessorModelo) obj;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (fotoPerfil == null) {
            if (other.fotoPerfil != null)
                return false;
        } else if (!fotoPerfil.equals(other.fotoPerfil))
            return false;
        if (biografia == null) {
            if (other.biografia != null)
                return false;
        } else if (!biografia.equals(other.biografia))
            return false;
        if (listaConteudo == null) {
            if (other.listaConteudo != null)
                return false;
        } else if (!listaConteudo.equals(other.listaConteudo))
            return false;
        if (colecaoListaConteudo == null) {
            if (other.colecaoListaConteudo != null)
                return false;
        } else if (!colecaoListaConteudo.equals(other.colecaoListaConteudo))
            return false;
        return true;
    }

    
}

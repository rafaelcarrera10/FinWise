package br.ifsul.finwise.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "publicação")
public class PublicacaoModelo extends ConteudoModelo{
    

    // Atributos
   @ElementCollection
    @CollectionTable(
    name = "publicacao_fotos",
    joinColumns = @JoinColumn(name = "publicacao_id")
)
    @Column(name = "foto")
    @NotNull(message = "texto não pode ser nulo")
    private List<String> foto;

    @Column(name = "texto", nullable = false)
    @NotNull(message = "texto não pode ser nulo")
    private String texto;

    // Construtores
    
    public PublicacaoModelo() {
    }

    public PublicacaoModelo(Integer id, @NotNull(message = "descricao não pode ser nulo") String descricao,
            @NotNull(message = "tag não pode ser nulo") TagEnum tag,
            @NotNull(message = "titulo não pode ser nulo") String titulo,
            @NotNull(message = "texto não pode ser nulo") List<String> foto,
            @NotNull(message = "texto não pode ser nulo") String texto) {
        super(id, descricao, tag, titulo);
        this.foto = foto;
        this.texto = texto;
    }

    public PublicacaoModelo(Integer id, @NotNull(message = "descricao não pode ser nulo") String descricao,
            @NotNull(message = "tag não pode ser nulo") TagEnum tag,
            @NotNull(message = "titulo não pode ser nulo") String titulo, ProfessorModelo professor,
            Set<ConteudoModelo> conteudoLista, Set<UsuarioModelo> favoritoUsuario,
            @NotNull(message = "texto não pode ser nulo") List<String> foto,
            @NotNull(message = "texto não pode ser nulo") String texto) {
        super(id, descricao, tag, titulo, professor, conteudoLista, favoritoUsuario);
        this.foto = foto;
        this.texto = texto;
    }
    // Getters

    public List<String> getFoto() {
        return foto;
    }

    public String getTexto() {
        return texto;
    }

    // Setters

    public void setFoto(List<String> foto) {
        this.foto = foto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    // ToString, hashCode, equals

    @Override
    public String toString() {
        return "PublicacaoModelo [foto=" + foto + ", texto=" + texto + ", toString()=" + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((foto == null) ? 0 : foto.hashCode());
        result = prime * result + ((texto == null) ? 0 : texto.hashCode());
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
        PublicacaoModelo other = (PublicacaoModelo) obj;
        if (foto == null) {
            if (other.foto != null)
                return false;
        } else if (!foto.equals(other.foto))
            return false;
        if (texto == null) {
            if (other.texto != null)
                return false;
        } else if (!texto.equals(other.texto))
            return false;
        return true;
    }

    
}

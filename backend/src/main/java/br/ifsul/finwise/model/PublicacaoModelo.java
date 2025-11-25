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
@Table(name = "publicacao")
public class PublicacaoModelo extends ConteudoModelo {

    // Atributos específicos
    @ElementCollection
    @CollectionTable(
        name = "publicacao_fotos",
        joinColumns = @JoinColumn(name = "publicacao_id")
    )
    @Column(name = "foto", nullable = false)
    @NotNull(message = "foto não pode ser nulo")
    private List<String> fotos;

    @Column(name = "texto", nullable = false)
    @NotNull(message = "texto não pode ser nulo")
    private String texto;

    // Construtores
    public PublicacaoModelo() {}

    public PublicacaoModelo(Integer id,
                            @NotNull(message = "descricao não pode ser nulo") String descricao,
                            @NotNull(message = "tag não pode ser nulo") TagEnum tag,
                            @NotNull(message = "titulo não pode ser nulo") String titulo,
                            @NotNull(message = "foto não pode ser nulo") List<String> fotos,
                            @NotNull(message = "texto não pode ser nulo") String texto) {
        super(id, descricao, tag, titulo);
        this.fotos = fotos;
        this.texto = texto;
    }

    public PublicacaoModelo(Integer id,
                            @NotNull(message = "descricao não pode ser nulo") String descricao,
                            @NotNull(message = "tag não pode ser nulo") TagEnum tag,
                            @NotNull(message = "titulo não pode ser nulo") String titulo,
                            ProfessorModelo professor,
                            Set<ListaConteudoModelo> conteudoListas,
                            Set<UsuarioModelo> usuariosFavoritos,
                            @NotNull(message = "foto não pode ser nulo") List<String> fotos,
                            @NotNull(message = "texto não pode ser nulo") String texto) {
        super(id, descricao, tag, titulo, professor, conteudoListas, usuariosFavoritos);
        this.fotos = fotos;
        this.texto = texto;
    }

    // Getters e Setters
    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    // toString, hashCode, equals
    @Override
    public String toString() {
        return "PublicacaoModelo [fotos=" + fotos + ", texto=" + texto + ", " + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((fotos == null) ? 0 : fotos.hashCode());
        result = prime * result + ((texto == null) ? 0 : texto.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PublicacaoModelo other = (PublicacaoModelo) obj;
        if (fotos == null) {
            if (other.fotos != null)
                return false;
        } else if (!fotos.equals(other.fotos))
            return false;
        if (texto == null) {
            if (other.texto != null)
                return false;
        } else if (!texto.equals(other.texto))
            return false;
        return true;
    }
}

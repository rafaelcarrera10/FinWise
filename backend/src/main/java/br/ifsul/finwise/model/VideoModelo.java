package br.ifsul.finwise.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "video")
public class VideoModelo extends ConteudoModelo {
    
    // Atributos

    @Column(name = "video", nullable = false)
    @NotNull(message = "video não pode ser nulo")
    private String video;

    // Construtores 

    public VideoModelo() {
    }

    public VideoModelo(Integer id, @NotNull(message = "descricao não pode ser nulo") String descricao,
            @NotNull(message = "tag não pode ser nulo") TagEnum tag,
            @NotNull(message = "titulo não pode ser nulo") String titulo,
            @NotNull(message = "video não pode ser nulo") String video) {
        super(id, descricao, tag, titulo);
        this.video = video;
    }

     public VideoModelo(Integer id, @NotNull(message = "descricao não pode ser nulo") String descricao,
            @NotNull(message = "tag não pode ser nulo") TagEnum tag,
            @NotNull(message = "titulo não pode ser nulo") String titulo, ProfessorModelo professor,
            Set<ConteudoModelo> conteudoLista, Set<UsuarioModelo> favoritoUsuario,
            @NotNull(message = "video não pode ser nulo") String video) {
        super(id, descricao, tag, titulo, professor, conteudoLista, favoritoUsuario);
        this.video = video;
    }

    // Getters

    public String getVideo() {
        return video;
    }

    // Setters

    public void setVideo(String video) {
        this.video = video;
    }

    // ToString, hashCode, equals
    
    @Override
    public String toString() {
        return "VideoModelo [video=" + video + ", toString()=" + super.toString() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((video == null) ? 0 : video.hashCode());
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
        VideoModelo other = (VideoModelo) obj;
        if (video == null) {
            if (other.video != null)
                return false;
        } else if (!video.equals(other.video))
            return false;
        return true;
    }

    

    
}

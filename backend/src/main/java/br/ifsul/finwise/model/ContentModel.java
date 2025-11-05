package br.ifsul.finwise.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "content")
public class ContentModel {

    // RELACIONAMENTO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor", nullable = false)
    private UserModel autor;

    // VARIÁVEIS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "description", nullable = false)
    private String description;

    // CONSTRUTORES
    public ContentModel() {
    }

    public ContentModel(String title, String url, String description) {
        this.title = title;
        this.url = url;
        this.description = description;
    }

    // GETTERS E SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getAutor() {
        return autor;
    }

    public void setAutor(UserModel autor) {
        this.autor = autor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // HASHCODE,EQUALS E TOSTRING
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ContentModel other = (ContentModel) obj;
        return Objects.equals(id, other.id);
    }
    
    @Override
    public String toString() {
        return "ContentModel: id=" + (id != null ? id : "null") +
               ", title=" + (title != null ? title : "null") +
               ", url=" + (url != null ? url : "null") +
               ", description=" + (description != null ? description : "null");
    }
}

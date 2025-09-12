package br.ifsul.finwise.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "content")
public class ContentModel {
    
    //Variaveis

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //ID do conteúdo
    
    @Column(name = "title", nullable = false)
    private String title; //Título do conteúdo

    @Column(name = "url", nullable = false)
    private String url; //URL do conteúdo
    
    @Column(name = "description", nullable = false)
    private String description; //Descrição do conteúdo

    // Construtores
    public ContentModel() {
    }

    public ContentModel(String title, String url, String description) {
        this.title = title;
        this.url = url;
        this.description = description;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // Métodos ToString, HashCode e Equals
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ContentModel: id=").append(id != null ? id : "null");
        sb.append(", title=").append(title != null ? title : "null");
        sb.append(", url=").append(url != null ? url : "null");
        sb.append(", description=").append(description != null ? description : "null");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, url, description);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ContentModel contentModel = (ContentModel) obj;
        return Objects.equals(id, contentModel.id);
    }
}
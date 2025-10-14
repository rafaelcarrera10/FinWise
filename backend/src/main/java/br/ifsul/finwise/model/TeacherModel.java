package br.ifsul.finwise.model;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("teacher")
public class TeacherModel extends UserModel {
    
    private String description;
    @Lob
    @Column(columnDefinition = "LONGBLOB") 
    private byte[] photo;

    // Construtores
    public TeacherModel() {
        super();
    }
    public TeacherModel(String name, String email, String password, String description, byte[] photo) {
        super(name, email, password);
        this.description = description;
        this.photo = photo;
    }
    // Getters e Setters
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public byte[] getPhoto() {
        return photo;
    }
    public void setFoto(byte[] photo) {
        this.photo = photo;
    }

    /**
     * Adiciona uma foto ao professor
     * @param photo Foto em formato de array de bytes
     */
    public void addPhoto(byte[] photo) {
        this.photo = photo;
    }

    /**
     * Remove a foto do professor
     * @param photo Foto em formato de array de bytes a ser removida
     */
    public void removePhoto() {
        this.photo = null;
    }

    /**
     * Adiciona uma descrição ao professor
     * @param description Descrição do professor
     */
    public void addDescription(String description) {
        this.description = description;
    }

    /**
     * Remove a descrição do professor
     * @param description Descrição a ser removida
     */
    public void removeDescription() {
        this.description = null;
    }

    // Métodos ToString, HashCode e Equals
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        TeacherModel other = (TeacherModel) obj;
        return Objects.equals(getId(), other.getId());
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TeacherModel: id=").append(getId() != null ? getId() : "null");
        sb.append(", name=").append(getName() != null ? getName() : "null");
        sb.append(", email=").append(getEmail() != null ? getEmail() : "null");
        sb.append(", password=**********");
        sb.append(", description=").append(description != null ? description : "null");
        sb.append(", photo=").append(photo != null ? "exists" : "null");
        return sb.toString();
    }  
}
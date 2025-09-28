package br.ifsul.finwise.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrator")
@DiscriminatorValue("administrator")
public class AdministratorModel extends UserModel {
    

    // Construtores
    public AdministratorModel() { 
        super();
    }
    public AdministratorModel(String name, String email, String password) {
        super(name, email, password);
    }

    // Metodos HashCode, Equals e ToString
    @Override
    public int hashCode() {
        return java.util.Objects.hash(getId());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        AdministratorModel other = (AdministratorModel) obj;
        return java.util.Objects.equals(getId(), other.getId());
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdministratorModel: id=").append(getId() != null ? getId() : "null");
        sb.append(", name=").append(getName() != null ? getName() : "null");
        sb.append(", email=").append(getEmail() != null ? getEmail() : "null");
        sb.append(", password=**********");
        return sb.toString();
    }
}
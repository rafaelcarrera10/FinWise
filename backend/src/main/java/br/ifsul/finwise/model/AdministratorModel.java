package br.ifsul.finwise.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("administrator")
public class AdministratorModel extends UserModel {

    // Construtores
    public AdministratorModel() {
        super();
    }

    public AdministratorModel(String name, String email, String password) {
        super(name, email, password);
    }

    // HashCode e Equals
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

    // ToString
    @Override
    public String toString() {
        return "AdministratorModel: id=" + (getId() != null ? getId() : "null") +
               ", name=" + (getName() != null ? getName() : "null") +
               ", email=" + (getEmail() != null ? getEmail() : "null") +
               ", password=**********";
    }
}

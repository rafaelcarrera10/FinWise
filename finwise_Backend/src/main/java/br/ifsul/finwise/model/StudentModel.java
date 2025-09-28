package br.ifsul.finwise.model;

import java.util.Objects;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
@DiscriminatorValue("student")
public class StudentModel extends UserModel {
    
    // Metodos
    public StudentModel() { 
        super();
    }
    public StudentModel(String name, String email, String password) {
        super(name, email, password);
    }   
    
    // Metodos HashCode, Equals e ToString
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
        StudentModel other = (StudentModel) obj;
        return Objects.equals(getId(), other.getId());
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StudentModel: id=").append(getId() != null ? getId() : "null");
        sb.append(", name=").append(getName() != null ? getName() : "null");
        sb.append(", email=").append(getEmail() != null ? getEmail() : "null");
        sb.append(", password=**********");
        return sb.toString();
    }
}

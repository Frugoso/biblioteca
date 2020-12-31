
package egg.mvn.libreria1.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;



 
@Entity
public class UsuarioBiblioteca {
    
    @Id
    private String email;
    private String nombre;
    private String clave;
    

    public UsuarioBiblioteca() {
    }

    public UsuarioBiblioteca(String email,String nombre, String clave) {
        this.email = email;
        this.clave = clave;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    
    
    
    
}

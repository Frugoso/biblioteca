
package egg.mvn.libreria1.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Prestamo implements Serializable {
@Id
@GeneratedValue(generator = "uuid2")
@GenericGenerator(name = "uuid2", strategy = "uuid2")
 
    private String id;
  
  @Temporal(TemporalType.DATE)
   private Date fecha;
  @Temporal(TemporalType.DATE)
   private Date devolucion;
   private boolean multa;
   private boolean devuelto;
   @OneToMany
   private List<Libro> litalibro;
   @OneToOne
   private Cliente cliente;
   public Prestamo() {
        
        
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    public Prestamo( Date fecha, Date devolucion, List<Libro> litalibro, Cliente cliente) {
        
        this.fecha = fecha;
        this.devolucion = devolucion;
        this.litalibro = litalibro;
        this.cliente = cliente;
    }

    public boolean isMulta() {
        return multa;
    }

    public void setMulta(boolean multa) {
        this.multa = multa;
    }

    public List<Libro> getLitalibro() {
        return litalibro;
    }

    public void setLitalibro(List<Libro> litalibro) {
        this.litalibro = litalibro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
   

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Date devolucion) {
        this.devolucion = devolucion;
    }
     
}

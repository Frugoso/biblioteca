
package egg.mvn.libreria1.repositorios;


import egg.mvn.libreria1.entidades.Prestamo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, String>{
    
    @Query("SELECT  c FROM Prestamo c, IN(c.litalibro) l")
    public List<Prestamo> PrestamoConLibros();
    
    @Query("SELECT  c FROM Prestamo c WHERE c.devuelto = TRUE")
    public List<Prestamo> PrestamosDevueltos();
    
    @Query("SELECT  c FROM Prestamo c WHERE c.devuelto = FALSE")
    public List<Prestamo> PrestamosSinDevolver();
    
    @Query("SELECT  c FROM Prestamo c , IN(c.cliente) x WHERE x.documento Like :dni")
    public List<Prestamo> PrestamosPorCliente(@Param("dni") String dni);
    
     @Query("SELECT x.nombre, COUNT(c) FROM Prestamo c , IN(c.cliente) x GROUP BY x.nombre")
     public List<String[]> PrestamosAgrupadosClientes();
    
     @Query("SELECT x.nombre, COUNT(c) FROM Prestamo c , IN(c.cliente) x WHERE c.multa = TRUE GROUP BY x.nombre ")
     public List<String[]> MultasAcumClientes();
    
//    tot
//    @Query("SELECT c FROM Prestamo c WHERE c.cliente =:documento ")
//    public List<Optional<Prestamo>> PrestamoPorCliente(@Param("documento")String documento);
}

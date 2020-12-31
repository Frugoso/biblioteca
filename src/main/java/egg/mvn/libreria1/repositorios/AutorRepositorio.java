
package egg.mvn.libreria1.repositorios;

import egg.mvn.libreria1.entidades.Autor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
    @Query("SELECT c FROM Autor c WHERE c.nombre =:nombre")
    public Optional<Autor> autorPorNombre(@Param("nombre")String nombre);
}

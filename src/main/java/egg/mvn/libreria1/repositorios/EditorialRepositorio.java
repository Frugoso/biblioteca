
package egg.mvn.libreria1.repositorios;


import egg.mvn.libreria1.entidades.Autor;
import egg.mvn.libreria1.entidades.Editorial;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
     @Query("SELECT c FROM Editorial c WHERE c.nombre =:nombre")
    public Optional<Editorial> editorialPorNombre(@Param("nombre")String nombre);
}

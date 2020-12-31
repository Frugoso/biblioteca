
package egg.mvn.libreria1.repositorios;

import egg.mvn.libreria1.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente,String>{
    
}

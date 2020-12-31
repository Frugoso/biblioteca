
package egg.mvn.libreria1.repositorios;

import egg.mvn.libreria1.entidades.UsuarioBiblioteca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioBibliotecaRepositorio extends JpaRepository<UsuarioBiblioteca, String>{
    
}


package egg.mvn.libreria1.servicios;

import egg.mvn.libreria1.entidades.Editorial;
import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.repositorios.EditorialRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EditorialServicio {
    @Autowired
    private EditorialRepositorio editorialrepositorio;
    
    @Transactional
    public void registrar(String nombre)throws ErrorServicio{
       if (nombre.isEmpty() || nombre == null){
            throw new ErrorServicio("El idnombre de la editorial de puede ser nulo");
        }
        Editorial editorial = new Editorial(nombre);
        editorialrepositorio.save(editorial);
        
    }
    @Transactional
    public void modificar(String id, String nombre)throws ErrorServicio{
        validar(id,nombre);
        Optional<Editorial>respuesta =editorialrepositorio.findById(id);
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            
            editorial.setNombre(nombre);
            editorialrepositorio.save(editorial);
        }else{
            throw new ErrorServicio("No se encontró un usuario registrado con esos datos");
        }
        
    }
    @Transactional
    public void deshabilitar(String id,String nombre)throws ErrorServicio{
        validar(id,nombre);
        Optional<Editorial>respuesta =editorialrepositorio.findById(id.toString());
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorialrepositorio.delete(editorial);
        }else{
            throw new ErrorServicio("No se encontró un usuario registrado con esos datos");
        }
    }
    
    private void validar (String id, String nombre) throws ErrorServicio{
        if (id == null || id.isEmpty()){
            throw new ErrorServicio("El nombre de la editorial de puede ser nulo");
        }
        if (nombre.isEmpty() || nombre == null){
            throw new ErrorServicio("El idnombre de la editorial de puede ser nulo");
        }
        
    }
    
}

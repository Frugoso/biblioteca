
package egg.mvn.libreria1.servicios;

import egg.mvn.libreria1.entidades.Autor;
import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.repositorios.AutorRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio{
    
    @Autowired
    private AutorRepositorio autorrepositorio;
    
    @Transactional
    public void registrar( String nombre)throws ErrorServicio{
        validar(nombre);
        Autor autor = new Autor(nombre);
        autorrepositorio.save(autor);
        
    }
    @Transactional
    public void modificar( String id,String nombre)throws ErrorServicio{
        validar(nombre);
        Optional<Autor>respuesta =autorrepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
           
            autor.setNombre(nombre);
            autorrepositorio.save(autor);
        }else{
            throw new ErrorServicio("No se encontró un usuario registrado con esos datos");
        }
//        
    }
    @Transactional
    public void deshabilitar(String id,String nombre)throws ErrorServicio{
        validar(nombre);
        Optional<Autor>respuesta =autorrepositorio.findById(id);
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autorrepositorio.delete(autor);
        }else{
            throw new ErrorServicio("No se encontró un usuario registrado con esos datos");
        }
    }
    
    private void validar ( String nombre) throws ErrorServicio{
        if (nombre.isEmpty() || nombre == null){
            throw new ErrorServicio("El nombre del usuario de puede ser nulo");
        }
       
        
    }
    public Autor autorPorId(String id){
        return autorrepositorio.findById(id).get();
    }
}

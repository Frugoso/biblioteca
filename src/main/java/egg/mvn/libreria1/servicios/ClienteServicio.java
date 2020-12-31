
package egg.mvn.libreria1.servicios;

import egg.mvn.libreria1.entidades.Cliente;
import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.repositorios.ClienteRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepositorio clienterepositorio;
    
    @Transactional
    public void registrar(String documento,String nombre, String apellido,String telefono)throws ErrorServicio{
        validar(documento,nombre,apellido,telefono);
        Cliente cliente = new Cliente(documento, nombre, apellido, telefono);
        clienterepositorio.save(cliente);
    }
    @Transactional
    public void modificar(String documento,String nombre, String apellido,String telefono)throws ErrorServicio{
    validar(documento,nombre,apellido,telefono);
    Optional<Cliente>respuesta = clienterepositorio.findById(documento);
    if(respuesta.isPresent()){
        Cliente cliente = respuesta.get();
        cliente.setDocumento(documento);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        clienterepositorio.save(cliente);
    }else{
        throw new ErrorServicio("No se enconcontró un cliente con ese documento");
    }
    }
    @Transactional
    public void deshabilitar(String documento,String nombre, String apellido,String telefono)throws ErrorServicio{
       validar(documento,nombre,apellido,telefono);
    Optional<Cliente>respuesta = clienterepositorio.findById(documento);
    if(respuesta.isPresent()){
        Cliente cliente = respuesta.get();
        clienterepositorio.delete(cliente);
    }else{
        throw new ErrorServicio("No se enconcontró un cliente con ese documento");
    } 
        
    }
    
    private void validar(String documento,String nombre, String apellido,String telefono)throws ErrorServicio{
        if(documento == null || documento.isEmpty()){
            throw new ErrorServicio("El documento no puede ser nulo");
        }
        if(nombre == null || documento.isEmpty()){
            throw new ErrorServicio("El nombre no puede ser nulo");
        }
        if(apellido == null || documento.isEmpty()){
            throw new ErrorServicio("El apellido no puede ser nulo");
        }
        if(telefono == null || documento.isEmpty()){
            throw new ErrorServicio("El telefono no puede ser nulo");
        }
       
    }
}

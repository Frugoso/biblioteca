
package egg.mvn.libreria1.servicios;

import egg.mvn.libreria1.entidades.Cliente;
import egg.mvn.libreria1.entidades.Libro;
import egg.mvn.libreria1.entidades.Prestamo;
import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.repositorios.ClienteRepositorio;
import egg.mvn.libreria1.repositorios.LibroRepositorio;
import egg.mvn.libreria1.repositorios.PrestamoRepositorio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PrestamoServicio {
    @Autowired
    private PrestamoRepositorio prestamorepositorio;
    @Autowired
    private ClienteRepositorio clienterepositorio;
    @Autowired
    private LibroRepositorio librorepositorio; 
    
    @Transactional
    public void prestarLibro(String documento,String fechadevolucion, List<String>libros)throws ErrorServicio{
        //recibo dni , fecha devolucion y la lista de libros
        //validamos los datos
       
        //validar(fechadevolucion);
        //buscamos el cliente
        Optional<Cliente>respuesta = clienterepositorio.findById(documento);
        if(respuesta.isPresent()){
        //usamos lista auxiliar    
        List<Libro>listalibros = new ArrayList();
        //uso LocalDate para poder comparar fechas
       
        for(String m : libros){
            
        }
        for(String m : libros){
            Libro libro =librorepositorio.findById(m).get();  
            validarLibro(m); 
            libro.setPrestados(libro.getPrestados()+1);
            libro.setEjemplares(libro.getEjemplares()-1);
            
            librorepositorio.save(libro);
            listalibros.add(libro);
            
            
        }
        Date fechadevolucion2 = new Date(fechadevolucion);
       
        
        Prestamo prestamo = new Prestamo(new Date(),fechadevolucion2, listalibros, clienterepositorio.findById(documento).get());
        
        prestamorepositorio.save(prestamo);
        }else{
            throw new ErrorServicio("No se encontro un cliente con ese documento");
        }
        
        
    }
    @Transactional
    public void devolverLibros(String IdPrestamo)throws ErrorServicio{
        //buscamos el cliente
       // Optional<Cliente>respuesta = clienterepositorio.findById(documento);
        //if(respuesta.isPresent()){
            Optional<Prestamo>respuesta1 = prestamorepositorio.findById(IdPrestamo);   
            if(respuesta1.isPresent()){
                Prestamo prestamo = respuesta1.get();
                if(prestamo.getDevolucion().before(new Date())){
                    prestamo.setMulta(true);
                }else{
                     prestamo.setMulta(false);
                }
                //devolvemos los libros
               for(Libro m : prestamo.getLitalibro()){
                m.setPrestados(m.getPrestados()-1);
                m.setEjemplares(m.getEjemplares()+1);
                librorepositorio.save(m);
                }
               prestamo.setDevuelto(true);
               //guardamos el prestamo con las multas
               prestamorepositorio.save(prestamo);
                
            }else{
                throw new ErrorServicio("El prestamo indicado no existe");
            }
//        }else{
//            throw new ErrorServicio("El cliente idicado no existe");
        }
    
    
//    private void validar(String fechadevolucion, String cliente, List<String>libros)throws ErrorServicio{
////        if(LocalDate.now().isAfter(LocalDate.parse(fechadevolucion)) || fechadevolucion.isEmpty() || fechadevolucion==null){
////            throw new ErrorServicio("La fecha ingresada no es valida");
////        }
//        if(cliente==null){
//            throw new ErrorServicio("Debe ingresar un cliente valido");
//        }
//        if(libros.isEmpty()){
//            throw new ErrorServicio("Debe ingresar uno o más libros validos");
//        }
//    }
    private void validarLibro(String id)throws ErrorServicio{
        if(id == null || id.isEmpty()){
            throw new ErrorServicio("El libro no puede ser nulo");
        }
        Optional<Libro> libro = librorepositorio.findById(id);
        if(libro.get().getEjemplares()==0){
            throw new ErrorServicio("El libro "+libro.get().getTitulo()+", no se encuentra disponible. Hay "+libro.get().getEjemplares()+" ejemplares disponibles");
        }
            
        
    }
}

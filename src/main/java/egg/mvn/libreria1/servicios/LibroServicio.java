
package egg.mvn.libreria1.servicios;

import egg.mvn.libreria1.entidades.Autor;
import egg.mvn.libreria1.entidades.Editorial;
import egg.mvn.libreria1.entidades.Libro;
import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.repositorios.AutorRepositorio;
import egg.mvn.libreria1.repositorios.EditorialRepositorio;
import egg.mvn.libreria1.repositorios.LibroRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {
      @Autowired
      private LibroRepositorio librorepositorio; 
      @Autowired
      private AutorRepositorio autorrepositorio;
      @Autowired
      private EditorialRepositorio editorialrepositorio;
      
      @Transactional
      public void registrar(String isbn, String titulo, Integer anio, Integer ejemplares, String autor, String editorial) throws ErrorServicio{
          validar(isbn,titulo,anio,ejemplares,autor,editorial);
          Optional<Autor> respuesta1 = autorrepositorio.findById(autor);
          Optional<Editorial> respuesta2 = editorialrepositorio.findById(editorial);
          if(respuesta1.isPresent()){
              if(respuesta2.isPresent()){
                  Libro libro = new Libro(isbn, titulo, anio, ejemplares,0, respuesta1.get(), respuesta2.get());
                  librorepositorio.save(libro); 
              }else{
                  throw new ErrorServicio("La editorial ingresada no es valida");
              }
             
          }else{
              throw new ErrorServicio("El autor ingresado no existe en el sistema");
          }
          
          
      }
      @Transactional
      public void eliminar(String isbn) throws ErrorServicio{
          Optional<Libro>respuesta = librorepositorio.findById(isbn);
          if(respuesta.isPresent()){
              librorepositorio.delete(respuesta.get());
          }else{
              throw new ErrorServicio("El isbn indicado no existe");
          }
          
      
      }
      @Transactional
      public void modificar(String isbn, String titulo, Integer anio, Integer ejemplares, String autor, String editorial)throws ErrorServicio{
          validar(isbn,titulo,anio,ejemplares,autor,editorial);
          Optional<Libro>respuesta = librorepositorio.findById(isbn);
          Optional<Autor> respuesta1 = autorrepositorio.findById(autor);
          Optional<Editorial> respuesta2 = editorialrepositorio.findById(editorial);
          System.out.println("---"+autor);
          if(respuesta.isPresent() && respuesta1.isPresent() && respuesta2.isPresent()){
             
              
              Libro libro = respuesta.get();
              libro.setIsbn(isbn);
              libro.setTitulo(titulo);
              libro.setAnio(anio);
              libro.setEjemplares(ejemplares);
//              libro.setPrestados(prestados);
              libro.setAutor(respuesta1.get());
              libro.setEditorial(respuesta2.get());
              librorepositorio.save(libro); 
              
              
          }else{
              throw new ErrorServicio("El isbn indicado no existe, o el autor y editorial indicado no es el correcto");
          }
      }
      
      private void validar(String isbn, String titulo, Integer anio, Integer ejemplares, String autor, String editorial) throws ErrorServicio{
          if (isbn==null || isbn.isEmpty()){
              throw new ErrorServicio("El isbn no puede ser nulo");
          }
          if (titulo==null || titulo.isEmpty()){
              throw new ErrorServicio("El titulo no puede ser nulo");
          }
          if (anio==null || anio==0){
              throw new ErrorServicio("El anio no puede ser nulo");
          }
          if (ejemplares==null || ejemplares==0){
              throw new ErrorServicio("Los ejemplares no pueden ser nulos");
          }
//          if (prestados==null || prestados> ejemplares){
//              throw new ErrorServicio("Los valores para ejemplares prestados son incorrectos");
//          }
          if (autor==null){
              throw new ErrorServicio("El autor ingresado no puede ser nulo");
          }
          if (editorial==null){
              throw new ErrorServicio("La editorial ingresada no puede ser nulo");
          }
      }
    
}


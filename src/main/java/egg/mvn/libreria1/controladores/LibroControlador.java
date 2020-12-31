
package egg.mvn.libreria1.controladores;

import egg.mvn.libreria1.entidades.Autor;
import egg.mvn.libreria1.entidades.Editorial;
import egg.mvn.libreria1.entidades.Libro;
import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.repositorios.AutorRepositorio;
import egg.mvn.libreria1.repositorios.EditorialRepositorio;
import egg.mvn.libreria1.repositorios.LibroRepositorio;
import egg.mvn.libreria1.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LibroControlador {
    @Autowired
    private LibroServicio libroservicio;
    @Autowired
    private AutorRepositorio autorrepositorio;
    @Autowired
    private EditorialRepositorio editorialrepositorio;
    @Autowired
    private LibroRepositorio librorepositorio;
    
    @PostMapping("/registrar_l")
    public String agregarAutor(ModelMap model,@RequestParam String isbn,@RequestParam String titulo,@RequestParam int anio,@RequestParam int ejemplares,@RequestParam String idAutor,@RequestParam String idEditorial){
        try {
            libroservicio.registrar(isbn, titulo, anio, ejemplares, idAutor, idEditorial);
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            model.put("isbn", isbn);
            model.put("titulo", titulo);
            model.put("anio", anio);
            model.put("ejemplares", ejemplares);
            
            return "agregar_Autor.html";
        }
        return "index.html";
}
    
    @GetMapping("/modificar_libro")
    public String modificarLibro(ModelMap model,@RequestParam String id){
        Libro libro = librorepositorio.findById(id).get();
        List<Autor>autores = autorrepositorio.findAll();
        List<Editorial>editoriales = editorialrepositorio.findAll();
        model.put("autores", autores);
        model.put("editoriales", editoriales);
        model.put("libro", libro);
        
        return "modificar_libro.html";
}
    @PostMapping("/actualizar_libro")
    public String actualizarLibro(ModelMap model,@RequestParam String isbn,@RequestParam String titulo,@RequestParam int anio,@RequestParam int ejemplares,@RequestParam String idAutor,@RequestParam String idEditorial){
        try {
           libroservicio.modificar(isbn, titulo, anio, ejemplares, idAutor, idEditorial);
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            model.put("isbn", isbn);
            model.put("titulo", titulo);
            model.put("anio", anio);
            model.put("ejemplares", ejemplares);
            
            return "agregar_Autor.html";
        }
        return "index.html";
}
    
}

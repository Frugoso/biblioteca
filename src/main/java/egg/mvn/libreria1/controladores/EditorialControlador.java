
package egg.mvn.libreria1.controladores;

import egg.mvn.libreria1.entidades.Autor;
import egg.mvn.libreria1.entidades.Editorial;
import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.repositorios.EditorialRepositorio;
import egg.mvn.libreria1.servicios.EditorialServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class EditorialControlador {
    @Autowired
    private EditorialServicio editorialservicio;
    @Autowired
    private EditorialRepositorio editorialrepositorio;
    
    @PostMapping("/registrar_e")
    public String agregarAutor(@RequestParam String nombre){
        try {
            editorialservicio.registrar(nombre);
        } catch (ErrorServicio ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "agregar_Editorial.html";
        }
        return "index.html";
}
     @GetMapping("/modificar_editorial")
    public String modificar_editorial_vista(ModelMap model,@RequestParam String id){
        Editorial editorial = editorialrepositorio.findById(id).get();
        model.put("editorial_id", editorial.getId());
        model.put("editorial_nombre", editorial.getNombre());
        return "modificar_editorial.html";
}
    @PostMapping("/modificar_editorial1")
    public String modificar_autor(@RequestParam String id,@RequestParam String nombre){
        try {
            editorialservicio.modificar(id, nombre);
        } catch (ErrorServicio ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "lista_editorial.html";
        }
        return "index.html";
}
    
    
}

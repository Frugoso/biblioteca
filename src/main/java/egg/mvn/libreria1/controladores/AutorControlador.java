
package egg.mvn.libreria1.controladores;

import egg.mvn.libreria1.entidades.Autor;
import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.repositorios.AutorRepositorio;
import egg.mvn.libreria1.servicios.AutorServicio;
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
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorservicio;
     @Autowired
    private AutorRepositorio autorrepositorio;
    
    @PostMapping("/registrar_a")
    public String agregarAutor(@RequestParam String nombre){
        try {
            autorservicio.registrar(nombre);
        } catch (ErrorServicio ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "agregar_Autor.html";
        }
        return "index.html";
}
    
    @GetMapping("/modificar_autor")
    public String modificar_autor_vista(ModelMap model,@RequestParam String id){
        Autor autor = autorrepositorio.findById(id).get();
        model.put("autor_id", autor.getId());
        model.put("nombre_autor", autor.getNombre());
        return "modificar_autor.html";
}
    @PostMapping("/modificar_autor1")
    public String modificar_autor(@RequestParam String id,@RequestParam String nombre){
        try {
            autorservicio.modificar(id, nombre);
        } catch (ErrorServicio ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "lista_autores.html";
        }
        return "index.html";
}
    

}

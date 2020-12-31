
package egg.mvn.libreria1.controladores;

import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.servicios.UsuarioBibliotecaServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class UsuarioBibliotecaControlador {
    
    @Autowired
    private UsuarioBibliotecaServicio usuarioservicio;
    
    @PostMapping("/registrar")
    public String registrar_u(ModelMap model,@RequestParam String nombre,@RequestParam String email,@RequestParam String clave,@RequestParam String clave1){
        try {
          
            usuarioservicio.registrar(email, nombre, clave, clave1);
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            model.put("nombre",nombre);
            model.put("email",email);
            model.put("clave",clave);
            model.put("clave1",clave1);
            return "registrar.html";
        }
        
        return "login.html";
    }
    
    
}

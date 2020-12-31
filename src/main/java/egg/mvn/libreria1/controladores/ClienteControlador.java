
package egg.mvn.libreria1.controladores;

import egg.mvn.libreria1.entidades.Cliente;
import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.repositorios.ClienteRepositorio;
import egg.mvn.libreria1.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class ClienteControlador {
    @Autowired
    private ClienteServicio clienteservicio; 
    @Autowired
    private ClienteRepositorio clienterepositorio; 
    
    @PostMapping("/registrar_c")
    public String agregarCliente(ModelMap model,@RequestParam String documento,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String telefono){
        try {
            clienteservicio.registrar(documento, nombre, apellido, telefono);
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            model.put("documento", documento);
            model.put("nombre", nombre);
            model.put("apellido", apellido);
            model.put("telefono", telefono);
            
            return "agregar_Cliente.html";
        }
        return "index.html";
}
    
      @GetMapping("/editar_cliente")
        public String editar(ModelMap model,@RequestParam String id){
        Cliente cliente =clienterepositorio.findById(id).get();
        model.put("cliente",cliente);
        return "modificar_cliente.html";
}
    @PostMapping("/actualizar_cliente")
    public String actualizar_cliente(ModelMap model,@RequestParam String documento,@RequestParam String nombre,@RequestParam String apellido,@RequestParam String telefono){
        try {
            clienteservicio.modificar(documento, nombre, apellido, telefono);
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            model.put("documento", documento);
            model.put("nombre", nombre);
            model.put("apellido", apellido);
            model.put("telefono", telefono);
            
            return "agregar_Cliente.html";
        }
        return "index.html";
}
    
}

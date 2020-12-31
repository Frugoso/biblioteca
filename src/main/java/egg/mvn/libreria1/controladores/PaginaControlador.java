
package egg.mvn.libreria1.controladores;


import static com.sun.jmx.snmp.SnmpStatusException.readOnly;
import egg.mvn.libreria1.entidades.Autor;
import egg.mvn.libreria1.entidades.Cliente;
import egg.mvn.libreria1.entidades.Editorial;
import egg.mvn.libreria1.entidades.Libro;
import egg.mvn.libreria1.entidades.Prestamo;
import egg.mvn.libreria1.repositorios.AutorRepositorio;
import egg.mvn.libreria1.repositorios.ClienteRepositorio;
import egg.mvn.libreria1.repositorios.EditorialRepositorio;
import egg.mvn.libreria1.repositorios.LibroRepositorio;
import egg.mvn.libreria1.repositorios.PrestamoRepositorio;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PaginaControlador {

    @Autowired
    private AutorRepositorio autorrepositorio;
    @Autowired
    private EditorialRepositorio editorialrepositorio;
    @Autowired
    private ClienteRepositorio clienterepositorio;
    @Autowired
    private LibroRepositorio librorepositorio;
    @Autowired
    private PrestamoRepositorio prestamorepositorio;

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/")
    public String index(){
        return "index.html";
    }
    
    @GetMapping("/agregar_Autor")
    public String agregarAutor(){
        return "agregar_Autor.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/agregar_Editorial")
    public String agregarEditorial(){
        return "agregar_Editorial.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/prestamo")
    public String prestamo(ModelMap model){
        List<Cliente>clientes = clienterepositorio.findAll();
        List<Libro>libros = librorepositorio.findAll();
        model.put("clientes", clientes);
        model.put("libros", libros);
        
        return "prestamo.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/lista_prestamo")
    public String prestamos(ModelMap model, @RequestParam(required = false) String filtro,@RequestParam(required = false)  String documento){//, @RequestParam String filtro
        
        List<Cliente>cliente=clienterepositorio.findAll();
        if(documento==null)documento="Todos";
        if(filtro==null && (documento==null || documento.equals("Todos"))) filtro="todos";
        
        List<Prestamo>prestamos=prestamorepositorio.findAll();
        if(clienterepositorio.findById(documento).isPresent()){
          prestamos = prestamorepositorio.PrestamosPorCliente(documento);  
        }else{
        switch(filtro){
            case "todos":
              prestamos = prestamorepositorio.findAll();
//              
//                  
//              }
              break;
            case "devueltos":
               prestamos = prestamorepositorio.PrestamosDevueltos();
//               if(clienterepositorio.findById(documento).isPresent()){
//                  prestamos = prestamorepositorio.PrestamosPorCliente(documento);
//              }
               break;
            case "sindevolver":
                prestamos = prestamorepositorio.PrestamosSinDevolver();
//                if(clienterepositorio.findById(documento).isPresent()){
//                  prestamos = prestamorepositorio.PrestamosPorCliente(documento);
//              }
                break;
        }
        }
       
//        if(){
//            prestamos= prestamorepositorio.findAll();
//        }
        
       model.put("clientes", cliente);
      
        model.addAttribute("prestamos",prestamos);
       // System.out.println("---"+filtro);
       
        
        return "lista_prestamo.html";
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/lista_libros")
    public String libros(ModelMap model){

        List<Libro>libros=librorepositorio.findAll();

        model.addAttribute("libros",libros);

        return "lista_libros.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/agregar_Cliente")
    public String agregarCliente(){
        
        
        return "agregar_Cliente.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/agregar_Libro")
    public String agregarLibro(ModelMap model){
        List<Autor>autores = autorrepositorio.findAll();
        List<Editorial>editoriales = editorialrepositorio.findAll();
        model.put("autores", autores);
        model.put("editoriales", editoriales);
        return "agregar_Libro.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/lista_autores")
    public String autores(ModelMap model){
       List<Autor>autores = autorrepositorio.findAll();
       model.put("autores", autores);
        return "lista_autores.html";
    }
     @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/lista_editorial")
    public String editoriales(ModelMap model){
       List<Editorial>editoriales = editorialrepositorio.findAll();
       model.put("editoriales", editoriales);
        return "lista_editorial.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/lista_clientes")
    public String clientes(ModelMap model){
       List<Cliente>clientes = clienterepositorio.findAll();
       model.put("clientes1", clientes);
        return "lista_clientes.html";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,ModelMap model){
        if(error!=null){
            model.put("error","Nombre de usuario o clave incorrecto");
        }
        
        return "login.html";
    
}
    @GetMapping("/registrar")
    public String registrar(){
        return "registrar.html";
    
}
     @GetMapping("/estadisticas")
     public String estadisticas(ModelMap model){
      List<String[]>pres= prestamorepositorio.PrestamosAgrupadosClientes();
       List<String[]>mul= prestamorepositorio.MultasAcumClientes();
      List<String[]>total= new ArrayList();
       
     
      for(int i=0; i<prestamorepositorio.PrestamosAgrupadosClientes().size();i++){
          String[] x = new String [4];
          x[0]=pres.get(i)[0];
          x[1]=pres.get(i)[1];
          for(int j=0;j<prestamorepositorio.MultasAcumClientes().size();j++){
              if(pres.get(i)[0].equals(mul.get(j)[0])){
                  x[2]=mul.get(j)[1];
                  Float porc = (Float.parseFloat(mul.get(j)[1])/Float.parseFloat(pres.get(i)[1]))*100;
                  
                  x[3]=Float.toString(porc);
                  System.out.println("Porcentaa--"+porc);
              }
              
          }
          
          
          total.add(x);
          
      }
         for(String[] ss : total){
             System.out.println("nombre: "+ss[0]+" prestados: "+ss[1]+" multas "+ss[2]);
         }
      model.put("clientes",total);
    
        return "estadisticas.html";
}
     @GetMapping("/debate")
    public String debate(){
        return "debate.html";
    
}
}
   


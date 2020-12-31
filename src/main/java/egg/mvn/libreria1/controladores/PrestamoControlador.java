
package egg.mvn.libreria1.controladores;

import egg.mvn.libreria1.entidades.Prestamo;
import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.repositorios.ClienteRepositorio;
import egg.mvn.libreria1.repositorios.LibroRepositorio;
import egg.mvn.libreria1.repositorios.PrestamoRepositorio;
import egg.mvn.libreria1.servicios.PrestamoServicio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//csv import--
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class PrestamoControlador {
    
    @Autowired
    private PrestamoServicio prestamoservicio;
    
    @Autowired
    private PrestamoRepositorio prestamorepositorio;
    
    @Autowired
    private ClienteRepositorio clienterepositorio;
    
    @Autowired
    private LibroRepositorio librorepositorio;
//    @Autowired
//    private Date fechahoy = new Date();
    
    @PostMapping("/registrar_prestamo")
    public String registrar_p(ModelMap model,@RequestParam String documento,@RequestParam String isbn,@RequestParam String isbn1,@RequestParam String isbn2,@RequestParam String fecha){
        try {
            
          List<String> listalibros= new ArrayList();
          listalibros.add(isbn);
          
          if(!isbn1.equals("-")){
            listalibros.add(isbn1);  
          }
          if(!isbn2.equals("-")){
            listalibros.add(isbn2);  
          }
//            System.out.println(documento);
//            System.out.println(fecha);
//            for( String m : listalibros){
//                System.out.println(m);
//            }
        
          String fecha1 = fecha.replaceAll("-", "/");
          
          prestamoservicio.prestarLibro(documento, fecha1, listalibros);
//           model.put("fecha",fechahoy);
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            
            return "prestamo.html";
        }
        
        return "index.html";
    }
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/devolver")
    public String devolver(ModelMap model,@RequestParam String id){
        try {
            if(prestamorepositorio.findById(id).get().isDevuelto()==false){
                 prestamoservicio.devolverLibros(id);
            }
         
            
        } catch (ErrorServicio ex) {
            model.put("error", ex.getMessage());
            
            return "index.html";
        }
        
        return "index.html";
    }
    //   /listar
  
    @PostMapping("/listar")
    public String listar(ModelMap model,@RequestParam String opcion) throws IOException{

                String SAMPLE_CSV_FILE = "C:\\Users\\panch\\Downloads\\"+new Date().getDay()+"-"+new Date().getMonth()+"ejemplo.csv";  
             
                 try (
              
                 
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE));

            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.EXCEL
                    .withHeader("Cliente", "fecha", "devolucion", "multa"));
                         ){
                     List<Prestamo> prestamos = prestamorepositorio.findAll();
                     switch(opcion){
                         case "todos":
                             prestamos = prestamorepositorio.findAll();
                             break;
                         case "devueltos":
                             prestamos = prestamorepositorio.PrestamosDevueltos();
                             break;
                         case "sindevolver":
                             prestamos = prestamorepositorio.PrestamosSinDevolver();
                             break;
                     }
            //List<String> valores = new ArrayList();
            
            for(Prestamo p : prestamos){
               
             csvPrinter.printRecord(p.getCliente().getNombre().concat(" "+p.getCliente().getApellido()), p.getFecha().toString(),p.isDevuelto(),p.isMulta());   
            }
           
            
            csvPrinter.close();            
        }
//              ------- opcion 2try {
//                    FileWriter fileWriter = new FileWriter("./lista_prestamos.csv", true);
//                    try (CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.RFC4180)) {
//                     
//                    //HEADER record
//                    csvPrinter.printRecord("ID", "fecha", "devolucion", "multa");
//                    //DATA records
//                    List<Prestamo> prestamos = prestamorepositorio.findAll();
//                        for(Prestamo p : prestamos){
//                         csvPrinter.printRecord(p.getId(), p.getFecha().toString(),p.isDevuelto(),p.isMulta());   
//                        }
//                    csvPrinter.flush();
//                    }
//                    } catch (IOException e) {
//                    e.printStackTrace();
//                    } opcion 2  -------------------
//               break;
//           case "devueltos":
//               
//               break;
//           case "sindevolver":
//               
//               break;
//       }
        return "index.html";
    }
}


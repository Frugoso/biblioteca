package egg.mvn.libreria1.servicios;

import egg.mvn.libreria1.entidades.UsuarioBiblioteca;
import egg.mvn.libreria1.errores.ErrorServicio;
import egg.mvn.libreria1.repositorios.UsuarioBibliotecaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Service
public class UsuarioBibliotecaServicio implements UserDetailsService {

    @Autowired
    private UsuarioBibliotecaRepositorio usuariorepositorio;
//posible error de impotacion de paquete
    @Transactional
    public void registrar(String email, String nombre, String clave, String clave1) throws ErrorServicio {
        validarRegistro(email, nombre, clave, clave1);
        String clavesegura = new BCryptPasswordEncoder().encode(clave);
        
        UsuarioBiblioteca usuario = new UsuarioBiblioteca(email, nombre, clavesegura);
        usuariorepositorio.save(usuario);
    }

    public void validarRegistro(String email, String nombre, String clave, String clave1) throws ErrorServicio {
        if (email == null || email.isEmpty()) {
            throw new ErrorServicio("El email no puede ser nullo");
        }
        Optional<UsuarioBiblioteca> respuesta = usuariorepositorio.findById(email);

        if (respuesta.isPresent()) {
            throw new ErrorServicio("El usuario ya existe");
        }
        if (clave == null || clave.isEmpty()) {
            throw new ErrorServicio("La clave 1 no puede ser nula");
        }
        if (clave1 == null || clave1.isEmpty()) {
            throw new ErrorServicio("La clave 2 no puede ser nula");
        }
        if (!clave1.equals(clave)) {
            throw new ErrorServicio("Las claves deben ser iguales");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del establecimiento no puede ser nulo");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        UsuarioBiblioteca usuario = usuariorepositorio.findById(email).get();
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList();
            GrantedAuthority permiso = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(permiso);
            ServletRequestAttributes attr=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpSession session= attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", usuario);
            
            User user = new User(usuario.getEmail(), usuario.getClave(), permisos);
            return user;
        } else {
            return null;
        }
    }

}

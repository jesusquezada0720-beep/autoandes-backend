package pe.edu.upeu.autoandes.config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.autoandes.entity.*;
import pe.edu.upeu.autoandes.repository.*;
import java.util.Map;
@Component @Profile("!test") public class UsuarioInicializador implements CommandLineRunner {
 private final UsuarioRepository usuarios;private final RolRepository roles;private final PasswordEncoder encoder;
 public UsuarioInicializador(UsuarioRepository u,RolRepository r,PasswordEncoder e){usuarios=u;roles=r;encoder=e;}
 @Override @Transactional public void run(String...args){crear("admin","Administrador AutoAndes","ADMIN",true);crear("asesor","Asesor AutoAndes","ASESOR",true);crear("jefe","Jefe de taller AutoAndes","JEFE_TALLER",true);crear("inactivo","Usuario inactivo","ASESOR",false);}
 private void crear(String username,String nombre,String role,boolean activo){if(usuarios.findByUsernameIgnoreCase(username).isPresent())return;Rol r=roles.findByNombre(role).orElseThrow(()->new IllegalStateException("Falta rol inicial " + role));Usuario u=new Usuario();u.setUsername(username);u.setNombreCompleto(nombre);u.setPassword(encoder.encode("Auto2026*"));u.setActivo(activo?1:0);u.getRoles().add(r);usuarios.save(u);}
}

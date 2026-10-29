package pe.edu.upeu.autoandes.security;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import pe.edu.upeu.autoandes.entity.Usuario;
import pe.edu.upeu.autoandes.repository.UsuarioRepository;
import java.util.*;
@Service public class UsuarioDetailsService implements UserDetailsService {
 private final UsuarioRepository repo; public UsuarioDetailsService(UsuarioRepository r){repo=r;}
 @Override public UserDetails loadUserByUsername(String username){Usuario u=repo.findByUsernameIgnoreCase(username).orElseThrow(()->new UsernameNotFoundException("Usuario no encontrado"));Set<String> roles=new HashSet<>();u.getRoles().forEach(r->roles.add(r.getNombre()));Set<String> authorities=PermisosPorRol.deRoles(roles);roles.forEach(r->authorities.add("ROLE_"+r));return User.withUsername(u.getUsername()).password(u.getPassword()).disabled(u.getActivo()==null||u.getActivo()!=1).authorities(authorities.stream().map(SimpleGrantedAuthority::new).toList()).build();}
}

package pe.edu.upeu.autoandes.service;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import pe.edu.upeu.autoandes.dto.*;
import pe.edu.upeu.autoandes.entity.Usuario;
import pe.edu.upeu.autoandes.exception.ReglaNegocioException;
import pe.edu.upeu.autoandes.repository.UsuarioRepository;
import pe.edu.upeu.autoandes.security.*;
import java.util.Set;
import java.util.stream.Collectors;
@Service public class AuthService {
 private final AuthenticationManager manager;private final UsuarioRepository usuarios;private final JwtService jwt;
 public AuthService(AuthenticationManager m,UsuarioRepository u,JwtService j){manager=m;usuarios=u;jwt=j;}
 public LoginResponse login(LoginRequest request){try{Authentication a=manager.authenticate(new UsernamePasswordAuthenticationToken(request.username(),request.password()));Usuario u=usuarios.findByUsernameIgnoreCase(a.getName()).orElseThrow();Set<String> roles=u.getRoles().stream().map(r->r.getNombre()).collect(Collectors.toSet());return new LoginResponse("Bearer",jwt.emitir(u),jwt.expiracion(),u.getNombreCompleto(),roles,PermisosPorRol.deRoles(roles));}catch(AuthenticationException ex){throw new ReglaNegocioException(org.springframework.http.HttpStatus.UNAUTHORIZED,"Usuario o contraseña incorrectos");}}
 public UsuarioActualResponse me(String username){Usuario u=usuarios.findByUsernameIgnoreCase(username).orElseThrow(()->ReglaNegocioException.noEncontrado("Usuario no encontrado"));Set<String> roles=u.getRoles().stream().map(r->r.getNombre()).collect(Collectors.toSet());return new UsuarioActualResponse(u.getUsername(),u.getNombreCompleto(),roles,PermisosPorRol.deRoles(roles));}
}

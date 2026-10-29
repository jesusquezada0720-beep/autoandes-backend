package pe.edu.upeu.autoandes.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.edu.upeu.autoandes.entity.Usuario;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.*;
@Service public class JwtService {
 @Value("${app.jwt.secret}") private String secret;
 @Value("${app.jwt.expiration-seconds:3600}") private long expiration;
 private Key key(){return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}
 public long expiracion(){return expiration;}
 public String emitir(Usuario usuario){Set<String> roles=new HashSet<>();usuario.getRoles().forEach(r->roles.add(r.getNombre()));return Jwts.builder().subject(usuario.getUsername()).claim("nombreCompleto",usuario.getNombreCompleto()).claim("roles",roles).claim("permisos",PermisosPorRol.deRoles(roles)).issuedAt(new Date()).expiration(Date.from(Instant.now().plusSeconds(expiration))).signWith(key()).compact();}
 public Claims validar(String token){return Jwts.parser().verifyWith((javax.crypto.SecretKey)key()).build().parseSignedClaims(token).getPayload();}
}

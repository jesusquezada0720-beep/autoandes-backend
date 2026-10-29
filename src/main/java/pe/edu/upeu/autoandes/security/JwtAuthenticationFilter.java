package pe.edu.upeu.autoandes.security;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
@Component public class JwtAuthenticationFilter extends OncePerRequestFilter {
 private final JwtService jwt;private final UsuarioDetailsService users;
 public JwtAuthenticationFilter(JwtService j,UsuarioDetailsService u){jwt=j;users=u;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String h=req.getHeader("Authorization");if(h!=null&&h.startsWith("Bearer ")){try{Claims c=jwt.validar(h.substring(7));String username=c.getSubject();if(SecurityContextHolder.getContext().getAuthentication()==null){UserDetails u=users.loadUserByUsername(username);if(u.isEnabled()){var auth=new UsernamePasswordAuthenticationToken(u,null,u.getAuthorities());auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));SecurityContextHolder.getContext().setAuthentication(auth);}}}catch(RuntimeException ex){SecurityContextHolder.clearContext();}}
  chain.doFilter(req,res);
 }
}

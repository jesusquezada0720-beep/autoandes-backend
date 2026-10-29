package pe.edu.upeu.autoandes.dto;
import java.util.Set;
public record LoginResponse(String tipo,String token,long expiraEnSegundos,String nombreCompleto,Set<String> roles,Set<String> permisos) { }

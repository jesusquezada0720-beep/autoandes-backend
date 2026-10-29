package pe.edu.upeu.autoandes.dto;
import java.util.Set;
public record UsuarioActualResponse(String username,String nombreCompleto,Set<String> roles,Set<String> permisos) { }

package pe.edu.upeu.autoandes.dto;
import java.time.Instant;
public record ErrorResponse(Instant timestamp,int status,String error,String mensaje,String ruta) { }

package pe.edu.upeu.autoandes.dto;
import java.time.Instant;
import java.util.Map;
public record ValidationErrorResponse(Instant timestamp,int status,String error,String mensaje,String ruta,Map<String,String> campos) { }

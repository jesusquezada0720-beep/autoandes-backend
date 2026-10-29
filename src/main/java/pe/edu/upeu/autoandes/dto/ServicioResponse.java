package pe.edu.upeu.autoandes.dto;
import java.math.BigDecimal;
public record ServicioResponse(Long id,String codigo,String nombre,BigDecimal precio,Integer duracionMin,boolean activo) { }

package pe.edu.upeu.autoandes.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
public record OrdenResponse(Long id,String numero,LocalDateTime fecha,String placa,String estado,Long kilometraje,BigDecimal total,String usuarioRegistro,String motivoAnulacion,List<DetalleResponse> detalles) {
 public record DetalleResponse(String codigoServicio,String nombreServicio,Integer cantidad,BigDecimal precioUnitario,BigDecimal subtotal) { }
}

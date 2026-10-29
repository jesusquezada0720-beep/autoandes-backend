package pe.edu.upeu.autoandes.mapper;
import org.springframework.stereotype.Component;
import pe.edu.upeu.autoandes.dto.OrdenResponse;
import pe.edu.upeu.autoandes.entity.OrdenServicio;
import java.util.List;
@Component public class OrdenMapper {
 public OrdenResponse toResponse(OrdenServicio o){List<OrdenResponse.DetalleResponse> detalles=o.getDetalles().stream().map(d->new OrdenResponse.DetalleResponse(d.getServicio().getCodigo(),d.getServicio().getNombre(),d.getCantidad(),d.getPrecioUnitario(),d.getSubtotal())).toList();return new OrdenResponse(o.getId(),o.getNumero(),o.getFecha(),o.getVehiculo().getPlaca(),o.getEstado().name(),o.getKilometraje(),o.getTotal(),o.getUsuarioRegistro(),o.getMotivoAnulacion(),detalles);}
}

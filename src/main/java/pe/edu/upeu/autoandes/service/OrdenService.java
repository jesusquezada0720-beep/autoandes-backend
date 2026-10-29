package pe.edu.upeu.autoandes.service;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.autoandes.dto.*;
import pe.edu.upeu.autoandes.entity.*;
import pe.edu.upeu.autoandes.exception.ReglaNegocioException;
import pe.edu.upeu.autoandes.mapper.OrdenMapper;
import pe.edu.upeu.autoandes.enums.EstadoOrden;
import pe.edu.upeu.autoandes.repository.*;
import java.math.BigDecimal;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;
@Service public class OrdenService {
 private final OrdenRepository ordenes;private final VehiculoRepository vehiculos;private final ServicioRepository servicios;private final OrdenMapper mapper;
 public OrdenService(OrdenRepository o,VehiculoRepository v,ServicioRepository s,OrdenMapper m){ordenes=o;vehiculos=v;servicios=s;mapper=m;}
 @Transactional public OrdenResponse crear(OrdenRequest x,String username){if(x.detalles().stream().map(DetalleOrdenRequest::servicioId).distinct().count()!=x.detalles().size())throw new ReglaNegocioException(HttpStatus.BAD_REQUEST,"No se puede repetir un servicio en la orden");Vehiculo v=vehiculos.findById(x.vehiculoId()).orElseThrow(()->ReglaNegocioException.noEncontrado("Vehículo no encontrado"));Long ultimo=ordenes.ultimoKilometraje(v.getId(),EstadoOrden.REGISTRADA);if(ultimo!=null&&x.kilometraje()<ultimo)throw ReglaNegocioException.conflicto("El kilometraje debe ser igual o mayor al último registrado: "+ultimo);
  OrdenServicio o=new OrdenServicio();o.setNumero(String.format("OS-%06d",ordenes.siguienteNumero()));o.setFecha(LocalDateTime.now());o.setVehiculo(v);o.setKilometraje(x.kilometraje());o.setEstado(EstadoOrden.REGISTRADA);o.setUsuarioRegistro(username);BigDecimal total=BigDecimal.ZERO;
  for(DetalleOrdenRequest d:x.detalles()){Servicio s=servicios.findById(d.servicioId()).orElseThrow(()->ReglaNegocioException.noEncontrado("Servicio no encontrado: "+d.servicioId()));if(s.getActivo()==null||s.getActivo()!=1)throw ReglaNegocioException.conflicto("El servicio "+s.getCodigo()+" está inactivo");DetalleOrden det=new DetalleOrden();det.setOrden(o);det.setServicio(s);det.setCantidad(d.cantidad());det.setPrecioUnitario(s.getPrecio());det.setSubtotal(s.getPrecio().multiply(BigDecimal.valueOf(d.cantidad())));total=total.add(det.getSubtotal());o.getDetalles().add(det);}
  o.setTotal(total);return mapper.toResponse(ordenes.saveAndFlush(o));
 }
 @Transactional(readOnly=true) public List<OrdenResponse> listar(String placa,EstadoOrden estado,LocalDate desde,LocalDate hasta){LocalDateTime d=desde==null?null:desde.atStartOfDay();LocalDateTime h=hasta==null?null:hasta.plusDays(1).atStartOfDay();if(desde!=null&&hasta!=null&&desde.isAfter(hasta))throw new ReglaNegocioException(HttpStatus.BAD_REQUEST,"La fecha desde no puede ser posterior a hasta");return ordenes.buscar(placa,estado,d,h).stream().map(mapper::toResponse).distinct().toList();}
 @Transactional(readOnly=true) public OrdenResponse obtener(Long id){return ordenes.buscarPorId(id).map(mapper::toResponse).orElseThrow(()->ReglaNegocioException.noEncontrado("Orden no encontrada"));}
 @Transactional public OrdenResponse anular(Long id,String motivo){if(motivo==null||motivo.trim().length()<10||motivo.trim().length()>200)throw new ReglaNegocioException(HttpStatus.BAD_REQUEST,"El motivo debe tener entre 10 y 200 caracteres");OrdenServicio o=ordenes.buscarPorId(id).orElseThrow(()->ReglaNegocioException.noEncontrado("Orden no encontrada"));if(o.getEstado()!=EstadoOrden.REGISTRADA)throw ReglaNegocioException.conflicto("Solo se pueden anular órdenes REGISTRADA");if(!o.getFecha().toLocalDate().equals(LocalDate.now()))throw ReglaNegocioException.conflicto("Solo se pueden anular órdenes del día actual");o.setEstado(EstadoOrden.ANULADA);o.setMotivoAnulacion(motivo.trim());return mapper.toResponse(o);}
}

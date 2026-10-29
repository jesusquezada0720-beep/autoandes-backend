package pe.edu.upeu.autoandes.repository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.autoandes.entity.DetalleOrden;
import pe.edu.upeu.autoandes.enums.EstadoOrden;
import java.time.LocalDateTime;
import java.util.List;
public interface ReporteRepository extends JpaRepository<DetalleOrden,Long> {
 interface IngresoServicio { String getCodigo(); String getNombre(); Long getCantidadTotal(); java.math.BigDecimal getMontoTotal(); }
 @Query("select s.codigo as codigo, s.nombre as nombre, sum(d.cantidad) as cantidadTotal, sum(d.subtotal) as montoTotal from DetalleOrden d join d.servicio s join d.orden o where o.estado=:estado and o.fecha>=:desde and o.fecha<:hasta group by s.codigo,s.nombre order by sum(d.subtotal) desc")
 List<IngresoServicio> ingresos(@Param("desde") LocalDateTime desde,@Param("hasta") LocalDateTime hasta,@Param("estado") EstadoOrden estado);
}

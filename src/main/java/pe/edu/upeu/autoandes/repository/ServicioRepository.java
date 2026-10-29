package pe.edu.upeu.autoandes.repository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.autoandes.entity.Servicio;
public interface ServicioRepository extends JpaRepository<Servicio,Long> {
 boolean existsByCodigoIgnoreCase(String codigo);
 boolean existsByCodigoIgnoreCaseAndIdNot(String codigo, Long id);
 @Query("select count(d) > 0 from DetalleOrden d where d.servicio.id=:id") boolean estaEnOrdenes(@Param("id") Long id);
}

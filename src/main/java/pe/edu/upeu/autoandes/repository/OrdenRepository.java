package pe.edu.upeu.autoandes.repository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.autoandes.entity.OrdenServicio;
import pe.edu.upeu.autoandes.enums.EstadoOrden;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
public interface OrdenRepository extends JpaRepository<OrdenServicio,Long> {
 @Query("select o from OrdenServicio o join fetch o.vehiculo v join fetch v.cliente left join fetch o.detalles d left join fetch d.servicio where (:placa is null or upper(v.placa)=upper(:placa)) and (:estado is null or o.estado=:estado) and (:desde is null or o.fecha>=:desde) and (:hasta is null or o.fecha<:hasta) order by o.fecha desc")
 List<OrdenServicio> buscar(@Param("placa") String placa,@Param("estado") EstadoOrden estado,@Param("desde") LocalDateTime desde,@Param("hasta") LocalDateTime hasta);
 @Query("select o from OrdenServicio o join fetch o.vehiculo v join fetch v.cliente left join fetch o.detalles d left join fetch d.servicio where o.id=:id") Optional<OrdenServicio> buscarPorId(@Param("id") Long id);
 @Query("select max(o.kilometraje) from OrdenServicio o where o.vehiculo.id=:vehiculoId and o.estado=:estado") Long ultimoKilometraje(@Param("vehiculoId") Long vehiculoId,@Param("estado") EstadoOrden estado);
 @Query(value="SELECT seq_numero_orden.NEXTVAL FROM dual",nativeQuery=true) Long siguienteNumero();
}

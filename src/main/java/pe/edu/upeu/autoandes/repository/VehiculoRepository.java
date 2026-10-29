package pe.edu.upeu.autoandes.repository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import pe.edu.upeu.autoandes.entity.Vehiculo;
import java.util.List;
public interface VehiculoRepository extends JpaRepository<Vehiculo,Long> {
 @Override @EntityGraph(attributePaths="cliente") List<Vehiculo> findAll();
 @EntityGraph(attributePaths="cliente") List<Vehiculo> findByClienteId(Long clienteId);
 @EntityGraph(attributePaths="cliente") java.util.Optional<Vehiculo> findWithClienteById(Long id);
 boolean existsByPlacaIgnoreCase(String placa);
 boolean existsByPlacaIgnoreCaseAndIdNot(String placa,Long id);
 @Query("select count(o) > 0 from OrdenServicio o where o.vehiculo.id=:id") boolean tieneOrdenes(@Param("id") Long id);
}

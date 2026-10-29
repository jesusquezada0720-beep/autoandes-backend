package pe.edu.upeu.autoandes.service;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.autoandes.dto.VehiculoRequest;
import pe.edu.upeu.autoandes.entity.*;
import pe.edu.upeu.autoandes.exception.ReglaNegocioException;
import pe.edu.upeu.autoandes.repository.*;
import java.util.List;
@Service public class VehiculoService {
 private static final Logger log=LoggerFactory.getLogger(VehiculoService.class);private final VehiculoRepository repo;private final ClienteRepository clientes;
 public VehiculoService(VehiculoRepository r,ClienteRepository c){repo=r;clientes=c;}
 public List<Vehiculo> listar(Long clienteId){return clienteId==null?repo.findAll():repo.findByClienteId(clienteId);}
 public Vehiculo obtener(Long id){return repo.findWithClienteById(id).orElseThrow(()->ReglaNegocioException.noEncontrado("Vehículo no encontrado"));}
 @Transactional public Vehiculo crear(VehiculoRequest x){if(repo.existsByPlacaIgnoreCase(x.placa()))throw ReglaNegocioException.conflicto("La placa ya existe");Vehiculo v=new Vehiculo();aplicar(v,x);Vehiculo saved=repo.save(v);log.info("Vehículo creado id={}",saved.getId());return saved;}
 @Transactional public Vehiculo actualizar(Long id,VehiculoRequest x){Vehiculo v=obtener(id);if(repo.existsByPlacaIgnoreCaseAndIdNot(x.placa(),id))throw ReglaNegocioException.conflicto("La placa ya existe");aplicar(v,x);log.info("Vehículo actualizado id={}",id);return v;}
 @Transactional public void eliminar(Long id){Vehiculo v=obtener(id);if(repo.tieneOrdenes(id))throw ReglaNegocioException.conflicto("El vehículo tiene órdenes asociadas");repo.delete(v);log.info("Vehículo eliminado id={}",id);}
 private void aplicar(Vehiculo v,VehiculoRequest x){Cliente c=clientes.findById(x.clienteId()).orElseThrow(()->ReglaNegocioException.noEncontrado("Cliente no encontrado"));v.setPlaca(x.placa().toUpperCase());v.setMarca(x.marca().trim());v.setModelo(x.modelo().trim());v.setAnio(x.anio());v.setCliente(c);}
}

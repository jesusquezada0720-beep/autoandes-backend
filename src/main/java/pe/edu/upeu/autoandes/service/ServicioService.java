package pe.edu.upeu.autoandes.service;
import org.slf4j.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.autoandes.dto.ServicioRequest;
import pe.edu.upeu.autoandes.entity.Servicio;
import pe.edu.upeu.autoandes.exception.ReglaNegocioException;
import pe.edu.upeu.autoandes.repository.ServicioRepository;
import java.util.List;
@Service public class ServicioService {
 private static final Logger log=LoggerFactory.getLogger(ServicioService.class); private final ServicioRepository repo;
 public ServicioService(ServicioRepository r){repo=r;}
 public List<Servicio> listar(){return repo.findAll();}
 public Servicio obtener(Long id){return repo.findById(id).orElseThrow(()->ReglaNegocioException.noEncontrado("Servicio no encontrado"));}
 @Transactional public Servicio crear(ServicioRequest x){if(repo.existsByCodigoIgnoreCase(x.codigo()))throw ReglaNegocioException.conflicto("El código del servicio ya existe");Servicio s=new Servicio();aplicar(s,x);s.setActivo(1);Servicio saved=repo.save(s);log.info("Servicio creado id={}",saved.getId());return saved;}
 @Transactional public Servicio actualizar(Long id,ServicioRequest x){Servicio s=obtener(id);if(repo.existsByCodigoIgnoreCaseAndIdNot(x.codigo(),id))throw ReglaNegocioException.conflicto("El código del servicio ya existe");aplicar(s,x);log.info("Servicio actualizado id={}",id);return s;}
 @Transactional public void eliminar(Long id){Servicio s=obtener(id);if(repo.estaEnOrdenes(id))throw ReglaNegocioException.conflicto("El servicio está usado por órdenes; desactívelo");repo.delete(s);log.info("Servicio eliminado id={}",id);}
 @Transactional public Servicio cambiarEstado(Long id,Boolean activo){Servicio s=obtener(id);s.setActivo(Boolean.TRUE.equals(activo)?1:0);log.info("Estado de servicio actualizado id={} activo={}",id,activo);return s;}
 private void aplicar(Servicio s,ServicioRequest x){s.setCodigo(x.codigo().toUpperCase());s.setNombre(x.nombre().trim());s.setPrecio(x.precio());s.setDuracionMin(x.duracionMin());}
}

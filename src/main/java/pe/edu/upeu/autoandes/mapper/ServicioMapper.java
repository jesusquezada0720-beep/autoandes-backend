package pe.edu.upeu.autoandes.mapper;
import org.springframework.stereotype.Component;
import pe.edu.upeu.autoandes.dto.ServicioResponse;
import pe.edu.upeu.autoandes.entity.Servicio;
@Component public class ServicioMapper {
 public ServicioResponse toResponse(Servicio s){return new ServicioResponse(s.getId(),s.getCodigo(),s.getNombre(),s.getPrecio(),s.getDuracionMin(),s.getActivo()!=null&&s.getActivo()==1);}
}

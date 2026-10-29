package pe.edu.upeu.autoandes.mapper;
import org.springframework.stereotype.Component;
import pe.edu.upeu.autoandes.dto.ClienteResponse;
import pe.edu.upeu.autoandes.entity.Cliente;
@Component public class ClienteMapper {
 public ClienteResponse toResponse(Cliente c){return new ClienteResponse(c.getId(),c.getDocumento(),c.getNombres(),c.getTelefono());}
}

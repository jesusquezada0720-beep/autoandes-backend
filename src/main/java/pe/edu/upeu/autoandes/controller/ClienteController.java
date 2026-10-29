package pe.edu.upeu.autoandes.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.autoandes.dto.ClienteResponse;
import pe.edu.upeu.autoandes.mapper.ClienteMapper;
import pe.edu.upeu.autoandes.service.ClienteService;
import java.util.List;
@RestController @RequestMapping("/api/v1/clientes") @SecurityRequirement(name="bearerAuth") public class ClienteController {
 private final ClienteService service;private final ClienteMapper mapper;public ClienteController(ClienteService s,ClienteMapper m){service=s;mapper=m;}
 @GetMapping @PreAuthorize("hasAuthority('VEHICULO_LEER')") public List<ClienteResponse> listar(){return service.listar().stream().map(mapper::toResponse).toList();}
}

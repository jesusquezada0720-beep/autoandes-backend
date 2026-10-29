package pe.edu.upeu.autoandes.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.autoandes.dto.*;
import pe.edu.upeu.autoandes.entity.Servicio;
import pe.edu.upeu.autoandes.mapper.ServicioMapper;
import pe.edu.upeu.autoandes.service.ServicioService;
import java.util.List;
@RestController @RequestMapping("/api/v1/servicios") @SecurityRequirement(name="bearerAuth") public class ServicioController {
 private final ServicioService service;private final ServicioMapper mapper;public ServicioController(ServicioService s,ServicioMapper m){service=s;mapper=m;}
 @GetMapping @PreAuthorize("hasAuthority('SERVICIO_LEER')") public List<ServicioResponse> listar(){return service.listar().stream().map(mapper::toResponse).toList();}
 @GetMapping("/{id}") @PreAuthorize("hasAuthority('SERVICIO_LEER')") public ServicioResponse obtener(@PathVariable Long id){return mapper.toResponse(service.obtener(id));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAuthority('SERVICIO_ESCRIBIR')") public ServicioResponse crear(@Valid @RequestBody ServicioRequest x){return mapper.toResponse(service.crear(x));}
 @PutMapping("/{id}") @PreAuthorize("hasAuthority('SERVICIO_ESCRIBIR')") public ServicioResponse actualizar(@PathVariable Long id,@Valid @RequestBody ServicioRequest x){return mapper.toResponse(service.actualizar(id,x));}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @PreAuthorize("hasAuthority('SERVICIO_ESCRIBIR')") public void eliminar(@PathVariable Long id){service.eliminar(id);}
 public record EstadoRequest(@jakarta.validation.constraints.NotNull Boolean activo){}
 @PatchMapping("/{id}/estado") @PreAuthorize("hasAuthority('SERVICIO_ESCRIBIR')") public ServicioResponse estado(@PathVariable Long id,@Valid @RequestBody EstadoRequest x){return mapper.toResponse(service.cambiarEstado(id,x.activo()));}
}

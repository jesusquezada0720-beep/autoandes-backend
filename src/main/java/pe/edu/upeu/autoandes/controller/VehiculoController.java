package pe.edu.upeu.autoandes.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.autoandes.dto.*;
import pe.edu.upeu.autoandes.entity.Vehiculo;
import pe.edu.upeu.autoandes.mapper.VehiculoMapper;
import pe.edu.upeu.autoandes.service.VehiculoService;
import java.util.List;
@RestController @RequestMapping("/api/v1/vehiculos") @SecurityRequirement(name="bearerAuth") public class VehiculoController {
 private final VehiculoService service;private final VehiculoMapper mapper;public VehiculoController(VehiculoService s,VehiculoMapper m){service=s;mapper=m;}
 @GetMapping @PreAuthorize("hasAuthority('VEHICULO_LEER')") public List<VehiculoResponseDTO> listar(@RequestParam(required=false) Long clienteId){return service.listar(clienteId).stream().map(mapper::toResponse).toList();}
 @GetMapping("/{id}") @PreAuthorize("hasAuthority('VEHICULO_LEER')") public VehiculoResponseDTO obtener(@PathVariable Long id){return mapper.toResponse(service.obtener(id));}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAuthority('VEHICULO_ESCRIBIR')") public VehiculoResponseDTO crear(@Valid @RequestBody VehiculoRequest x){return mapper.toResponse(service.crear(x));}
 @PutMapping("/{id}") @PreAuthorize("hasAuthority('VEHICULO_ESCRIBIR')") public VehiculoResponseDTO actualizar(@PathVariable Long id,@Valid @RequestBody VehiculoRequest x){return mapper.toResponse(service.actualizar(id,x));}
 @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @PreAuthorize("hasAuthority('VEHICULO_ESCRIBIR')") public void eliminar(@PathVariable Long id){service.eliminar(id);}
}

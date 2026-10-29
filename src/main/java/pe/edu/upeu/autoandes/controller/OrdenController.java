package pe.edu.upeu.autoandes.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.autoandes.dto.*;
import pe.edu.upeu.autoandes.service.OrdenService;
import pe.edu.upeu.autoandes.enums.EstadoOrden;
import java.time.LocalDate;
import java.util.List;
@RestController @RequestMapping("/api/v1/ordenes") @SecurityRequirement(name="bearerAuth") public class OrdenController {
 private final OrdenService service;public OrdenController(OrdenService s){service=s;}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAuthority('ORDEN_REGISTRAR')") public OrdenResponse crear(@Valid @RequestBody OrdenRequest x,Authentication a){return service.crear(x,a.getName());}
 @GetMapping @PreAuthorize("hasAuthority('ORDEN_LEER')") public List<OrdenResponse> listar(@RequestParam(required=false) String placa,@RequestParam(required=false) EstadoOrden estado,@RequestParam(required=false) LocalDate desde,@RequestParam(required=false) LocalDate hasta){return service.listar(placa,estado,desde,hasta);}
 @GetMapping("/{id}") @PreAuthorize("hasAuthority('ORDEN_LEER')") public OrdenResponse obtener(@PathVariable Long id){return service.obtener(id);}
 @PatchMapping("/{id}/anular") @PreAuthorize("hasAuthority('ORDEN_ANULAR')") public OrdenResponse anular(@PathVariable Long id,@Valid @RequestBody AnularOrdenRequest x){return service.anular(id,x.motivo());}
}

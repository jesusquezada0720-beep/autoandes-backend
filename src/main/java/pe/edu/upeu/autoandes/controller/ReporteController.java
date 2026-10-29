package pe.edu.upeu.autoandes.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.autoandes.repository.ReporteRepository;
import pe.edu.upeu.autoandes.enums.EstadoOrden;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@RestController @RequestMapping("/api/v1/reportes") @SecurityRequirement(name="bearerAuth") public class ReporteController {
 private final ReporteRepository repo;public ReporteController(ReporteRepository r){repo=r;}
 @GetMapping("/ingresos-por-servicio") @PreAuthorize("hasAuthority('REPORTE_VER')") public List<ReporteRepository.IngresoServicio> ingresos(@RequestParam LocalDate desde,@RequestParam LocalDate hasta){if(desde.isAfter(hasta))throw new pe.edu.upeu.autoandes.exception.ReglaNegocioException(org.springframework.http.HttpStatus.BAD_REQUEST,"La fecha desde no puede ser posterior a hasta");return repo.ingresos(desde.atStartOfDay(),hasta.plusDays(1).atStartOfDay(),EstadoOrden.REGISTRADA);}
}

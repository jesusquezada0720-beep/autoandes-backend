package pe.edu.upeu.autoandes.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
public record OrdenRequest(@NotNull @Positive Long vehiculoId,@NotNull @PositiveOrZero Long kilometraje,@NotNull @Size(min=1,max=10) List<@Valid DetalleOrdenRequest> detalles) { }

package pe.edu.upeu.autoandes.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public record ServicioRequest(@NotBlank @Pattern(regexp="SRV-[0-9]{3}") String codigo,@NotBlank @Size(min=3,max=80) String nombre,@NotNull @DecimalMin(value="0.0",inclusive=false) BigDecimal precio,@NotNull @Min(15) @Max(480) Integer duracionMin) { }

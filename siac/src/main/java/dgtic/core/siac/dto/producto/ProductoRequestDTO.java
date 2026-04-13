package dgtic.core.siac.dto.producto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 150, message = "El nombre no debe exceder 150 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no debe exceder 255 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "La cantidad actual es obligatoria")
    @Min(value = 0, message = "La cantidad actual no puede ser negativa")
    private Integer cantidadActual;

    @NotNull(message = "La categoría es obligatoria")
    @Positive(message = "El id de la categoría debe ser mayor a cero")
    private Long categoriaId;

    @NotNull(message = "El usuario es obligatorio")
    @Positive(message = "El id del usuario debe ser mayor a cero")
    private Long usuarioId;
}
package dgtic.core.siac.system.dto.movimientoInventario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoInventarioRequestDTO {

    @NotNull(message = "El producto es obligatorio")
    @Positive(message = "El id del producto debe ser mayor a cero")
    private Long productoId;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    @Positive(message = "El id del tipo de movimiento debe ser mayor a cero")
    private Long tipoMovimientoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que cero")
    private Integer cantidad;

    @Size(max = 255, message = "El comentario no debe exceder 255 caracteres")
    private String comentario;


}

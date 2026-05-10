package dgtic.core.siac.system.dto.productoCarta;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoCartaRequestDTO {

    @NotNull(message = "El producto es obligatorio")
    @Positive(message = "El id del producto debe ser mayor a cero")
    private Long productoId;

    @NotNull(message = "El tipo de carta es obligatorio")
    @Positive(message = "El id del tipo de carta debe ser mayor a cero")
    private Long tipoCartaId;

    @NotBlank(message = "El atributo es obligatorio")
    @Size(max = 100, message = "El atributo no debe exceder 100 caracteres")
    private String atributo;

    @NotNull(message = "El ataque es obligatorio")
    @PositiveOrZero(message = "El ataque no puede ser negativo")
    @Column(nullable = false)
    private Integer ataque;

    @NotNull(message = "La defensa es obligatoria")
    @PositiveOrZero(message = "La defensa no puede ser negativa")
    @Column(nullable = false)
    private Integer defensa;

    @NotNull(message = "El nivel es obligatorio")
    @Positive(message = "El nivel debe ser mayor que cero")
    @Column(nullable = false)
    private Integer nivel;
}

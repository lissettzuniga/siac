package dgtic.core.siac.system.dto.imagenProducto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagenProductoRequestDTO {

    @NotNull(message = "El producto es obligatorio")
    @Positive(message = "El id del producto debe ser mayor a cero")
    private Long productoId;

    @NotNull(message = "El usuario es obligatorio")
    @Positive(message = "El id del usuario debe ser mayor a cero")
    private Long usuarioId;

    @NotBlank(message = "La ruta es obligatoria")
    @Size(max = 255, message = "La ruta no debe exceder 255 caracteres")
    private String ruta;

    @NotBlank(message = "El nombre del archivo es obligatorio")
    @Size(max = 255, message = "El nombre del archivo no debe exceder 255 caracteres")
    private String nombreArchivo;
}
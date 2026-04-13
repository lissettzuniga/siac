package dgtic.core.siac.dto.imagenProducto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagenProductoResponseDTO {

    private Long id;
    private String ruta;
    private String nombreArchivo;
    private LocalDateTime fechaRegistro;
    private Boolean activo;

    private Long productoId;
    private String productoNombre;
}
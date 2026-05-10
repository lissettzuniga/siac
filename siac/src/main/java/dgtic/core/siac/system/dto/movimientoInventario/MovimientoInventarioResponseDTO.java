package dgtic.core.siac.system.dto.movimientoInventario;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoInventarioResponseDTO {

    private Long id;
    private Long productoId;
    private String productoNombre;
    private Long tipoMovimientoId;
    private String tipoMovimientoNombre;
    private Long usuarioId;
    private String usuarioNombre;
    private Integer cantidad;
    private String comentario;
    private LocalDateTime fecha;
    private Boolean activo;
}

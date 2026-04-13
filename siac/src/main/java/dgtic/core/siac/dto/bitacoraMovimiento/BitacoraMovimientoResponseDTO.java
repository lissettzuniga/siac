package dgtic.core.siac.dto.bitacoraMovimiento;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BitacoraMovimientoResponseDTO {

    private Long id;

    private Long usuarioId;
    private String usuarioNombre;

    private String entidad;
    private Long idEntidad;

    private String accion;
    private String descripcion;

    private LocalDateTime fecha;
}
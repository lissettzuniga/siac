package dgtic.core.siac.system.dto.bitacoraMovimiento;

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

    private String accion;
    private String descripcion;

    private LocalDateTime fecha;
}
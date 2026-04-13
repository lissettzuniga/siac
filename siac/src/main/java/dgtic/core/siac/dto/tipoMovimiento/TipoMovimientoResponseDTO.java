package dgtic.core.siac.dto.tipoMovimiento;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoMovimientoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}

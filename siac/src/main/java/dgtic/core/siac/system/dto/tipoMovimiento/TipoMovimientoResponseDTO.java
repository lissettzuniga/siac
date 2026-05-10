package dgtic.core.siac.system.dto.tipoMovimiento;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoMovimientoResponseDTO {

    private Long id;
    private String nombre;
    private String clave;
    private String descripcion;
    private Boolean activo;
}

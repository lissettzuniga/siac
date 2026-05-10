package dgtic.core.siac.system.dto.rol;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}

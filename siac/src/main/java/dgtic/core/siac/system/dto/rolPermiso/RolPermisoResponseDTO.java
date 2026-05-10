package dgtic.core.siac.system.dto.rolPermiso;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolPermisoResponseDTO {

    private Long id;
    private Long rolId;
    private String nombreRol;
    private Long permisoId;
    private String accionPermiso;
    private Boolean activo;

}

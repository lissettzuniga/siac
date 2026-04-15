package dgtic.core.siac.dto.permiso;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoResponseDTO {

    private Long id;
    private String accion;
    private String recurso;
    private Boolean activo;
}

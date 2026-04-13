package dgtic.core.siac.dto.permiso;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}

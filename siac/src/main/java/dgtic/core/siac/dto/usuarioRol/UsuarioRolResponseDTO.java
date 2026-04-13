package dgtic.core.siac.dto.usuarioRol;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRolResponseDTO {

    private Long id;
    private Long usuarioId;
    private String nombreUsuario;
    private Long rolId;
    private String nombreRol;
    private Boolean activo;

}

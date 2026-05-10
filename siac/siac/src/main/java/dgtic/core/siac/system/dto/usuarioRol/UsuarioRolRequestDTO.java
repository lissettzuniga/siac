package dgtic.core.siac.system.dto.usuarioRol;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRolRequestDTO {

    @NotNull(message = "El usuario es obligatorio")
    @Positive(message = "El id del usuario debe ser mayor a cero")
    private Long usuarioId;

    @NotNull(message = "El rol es obligatorio")
    @Positive(message = "El id del rol debe ser mayor a cero")
    private Long rolId;
}

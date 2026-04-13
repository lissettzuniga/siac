package dgtic.core.siac.dto.rolPermiso;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolPermisoRequestDTO {

    @NotNull(message = "El rol es obligatorio")
    @Positive(message = "El id del rol debe ser mayor a cero")
    private Long rolId;

    @NotNull(message = "El permiso es obligatorio")
    @Positive(message = "El id del permiso debe ser mayor a cero")
    private Long permisoId;
}

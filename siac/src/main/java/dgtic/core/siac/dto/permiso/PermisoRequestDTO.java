package dgtic.core.siac.dto.permiso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermisoRequestDTO {

    @NotBlank(message = "La acción es obligatoria")
    private String accion;

    @NotBlank(message = "El recurso es obligatorio")
    private String recurso;
}

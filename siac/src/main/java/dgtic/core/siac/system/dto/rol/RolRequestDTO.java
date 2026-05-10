package dgtic.core.siac.system.dto.rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolRequestDTO {

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Size(max = 100, message = "El nombre del rol no debe exceder 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no debe exceder 255 caracteres")
    private String descripcion;
}

package dgtic.core.siac.system.dto.usuario;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe exceder 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 100, message = "El apellido paterno no debe exceder 100 caracteres")
    private String apPaterno;

    @Size(max = 100, message = "El apellido materno no debe exceder 100 caracteres")
    private String apMaterno;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un correo válido")
    @Size(max = 150, message = "El correo no debe exceder 150 caracteres")
    private String correo;

    @Column(nullable = false)
    private String contrasena;
}
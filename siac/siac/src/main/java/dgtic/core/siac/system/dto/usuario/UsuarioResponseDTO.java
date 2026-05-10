package dgtic.core.siac.system.dto.usuario;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String correo;
    private LocalDateTime fechaRegistro;
    private LocalDateTime fechaDesactivacion;
    private Boolean activo;
}
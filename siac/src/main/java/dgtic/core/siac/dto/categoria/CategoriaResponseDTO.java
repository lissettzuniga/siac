package dgtic.core.siac.dto.categoria;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}
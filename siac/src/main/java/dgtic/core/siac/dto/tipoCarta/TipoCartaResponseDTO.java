package dgtic.core.siac.dto.tipoCarta;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoCartaResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean activo;
}

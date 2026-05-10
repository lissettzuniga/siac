package dgtic.core.siac.system.dto.productoCarta;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoCartaResponseDTO {

    private Long id;

    private Long productoId;
    private String productoNombre;

    private Long tipoCartaId;
    private String tipoCartaNombre;

    private String atributo;
    private Integer ataque;
    private Integer defensa;
    private Integer nivel;
    private Boolean activo;
}

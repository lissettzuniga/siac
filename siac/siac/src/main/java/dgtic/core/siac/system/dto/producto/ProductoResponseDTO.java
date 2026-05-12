package dgtic.core.siac.system.dto.producto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer cantidadActual;
    private LocalDateTime fechaCreacion;
    private Boolean activo;

    private String usuarioNombre;

    private Long categoriaId;
    private String categoriaNombre;

    private String imagenUrl;
}
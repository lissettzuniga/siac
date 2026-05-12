package dgtic.core.siac.system.dto.busquedaImagen;

import dgtic.core.siac.system.dto.producto.ProductoResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BusquedaImagenResponseDTO {

    private String textoDetectado;
    private Boolean encontrado;
    private List<ProductoResponseDTO> productos;
}
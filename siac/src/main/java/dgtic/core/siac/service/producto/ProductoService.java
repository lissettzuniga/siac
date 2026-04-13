package dgtic.core.siac.service.producto;

import dgtic.core.siac.dto.ProductoFormDTO;
import dgtic.core.siac.dto.producto.ProductoRequestDTO;
import dgtic.core.siac.dto.producto.ProductoResponseDTO;
import dgtic.core.siac.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<ProductoResponseDTO> findAllActivos();
    List<ProductoResponseDTO> findAllInactivos();
    ProductoResponseDTO findById(Long id);
    ProductoResponseDTO create(ProductoRequestDTO request);
    ProductoResponseDTO update(Long id, ProductoRequestDTO request);
    void activar(Long id);
    void desactivar(Long id);
}

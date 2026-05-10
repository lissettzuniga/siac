package dgtic.core.siac.system.service.producto;

import dgtic.core.siac.system.dto.producto.ProductoRequestDTO;
import dgtic.core.siac.system.dto.producto.ProductoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductoService {

    Page<ProductoResponseDTO> findAllActivos(Pageable pageable);

    Page<ProductoResponseDTO> findAllInactivos(Pageable pageable);

    ProductoResponseDTO findById(Long id);

    ProductoResponseDTO create(ProductoRequestDTO request);

    ProductoResponseDTO update(Long id, ProductoRequestDTO request);

    void deactivate(Long id);

    void activate(Long id);
}

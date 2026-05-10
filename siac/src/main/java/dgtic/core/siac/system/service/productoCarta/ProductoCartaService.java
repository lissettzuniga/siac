package dgtic.core.siac.system.service.productoCarta;

import dgtic.core.siac.system.dto.productoCarta.ProductoCartaRequestDTO;
import dgtic.core.siac.system.dto.productoCarta.ProductoCartaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoCartaService {

    Page<ProductoCartaResponseDTO> findAllActivos(Pageable pageable);

    Page<ProductoCartaResponseDTO> findAllInactivos(Pageable pageable);

    Page<ProductoCartaResponseDTO> findByTipoCarta(Long tipoCartaId, Pageable pageable);

    ProductoCartaResponseDTO findById(Long id);

    ProductoCartaResponseDTO create(ProductoCartaRequestDTO request);

    ProductoCartaResponseDTO update(Long id, ProductoCartaRequestDTO request);

    void deactivate(Long id);

    void activate(Long id);
}
package dgtic.core.siac.system.service.imagenProducto;

import dgtic.core.siac.system.dto.imagenProducto.ImagenProductoRequestDTO;
import dgtic.core.siac.system.dto.imagenProducto.ImagenProductoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ImagenProductoService {

    Page<ImagenProductoResponseDTO> findAllActivos(Pageable pageable);
    Page<ImagenProductoResponseDTO> findAllInactivos(Pageable pageable);

    ImagenProductoResponseDTO findById(Long id);
    ImagenProductoResponseDTO create(ImagenProductoRequestDTO request);
    ImagenProductoResponseDTO update(Long id, ImagenProductoRequestDTO request);

    void deactivate(Long id);
    void activate(Long id);
}
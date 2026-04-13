package dgtic.core.siac.service.imagenProducto;

import dgtic.core.siac.dto.imagenProducto.ImagenProductoRequestDTO;
import dgtic.core.siac.dto.imagenProducto.ImagenProductoResponseDTO;

import java.util.List;

public interface ImagenProductoService {

    List<ImagenProductoResponseDTO> findAllActivas();
    List<ImagenProductoResponseDTO> findAllInactivas();
    ImagenProductoResponseDTO findById(Long id);
    ImagenProductoResponseDTO create(ImagenProductoRequestDTO request);
    ImagenProductoResponseDTO update(Long id, ImagenProductoRequestDTO request);
    void activar(Long id);
    void desactivar(Long id);
}
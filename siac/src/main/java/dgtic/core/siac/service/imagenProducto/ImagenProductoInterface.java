package dgtic.core.siac.service.imagenProducto;

import dgtic.core.siac.model.ImagenProducto;

import java.util.List;
import java.util.Optional;

public interface ImagenProductoInterface {
    List<ImagenProducto> findAllActivos();
    List<ImagenProducto> findAllInactivos();
    Optional<ImagenProducto> findById(Long id);
    ImagenProducto save(ImagenProducto imagenProducto);
    void activar(Long id);
    void desactivar(Long id);
}

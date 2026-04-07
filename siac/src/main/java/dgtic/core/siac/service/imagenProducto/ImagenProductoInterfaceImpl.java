package dgtic.core.siac.service.imagenProducto;

import dgtic.core.siac.model.ImagenProducto;
import dgtic.core.siac.repository.ImagenProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenProductoInterfaceImpl implements ImagenProductoInterface{

    private final ImagenProductoRepository imagenProductoRepository;

    public ImagenProductoInterfaceImpl(ImagenProductoRepository imagenProductoRepository) {
        this.imagenProductoRepository = imagenProductoRepository;
    }

    @Override
    public List<ImagenProducto> findAllActivos() {
        return imagenProductoRepository.findByActivoTrue();
    }

    @Override
    public List<ImagenProducto> findAllInactivos() {
        return imagenProductoRepository.findByActivoFalse();
    }

    @Override
    public Optional<ImagenProducto> findById(Long id) {
        return imagenProductoRepository.findById(id);
    }

    @Override
    public ImagenProducto save(ImagenProducto imagenProducto) {
        return imagenProductoRepository.save(imagenProducto);
    }

    @Override
    public void activar(Long id) {
        ImagenProducto imagenProducto = imagenProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen producto no encontrado"));

        imagenProducto.setActivo(true);
        imagenProductoRepository.save(imagenProducto);
    }

    @Override
    public void desactivar(Long id) {
        ImagenProducto imagenProducto = imagenProductoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imagen producto no encontrado"));

        imagenProducto.setActivo(false);
        imagenProductoRepository.save(imagenProducto);
    }
}

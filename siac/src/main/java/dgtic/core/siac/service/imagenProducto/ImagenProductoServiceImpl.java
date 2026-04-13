package dgtic.core.siac.service.imagenProducto;

import dgtic.core.siac.dto.imagenProducto.ImagenProductoRequestDTO;
import dgtic.core.siac.dto.imagenProducto.ImagenProductoResponseDTO;
import dgtic.core.siac.exception.ResourceNotFoundException;
import dgtic.core.siac.mapper.ImagenProductoMapper;
import dgtic.core.siac.model.ImagenProducto;
import dgtic.core.siac.model.Producto;
import dgtic.core.siac.repository.ImagenProductoRepository;
import dgtic.core.siac.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenProductoServiceImpl implements ImagenProductoService {

    private final ImagenProductoRepository imagenProductoRepository;
    private final ProductoRepository productoRepository;

    public ImagenProductoServiceImpl(ImagenProductoRepository imagenProductoRepository,
                                     ProductoRepository productoRepository) {
        this.imagenProductoRepository = imagenProductoRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<ImagenProductoResponseDTO> findAllActivas() {
        return imagenProductoRepository.findByActivoTrue()
                .stream()
                .map(ImagenProductoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<ImagenProductoResponseDTO> findAllInactivas() {
        return imagenProductoRepository.findByActivoFalse()
                .stream()
                .map(ImagenProductoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ImagenProductoResponseDTO findById(Long id) {
        ImagenProducto imagenProducto = imagenProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imagen no encontrada con id: " + id));

        return ImagenProductoMapper.toResponseDTO(imagenProducto);
    }

    @Override
    public ImagenProductoResponseDTO create(ImagenProductoRequestDTO request) {
        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con id: " + request.getProductoId()));

        ImagenProducto imagenProducto = ImagenProducto.builder()
                .producto(producto)
                .ruta(request.getRuta())
                .nombreArchivo(request.getNombreArchivo())
                .activo(true)
                .build();

        ImagenProducto imagenGuardada = imagenProductoRepository.save(imagenProducto);
        return ImagenProductoMapper.toResponseDTO(imagenGuardada);
    }

    @Override
    public ImagenProductoResponseDTO update(Long id, ImagenProductoRequestDTO request) {
        ImagenProducto imagenProducto = imagenProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imagen no encontrada con id: " + id));

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Producto no encontrado con id: " + request.getProductoId()));

        imagenProducto.setProducto(producto);
        imagenProducto.setRuta(request.getRuta());
        imagenProducto.setNombreArchivo(request.getNombreArchivo());

        ImagenProducto imagenActualizada = imagenProductoRepository.save(imagenProducto);
        return ImagenProductoMapper.toResponseDTO(imagenActualizada);
    }

    @Override
    public void activar(Long id) {
        ImagenProducto imagenProducto = imagenProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imagen no encontrada con id: " + id));

        imagenProducto.setActivo(true);
        imagenProductoRepository.save(imagenProducto);
    }

    @Override
    public void desactivar(Long id) {
        ImagenProducto imagenProducto = imagenProductoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imagen no encontrada con id: " + id));

        imagenProducto.setActivo(false);
        imagenProductoRepository.save(imagenProducto);
    }
}
package dgtic.core.siac.system.service.imagenProducto;

import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.imagenProducto.ImagenProductoRequestDTO;
import dgtic.core.siac.system.dto.imagenProducto.ImagenProductoResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.DuplicateResourceException;
import dgtic.core.siac.system.exception.ImagenProductoNotFoundException;
import dgtic.core.siac.system.exception.ProductoNotFoundException;
import dgtic.core.siac.system.mapper.ImagenProductoMapper;
import dgtic.core.siac.system.model.ImagenProducto;
import dgtic.core.siac.system.model.Producto;
import dgtic.core.siac.system.repository.ImagenProductoRepository;
import dgtic.core.siac.system.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImagenProductoServiceImpl implements ImagenProductoService {

    private final ImagenProductoRepository imagenProductoRepository;
    private final ProductoRepository productoRepository;
    private final ImagenProductoMapper imagenProductoMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ImagenProductoResponseDTO> findAllActivos(Pageable pageable) {
        return imagenProductoRepository.findByActivoTrue(pageable)
                .map(imagenProductoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImagenProductoResponseDTO> findAllInactivos(Pageable pageable) {
        return imagenProductoRepository.findByActivoFalse(pageable)
                .map(imagenProductoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ImagenProductoResponseDTO findById(Long id) {
        ImagenProducto imagen = imagenProductoRepository.findById(id)
                .orElseThrow(() -> new ImagenProductoNotFoundException(id));

        return imagenProductoMapper.toResponseDTO(imagen);
    }

    @Auditable(
            entidad = EntidadEnum.IMAGEN_PRODUCTO,
            accion = AccionEnum.CREAR,
            descripcion = "Se creó una imagen de producto"
    )
    @Override
    public ImagenProductoResponseDTO create(ImagenProductoRequestDTO request) {

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ProductoNotFoundException(request.getProductoId()));

        if (imagenProductoRepository.existsByProductoIdAndRutaIgnoreCase(
                request.getProductoId(), request.getRuta())) {
            throw new DuplicateResourceException(
                    "Ya existe una imagen con esa ruta para este producto"
            );
        }

        if (imagenProductoRepository.existsByProductoIdAndNombreArchivoIgnoreCase(
                request.getProductoId(), request.getNombreArchivo())) {
            throw new DuplicateResourceException(
                    "Ya existe una imagen con ese nombre de archivo para este producto"
            );
        }

        ImagenProducto imagen = imagenProductoMapper.toEntity(request);
        imagen.setProducto(producto);
        imagen.setActivo(true);

        ImagenProducto guardada = imagenProductoRepository.save(imagen);

        return imagenProductoMapper.toResponseDTO(guardada);
    }

    @Auditable(
            entidad = EntidadEnum.IMAGEN_PRODUCTO,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "Se actualizó una imagen de producto"
    )
    @Override
    public ImagenProductoResponseDTO update(Long id, ImagenProductoRequestDTO request) {

        ImagenProducto imagen = imagenProductoRepository.findById(id)
                .orElseThrow(() -> new ImagenProductoNotFoundException(id));

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ProductoNotFoundException(request.getProductoId()));

        if (imagenProductoRepository.existsByProductoIdAndRutaIgnoreCaseAndIdNot(
                request.getProductoId(), request.getRuta(), id)) {
            throw new DuplicateResourceException(
                    "Ya existe otra imagen con esa ruta para este producto"
            );
        }

        if (imagenProductoRepository.existsByProductoIdAndNombreArchivoIgnoreCaseAndIdNot(
                request.getProductoId(), request.getNombreArchivo(), id)) {
            throw new DuplicateResourceException(
                    "Ya existe otra imagen con ese nombre de archivo para este producto"
            );
        }

        imagenProductoMapper.updateEntityFromDTO(request, imagen);
        imagen.setProducto(producto);

        ImagenProducto actualizada = imagenProductoRepository.save(imagen);

        return imagenProductoMapper.toResponseDTO(actualizada);
    }

    @Auditable(
            entidad = EntidadEnum.IMAGEN_PRODUCTO,
            accion = AccionEnum.DESACTIVAR,
            descripcion = "Se desactivó una imagen de producto"
    )
    @Override
    public void deactivate(Long id) {

        ImagenProducto imagen = imagenProductoRepository.findById(id)
                .orElseThrow(() -> new ImagenProductoNotFoundException(id));

        if (!imagen.getActivo()) {
            return;
        }

        imagen.setActivo(false);
        imagenProductoRepository.save(imagen);
    }

    @Auditable(
            entidad = EntidadEnum.IMAGEN_PRODUCTO,
            accion = AccionEnum.ACTIVAR,
            descripcion = "Se activó una imagen de producto"
    )
    @Override
    public void activate(Long id) {

        ImagenProducto imagen = imagenProductoRepository.findById(id)
                .orElseThrow(() -> new ImagenProductoNotFoundException(id));

        if (imagen.getActivo()) {
            return;
        }

        imagen.setActivo(true);
        imagenProductoRepository.save(imagen);
    }
}
package dgtic.core.siac.system.service.productoCarta;


import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.productoCarta.ProductoCartaRequestDTO;
import dgtic.core.siac.system.dto.productoCarta.ProductoCartaResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.DuplicateResourceException;
import dgtic.core.siac.system.exception.ProductoCartaNotFoundException;
import dgtic.core.siac.system.exception.ProductoNotFoundException;
import dgtic.core.siac.system.exception.TipoCartaNotFoundException;
import dgtic.core.siac.system.mapper.ProductoCartaMapper;
import dgtic.core.siac.system.model.Producto;
import dgtic.core.siac.system.model.ProductoCarta;
import dgtic.core.siac.system.model.TipoCarta;
import dgtic.core.siac.system.repository.ProductoCartaRepository;
import dgtic.core.siac.system.repository.ProductoRepository;
import dgtic.core.siac.system.repository.TipoCartaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductoCartaServiceImpl implements ProductoCartaService {

    private final ProductoCartaRepository productoCartaRepository;
    private final ProductoRepository productoRepository;
    private final TipoCartaRepository tipoCartaRepository;
    private final ProductoCartaMapper productoCartaMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoCartaResponseDTO> findAllActivos(Pageable pageable) {
        return productoCartaRepository.findByActivoTrue(pageable)
                .map(productoCartaMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoCartaResponseDTO> findAllInactivos(Pageable pageable) {
        return productoCartaRepository.findByActivoFalse(pageable)
                .map(productoCartaMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoCartaResponseDTO> findByTipoCarta(Long tipoCartaId, Pageable pageable) {
        return productoCartaRepository.findByTipoCartaIdAndActivoTrue(tipoCartaId, pageable)
                .map(productoCartaMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoCartaResponseDTO findById(Long id) {
        ProductoCarta productoCarta = productoCartaRepository.findById(id)
                .orElseThrow(() -> new ProductoCartaNotFoundException(id));

        return productoCartaMapper.toResponseDTO(productoCarta);
    }


    @Auditable(
            entidad = EntidadEnum.PRODUCTO_CARTA,
            accion = AccionEnum.CREAR,
            descripcion = "Se creó un producto de tipo carta"
    )
    @Override
    public ProductoCartaResponseDTO create(ProductoCartaRequestDTO request) {

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ProductoNotFoundException(request.getProductoId()));

        TipoCarta tipoCarta = tipoCartaRepository.findById(request.getTipoCartaId())
                .orElseThrow(() -> new TipoCartaNotFoundException(request.getTipoCartaId()));

        if (productoCartaRepository.existsByProductoId(request.getProductoId())) {
            throw new DuplicateResourceException(
                    "El producto con id " + request.getProductoId()
                            + " ya tiene un detalle de carta registrado"
            );
        }

        ProductoCarta productoCarta = productoCartaMapper.toEntity(request);
        productoCarta.setProducto(producto);
        productoCarta.setTipoCarta(tipoCarta);
        productoCarta.setActivo(true);

        ProductoCarta guardado = productoCartaRepository.save(productoCarta);

        return productoCartaMapper.toResponseDTO(guardado);
    }


    @Auditable(
            entidad = EntidadEnum.PRODUCTO_CARTA,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "Se actualizó un producto de tipo carta"
    )
    @Override
    public ProductoCartaResponseDTO update(Long id, ProductoCartaRequestDTO request) {

        ProductoCarta productoCarta = productoCartaRepository.findById(id)
                .orElseThrow(() -> new ProductoCartaNotFoundException(id));

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ProductoNotFoundException(request.getProductoId()));

        TipoCarta tipoCarta = tipoCartaRepository.findById(request.getTipoCartaId())
                .orElseThrow(() -> new TipoCartaNotFoundException(request.getTipoCartaId()));

        if (productoCartaRepository.existsByProductoIdAndIdNot(request.getProductoId(), id)) {
            throw new DuplicateResourceException(
                    "El producto con id " + request.getProductoId()
                            + " ya tiene otro detalle de carta registrado"
            );
        }

        productoCartaMapper.updateEntityFromDTO(request, productoCarta);
        productoCarta.setProducto(producto);
        productoCarta.setTipoCarta(tipoCarta);

        ProductoCarta actualizado = productoCartaRepository.save(productoCarta);

        return productoCartaMapper.toResponseDTO(actualizado);
    }

    @Auditable(
            entidad = EntidadEnum.PRODUCTO_CARTA,
            accion = AccionEnum.DESACTIVAR,
            descripcion = "Se desactivó un producto de tipo carta"
    )
    @Override
    public void deactivate(Long id) {

        ProductoCarta productoCarta = productoCartaRepository.findById(id)
                .orElseThrow(() -> new ProductoCartaNotFoundException(id));

        if (!productoCarta.getActivo()) {
            return;
        }

        productoCarta.setActivo(false);
        productoCartaRepository.save(productoCarta);
    }


    @Auditable(
            entidad = EntidadEnum.PRODUCTO_CARTA,
            accion = AccionEnum.ACTIVAR,
            descripcion = "Se activó un producto de tipo carta"
    )
    @Override
    public void activate(Long id) {

        ProductoCarta productoCarta = productoCartaRepository.findById(id)
                .orElseThrow(() -> new ProductoCartaNotFoundException(id));

        if (productoCarta.getActivo()) {
            return;
        }

        productoCarta.setActivo(true);
        productoCartaRepository.save(productoCarta);
    }
}
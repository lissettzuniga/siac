package dgtic.core.siac.system.service.producto;

import dgtic.core.siac.security.SecurityUtils;
import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.producto.ProductoRequestDTO;
import dgtic.core.siac.system.dto.producto.ProductoResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.*;
import dgtic.core.siac.system.mapper.ProductoMapper;
import dgtic.core.siac.system.model.Categoria;
import dgtic.core.siac.system.model.Producto;
import dgtic.core.siac.system.model.Usuario;
import dgtic.core.siac.system.repository.CategoriaRepository;
import dgtic.core.siac.system.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoResponseDTO> findAllActivos(Pageable pageable) {
        return productoRepository.findByActivoTrue(pageable)
                .map(productoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoResponseDTO> findAllInactivos(Pageable pageable) {
        return productoRepository.findByActivoFalse(pageable)
                .map(productoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponseDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        return productoMapper.toResponseDTO(producto);
    }


    @Auditable(
            entidad = EntidadEnum.PRODUCTO,
            accion = AccionEnum.CREAR,
            descripcion = "Se creó un producto"
    )
    @Override
    public ProductoResponseDTO create(ProductoRequestDTO request) {

        if (productoRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new DuplicateResourceException(
                    "Ya existe un producto con el nombre: " + request.getNombre()
            );
        }

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException(request.getCategoriaId()));


        Usuario usuarioAutenticado = securityUtils.getUsuarioAutenticado();

        Producto producto = productoMapper.toEntity(request);
        producto.setCategoria(categoria);
        producto.setUsuario(usuarioAutenticado);
        producto.setActivo(true);

        Producto guardado = productoRepository.save(producto);

        return productoMapper.toResponseDTO(guardado);
    }


    @Auditable(
            entidad = EntidadEnum.PRODUCTO,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "Se actualizó un producto"
    )
    @Override
    public ProductoResponseDTO update(Long id, ProductoRequestDTO request) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        if (productoRepository.existsByNombreIgnoreCaseAndIdNot(request.getNombre(), id)) {
            throw new DuplicateResourceException(
                    "Ya existe otro producto con el nombre: " + request.getNombre()
            );
        }

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException(request.getCategoriaId()));

        productoMapper.updateEntityFromDTO(request, producto);
        producto.setCategoria(categoria);

        Producto actualizado = productoRepository.save(producto);

        return productoMapper.toResponseDTO(actualizado);
    }

    @Auditable(
            entidad = EntidadEnum.PRODUCTO,
            accion = AccionEnum.DESACTIVAR,
            descripcion = "Se desactivó un producto"
    )
    @Override
    public void deactivate(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        if (!producto.getActivo()) {
            return;
        }

        producto.setActivo(false);
        productoRepository.save(producto);
    }


    @Auditable(
            entidad = EntidadEnum.PRODUCTO,
            accion = AccionEnum.ACTIVAR,
            descripcion = "Se activó un producto"
    )
    @Override
    public void activate(Long id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        if (producto.getActivo()) {
            return;
        }

        producto.setActivo(true);
        productoRepository.save(producto);
    }

}
package dgtic.core.siac.service.producto;

import dgtic.core.siac.dto.ProductoFormDTO;
import dgtic.core.siac.model.Categoria;
import dgtic.core.siac.model.Producto;
import dgtic.core.siac.model.TipoProducto;
import dgtic.core.siac.model.Usuario;
import dgtic.core.siac.repository.CategoriaRepository;
import dgtic.core.siac.repository.ProductoRepository;
import dgtic.core.siac.repository.TipoProductoRepository;
import dgtic.core.siac.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService{

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final TipoProductoRepository tipoProductoRepository;
    private final UsuarioRepository usuarioRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, TipoProductoRepository tipoProductoRepository, UsuarioRepository usuarioRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.tipoProductoRepository = tipoProductoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAllActivos() {
        return productoRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAllInactivos() {
        return productoRepository.findByActivoFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));
    }

    @Override
    public ProductoFormDTO findFormularioById(Long id) {
        Producto producto = findById(id);

        ProductoFormDTO dto = new ProductoFormDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setCantidadActual(producto.getCantidadActual());
        dto.setCategoriaId(producto.getCategoria().getId());
        dto.setTipoProductoId(producto.getTipoProducto().getId());
        dto.setActivo(producto.getActivo());

        return dto;
    }

    @Override
    public void save(ProductoFormDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        TipoProducto tipoProducto = tipoProductoRepository.findById(dto.getTipoProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de producto no encontrado"));

        Usuario usuario = usuarioRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Producto producto = new Producto();
        producto.setNombre(dto.getNombre().trim());
        producto.setDescripcion(dto.getDescripcion() != null ? dto.getDescripcion().trim() : null);
        producto.setPrecio(dto.getPrecio());
        producto.setCantidadActual(dto.getCantidadActual());
        producto.setCategoria(categoria);
        producto.setTipoProducto(tipoProducto);
        producto.setUsuario(usuario);
        producto.setActivo(true);

        productoRepository.save(producto);
    }

    @Override
    public void update(Long id, ProductoFormDTO dto) {
        Producto producto = findById(id);

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        TipoProducto tipoProducto = tipoProductoRepository.findById(dto.getTipoProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo de producto no encontrado"));

        producto.setNombre(dto.getNombre().trim());
        producto.setDescripcion(dto.getDescripcion() != null ? dto.getDescripcion().trim() : null);
        producto.setPrecio(dto.getPrecio());
        producto.setCantidadActual(dto.getCantidadActual());
        producto.setCategoria(categoria);
        producto.setTipoProducto(tipoProducto);

        productoRepository.save(producto);
    }


    @Override
    public void activar(Long id) {
        Producto producto = findById(id);
        producto.setActivo(true);
        productoRepository.save(producto);
    }

    @Override
    public void desactivar(Long id) {
        Producto producto = findById(id);
        producto.setActivo(false);
        productoRepository.save(producto);
    }
}

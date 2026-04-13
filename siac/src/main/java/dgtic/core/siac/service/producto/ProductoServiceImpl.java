package dgtic.core.siac.service.producto;

import dgtic.core.siac.dto.producto.ProductoRequestDTO;
import dgtic.core.siac.dto.producto.ProductoResponseDTO;
import dgtic.core.siac.exception.ResourceNotFoundException;
import dgtic.core.siac.mapper.ProductoMapper;
import dgtic.core.siac.model.Categoria;
import dgtic.core.siac.model.Producto;
import dgtic.core.siac.model.Usuario;
import dgtic.core.siac.repository.CategoriaRepository;
import dgtic.core.siac.repository.ProductoRepository;
import dgtic.core.siac.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository,
                               CategoriaRepository categoriaRepository,
                               UsuarioRepository usuarioRepository,
                               TipoProductoRepository tipoProductoRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<ProductoResponseDTO> findAllActivos() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(ProductoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<ProductoResponseDTO> findAllInactivos() {
        return productoRepository.findByActivoFalse()
                .stream()
                .map(ProductoMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ProductoResponseDTO findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        return ProductoMapper.toResponseDTO(producto);
    }

    @Override
    public ProductoResponseDTO create(ProductoRequestDTO request) {
        if (productoRepository.existsByNombre(request.getNombre())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con id: " + request.getUsuarioId()));


        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría no encontrada con id: " + request.getCategoriaId()));

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setCantidadActual(request.getCantidadActual());
        producto.setUsuario(usuario);
        producto.setCategoria(categoria);
        producto.setActivo(true);

        Producto productoGuardado = productoRepository.save(producto);
        return ProductoMapper.toResponseDTO(productoGuardado);
    }

    @Override
    public ProductoResponseDTO update(Long id, ProductoRequestDTO request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        if (productoRepository.existsByNombreAndIdNot(request.getNombre(), id)) {
            throw new IllegalArgumentException("Ya existe otro producto con ese nombre");
        }

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con id: " + request.getUsuarioId()));


        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría no encontrada con id: " + request.getCategoriaId()));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setCantidadActual(request.getCantidadActual());
        producto.setUsuario(usuario);
        producto.setCategoria(categoria);

        Producto productoActualizado = productoRepository.save(producto);
        return ProductoMapper.toResponseDTO(productoActualizado);
    }

    @Override
    public void activar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        producto.setActivo(true);
        productoRepository.save(producto);
    }

    @Override
    public void desactivar(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        producto.setActivo(false);
        productoRepository.save(producto);
    }
}
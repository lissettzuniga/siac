package dgtic.core.siac.service.producto;

public interface ProductoService {

    List<Producto> findAllActivos();

    List<Producto> findAllInactivos();

    Optional<Producto> findById(Long id);

    Producto save(Producto producto);

    void activar(Long id);

    void desactivar(Long id);
}

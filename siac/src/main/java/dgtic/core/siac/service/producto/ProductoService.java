package dgtic.core.siac.service.producto;

import dgtic.core.siac.dto.ProductoFormDTO;
import dgtic.core.siac.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {

    List<Producto> findAllActivos();
    List<Producto> findAllInactivos();
    Producto findById(Long id);
    ProductoFormDTO findFormularioById(Long id);
    void save(ProductoFormDTO dto);
    void update(Long id, ProductoFormDTO dto);
    void activar(Long id);
    void desactivar(Long id);
}

package dgtic.core.siac.service.productoCarta;

import dgtic.core.siac.model.ProductoCarta;

import java.util.List;
import java.util.Optional;

public interface ProductoCartaService {

    List<ProductoCarta> findAllActivos();
    List<ProductoCarta> findAllInactivos();
    Optional<ProductoCarta> findById(Long id);
    ProductoCarta save(ProductoCarta productoCarta);
    void activar(Long id);
    void desactivar(Long id);
}

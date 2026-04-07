package dgtic.core.siac.service.tipoProducto;

import dgtic.core.siac.model.TipoProducto;

import java.util.List;
import java.util.Optional;

public interface TipoProductoInterface {

    List<TipoProducto> findAllActivos();
    List<TipoProducto> findAllInactivos();
    Optional<TipoProducto> findById(Long id);
    TipoProducto save(TipoProducto tipoProducto);
    void activar(Long id);
    void desactivar(Long id);
}

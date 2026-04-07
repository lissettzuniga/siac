package dgtic.core.siac.service.movimientoInventario;

import dgtic.core.siac.model.MovimientoInventario;

import java.util.List;
import java.util.Optional;

public interface MovimientoInventarioInterface {
    List<MovimientoInventario> findAllActivos();
    List<MovimientoInventario> findAllInactivos();
    Optional<MovimientoInventario> findById(Long id);
    MovimientoInventario save(MovimientoInventario movimientoInventario);
    void activar(Long id);
    void desactivar(Long id);
}

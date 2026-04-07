package dgtic.core.siac.service.tipoMovimiento;

import dgtic.core.siac.model.TipoMovimiento;
import dgtic.core.siac.model.TipoProducto;

import java.util.List;
import java.util.Optional;

public interface TipoMovimientoInterface {
    List<TipoMovimiento> findAllActivos();
    List<TipoMovimiento> findAllInactivos();
    Optional<TipoMovimiento> findById(Long id);
    TipoMovimiento save(TipoMovimiento tipoMovimiento);
    void activar(Long id);
    void desactivar(Long id);
}

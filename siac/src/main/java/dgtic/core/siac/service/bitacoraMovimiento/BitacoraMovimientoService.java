package dgtic.core.siac.service.bitacoraMovimiento;

import dgtic.core.siac.model.BitacoraMovimiento;
import dgtic.core.siac.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface BitacoraMovimientoInterface {
    List<BitacoraMovimiento> findAll();
    Optional<BitacoraMovimiento> findById(Long id);
    List<BitacoraMovimiento> findByUsuario(Long usuarioId);
    List<BitacoraMovimiento> findByEntidad(String entidad);
    BitacoraMovimiento save(BitacoraMovimiento movimiento);
}

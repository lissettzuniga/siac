package dgtic.core.siac.service.bitacoraMovimiento;

import dgtic.core.siac.model.BitacoraMovimiento;

import java.util.List;
import java.util.Optional;

public interface BitacoraMovimientoService {
    List<BitacoraMovimiento> findAll();
    Optional<BitacoraMovimiento> findById(Long id);
    List<BitacoraMovimiento> findByUsuario(Long usuarioId);
    List<BitacoraMovimiento> findByEntidad(String entidad);
    BitacoraMovimiento save(BitacoraMovimiento movimiento);
}

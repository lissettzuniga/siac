package dgtic.core.siac.repository;

import dgtic.core.siac.model.BitacoraMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BitacoraMovimientoRepository
        extends JpaRepository<BitacoraMovimiento,Long> {

    List<BitacoraMovimiento> findByUsuarioId(Long usuarioId);
    List<BitacoraMovimiento> findByEntidad(String entidad);
}

package dgtic.core.siac.repository;

import dgtic.core.siac.model.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento,Long> {

    List<TipoMovimiento> findByActivoTrue();
}

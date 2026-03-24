package dgtic.core.siac.repository;

import dgtic.core.siac.model.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario,Long> {

    List<MovimientoInventario> findByActivoTrue();
}

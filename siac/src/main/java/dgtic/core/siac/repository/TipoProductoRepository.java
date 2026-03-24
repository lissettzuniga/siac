package dgtic.core.siac.repository;

import dgtic.core.siac.model.TipoProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoProductoRepository extends JpaRepository<TipoProducto, Long> {

    List<TipoProducto> findByActivoTrue();
}

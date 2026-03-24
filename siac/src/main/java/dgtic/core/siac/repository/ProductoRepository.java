package dgtic.core.siac.repository;

import dgtic.core.siac.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {

    List<Producto> findByActivoTrue();
}

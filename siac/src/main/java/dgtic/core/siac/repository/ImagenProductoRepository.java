package dgtic.core.siac.repository;

import dgtic.core.siac.model.ImagenProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto,Long> {

    List<ImagenProducto> findByActivoTrue();
}

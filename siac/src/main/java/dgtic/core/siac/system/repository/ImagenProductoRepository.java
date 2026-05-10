package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.ImagenProducto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenProductoRepository extends JpaRepository<ImagenProducto,Long> {

    Page<ImagenProducto> findByActivoTrue(Pageable pageable);

    Page<ImagenProducto> findByActivoFalse(Pageable pageable);

    boolean existsByProductoIdAndRutaIgnoreCase(Long productoId, String ruta);
    boolean existsByProductoIdAndRutaIgnoreCaseAndIdNot(Long productoId, String ruta, Long id);

    boolean existsByProductoIdAndNombreArchivoIgnoreCase(Long productoId, String nombreArchivo);
    boolean existsByProductoIdAndNombreArchivoIgnoreCaseAndIdNot(Long productoId, String nombreArchivo, Long id);
}

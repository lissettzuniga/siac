package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {

    Page<Producto> findByActivoTrue(Pageable pageable);

    Page<Producto> findByActivoFalse(Pageable pageable);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);


    List<Producto> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
}

package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.TipoMovimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TipoMovimientoRepository extends JpaRepository<TipoMovimiento,Long> {

    Page<TipoMovimiento> findByActivoTrue(Pageable pageable);

    Page<TipoMovimiento> findByActivoFalse(Pageable pageable);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);

    boolean existsByClaveIgnoreCase(String clave);

    boolean existsByClaveIgnoreCaseAndIdNot(String clave, Long id);


}

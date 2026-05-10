package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolRepository extends JpaRepository<Rol,Long> {

    Page<Rol> findByActivoTrue(Pageable pageable);

    Page<Rol> findByActivoFalse(Pageable pageable);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}

package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.RolPermiso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolPermisoRepository extends JpaRepository<RolPermiso,Long> {

    Page<RolPermiso> findByActivoTrue(Pageable pageable);

    Page<RolPermiso> findByActivoFalse(Pageable pageable);

    Page<RolPermiso> findByRolIdAndActivoTrue(Long rolId, Pageable pageable);

    Page<RolPermiso> findByPermisoIdAndActivoTrue(Long permisoId, Pageable pageable);

    boolean existsByRolIdAndPermisoId(Long rolId, Long permisoId);

    boolean existsByRolIdAndPermisoIdAndIdNot(Long rolId, Long permisoId, Long id);
}

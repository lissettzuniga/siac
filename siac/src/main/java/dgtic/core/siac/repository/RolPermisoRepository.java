package dgtic.core.siac.repository;

import dgtic.core.siac.model.RolPermiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolPermisoRepository extends JpaRepository<RolPermiso,Long> {

    List<RolPermiso> findByActivoTrue();
}

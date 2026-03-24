package dgtic.core.siac.repository;

import dgtic.core.siac.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermisoRepository extends JpaRepository<Permiso,Long> {

    List<Permiso> findByActivoTrue();
}

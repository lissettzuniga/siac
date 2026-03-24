package dgtic.core.siac.repository;

import dgtic.core.siac.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol,Long> {
    List<Rol> findByActivoTrue();
}

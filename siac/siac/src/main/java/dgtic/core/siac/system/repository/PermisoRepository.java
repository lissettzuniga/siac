package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.Permiso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoRepository extends JpaRepository<Permiso,Long> {

    Page<Permiso> findByActivoTrue(Pageable pageable);
    Page<Permiso> findByActivoFalse(Pageable pageable);

    boolean existsByAccionIgnoreCaseAndRecursoIgnoreCase(String accion, String recurso);


    boolean existsByAccionIgnoreCaseAndRecursoIgnoreCaseAndIdNot(String accion, String recurso, Long id);

}

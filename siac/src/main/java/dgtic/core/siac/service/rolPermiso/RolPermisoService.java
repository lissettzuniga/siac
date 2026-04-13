package dgtic.core.siac.service.rolPermiso;

import dgtic.core.siac.model.RolPermiso;

import java.util.List;
import java.util.Optional;

public interface RolPermisoService {
    List<RolPermiso> findAllActivos();
    List<RolPermiso> findAllInactivos();
    Optional<RolPermiso> findById(Long id);
    RolPermiso save(RolPermiso rolPermiso);
    void activar(Long id);
    void desactivar(Long id);
}

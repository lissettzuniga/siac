package dgtic.core.siac.service.permiso;

import dgtic.core.siac.model.Permiso;

import java.util.List;
import java.util.Optional;

public interface PermisoInterface {

    List<Permiso> findAllActivos();
    List<Permiso> findAllInactivos();
    Optional<Permiso> findById(Long id);
    Permiso save(Permiso permiso);
    void activar(Long id);
    void desactivar(Long id);
}

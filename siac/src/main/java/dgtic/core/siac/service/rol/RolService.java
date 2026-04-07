package dgtic.core.siac.service.rol;

import dgtic.core.siac.model.Rol;

import java.util.List;
import java.util.Optional;

public interface RolService {
    List<Rol> findAllActivos();
    List<Rol> findAllInactivos();
    Optional<Rol> findById(Long id);
    Rol save(Rol rol);
    void activar(Long id);
    void desactivar(Long id);
}

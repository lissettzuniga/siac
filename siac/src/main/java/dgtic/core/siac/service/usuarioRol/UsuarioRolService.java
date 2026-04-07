package dgtic.core.siac.service.usuarioRol;



import dgtic.core.siac.model.UsuarioRol;

import java.util.List;
import java.util.Optional;

public interface UsuarioRolService {
    List<UsuarioRol> findAllActivos();
    List<UsuarioRol> findAllInactivos();
    Optional<UsuarioRol> findById(Long id);
    UsuarioRol save(UsuarioRol usuarioRol);
    void activar(Long id);
    void desactivar(Long id);
}

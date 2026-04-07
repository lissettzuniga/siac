package dgtic.core.siac.service.usuario;

import dgtic.core.siac.dto.UsuarioFormDTO;
import dgtic.core.siac.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAllActivos();
    List<Usuario> findAllInactivos();
    UsuarioFormDTO findById(Long id);
    Usuario save(UsuarioFormDTO usuarioFormDTO);
    Usuario update(Long id, UsuarioFormDTO usuarioFormDTO);
    void activar(Long id);
    void desactivar(Long id);
}

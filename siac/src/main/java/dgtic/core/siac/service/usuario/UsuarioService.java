package dgtic.core.siac.service.usuario;

import dgtic.core.siac.dto.UsuarioFormDTO;
import dgtic.core.siac.dto.usuario.UsuarioRequestDTO;
import dgtic.core.siac.dto.usuario.UsuarioResponseDTO;
import dgtic.core.siac.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<UsuarioResponseDTO> findAllActivos();
    List<UsuarioResponseDTO> findAllInactivos();
    UsuarioResponseDTO findById(Long id);
    UsuarioResponseDTO create(UsuarioRequestDTO request);
    UsuarioResponseDTO update(Long id, UsuarioRequestDTO request);
    void activar(Long id);
    void desactivar(Long id);
}

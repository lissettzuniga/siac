package dgtic.core.siac.system.service.usuario;

import dgtic.core.siac.system.dto.usuario.ChangePasswordRequestDTO;
import dgtic.core.siac.system.dto.usuario.UsuarioRequestDTO;
import dgtic.core.siac.system.dto.usuario.UsuarioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioService {

    Page<UsuarioResponseDTO> findAllActivos(Pageable pageable);

    Page<UsuarioResponseDTO> findAllInactivos(Pageable pageable);

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO findByCorreo(String correo);

    UsuarioResponseDTO create(UsuarioRequestDTO request);

    UsuarioResponseDTO update(Long id, UsuarioRequestDTO request);

    void deactivate(Long id);

    void activate(Long id);

    void changePassword(ChangePasswordRequestDTO request);

    UsuarioResponseDTO getUsuarioAutenticado();
}
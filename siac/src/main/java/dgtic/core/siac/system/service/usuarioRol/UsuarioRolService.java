package dgtic.core.siac.system.service.usuarioRol;

import dgtic.core.siac.system.dto.usuarioRol.UsuarioRolRequestDTO;
import dgtic.core.siac.system.dto.usuarioRol.UsuarioRolResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioRolService {

    Page<UsuarioRolResponseDTO> findAllActivos(Pageable pageable);

    Page<UsuarioRolResponseDTO> findAllInactivos(Pageable pageable);

    Page<UsuarioRolResponseDTO> findByUsuario(Long usuarioId, Pageable pageable);

    Page<UsuarioRolResponseDTO> findByRol(Long rolId, Pageable pageable);

    UsuarioRolResponseDTO findById(Long id);

    UsuarioRolResponseDTO create(UsuarioRolRequestDTO request);

    UsuarioRolResponseDTO update(Long id, UsuarioRolRequestDTO request);

    void deactivate(Long id);

    void activate(Long id);
}
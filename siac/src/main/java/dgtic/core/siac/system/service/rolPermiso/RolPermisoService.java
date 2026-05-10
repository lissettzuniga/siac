package dgtic.core.siac.system.service.rolPermiso;

import dgtic.core.siac.system.dto.rolPermiso.RolPermisoRequestDTO;
import dgtic.core.siac.system.dto.rolPermiso.RolPermisoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RolPermisoService {
    Page<RolPermisoResponseDTO> findAllActivos(Pageable pageable);

    Page<RolPermisoResponseDTO> findAllInactivos(Pageable pageable);

    Page<RolPermisoResponseDTO> findByRol(Long rolId, Pageable pageable);

    Page<RolPermisoResponseDTO> findByPermiso(Long permisoId, Pageable pageable);

    RolPermisoResponseDTO findById(Long id);

    RolPermisoResponseDTO create(RolPermisoRequestDTO request);

    RolPermisoResponseDTO update(Long id, RolPermisoRequestDTO request);

    void deactivate(Long id);

    void activate(Long id);
}

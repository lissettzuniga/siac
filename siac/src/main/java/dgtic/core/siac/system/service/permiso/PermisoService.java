package dgtic.core.siac.system.service.permiso;

import dgtic.core.siac.system.dto.permiso.PermisoRequestDTO;
import dgtic.core.siac.system.dto.permiso.PermisoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PermisoService {

    Page<PermisoResponseDTO> findAllActivos(Pageable pageable);

    Page<PermisoResponseDTO> findAllInactivos(Pageable pageable);

    PermisoResponseDTO findById(Long id);

    PermisoResponseDTO create(PermisoRequestDTO request);

    PermisoResponseDTO update(Long id, PermisoRequestDTO request);

    void deactivate(Long id);

    void activate(Long id);
}

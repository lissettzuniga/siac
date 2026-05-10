package dgtic.core.siac.system.service.rol;

import dgtic.core.siac.system.dto.rol.RolRequestDTO;
import dgtic.core.siac.system.dto.rol.RolResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface RolService {
    Page<RolResponseDTO> findAllActivos(Pageable pageable);

    Page<RolResponseDTO> findAllInactivos(Pageable pageable);

    RolResponseDTO findById(Long id);

    RolResponseDTO create(RolRequestDTO request);

    RolResponseDTO update(Long id, RolRequestDTO request);

    void deactivate(Long id);

    void activate(Long id);
}

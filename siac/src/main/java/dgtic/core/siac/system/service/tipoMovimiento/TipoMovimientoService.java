package dgtic.core.siac.system.service.tipoMovimiento;

import dgtic.core.siac.system.dto.tipoMovimiento.TipoMovimientoRequestDTO;
import dgtic.core.siac.system.dto.tipoMovimiento.TipoMovimientoResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TipoMovimientoService {

    Page<TipoMovimientoResponseDTO> findAllActivos(Pageable pageable);

    Page<TipoMovimientoResponseDTO> findAllInactivos(Pageable pageable);

    TipoMovimientoResponseDTO findById(Long id);

    TipoMovimientoResponseDTO create(TipoMovimientoRequestDTO request);

    TipoMovimientoResponseDTO update(Long id, TipoMovimientoRequestDTO request);

    void deactivate(Long id);

    void activate(Long id);
}

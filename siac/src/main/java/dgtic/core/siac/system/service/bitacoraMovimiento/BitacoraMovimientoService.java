package dgtic.core.siac.system.service.bitacoraMovimiento;

import dgtic.core.siac.system.dto.bitacoraMovimiento.BitacoraMovimientoResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BitacoraMovimientoService {

    Page<BitacoraMovimientoResponseDTO> findAll(Pageable pageable);

    Page<BitacoraMovimientoResponseDTO> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<BitacoraMovimientoResponseDTO> findByEntidad(EntidadEnum entidad, Pageable pageable);

    BitacoraMovimientoResponseDTO findById(Long id);

    void logAction(EntidadEnum entidad,
            AccionEnum accion, String descripcion);

    Page<BitacoraMovimientoResponseDTO> findByUsuarioIdAndEntidad(
            Long usuarioId, EntidadEnum entidad, Pageable pageable);
}
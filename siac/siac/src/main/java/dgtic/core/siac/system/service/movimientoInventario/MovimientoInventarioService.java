package dgtic.core.siac.system.service.movimientoInventario;

import dgtic.core.siac.system.dto.movimientoInventario.MovimientoInventarioRequestDTO;
import dgtic.core.siac.system.dto.movimientoInventario.MovimientoInventarioResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovimientoInventarioService {

    Page<MovimientoInventarioResponseDTO> findAll(Pageable pageable);
    Page<MovimientoInventarioResponseDTO> findByProductoId(Long productoId, Pageable pageable);
    Page<MovimientoInventarioResponseDTO> findByUsuarioId(Long usuarioId, Pageable pageable);
    Page<MovimientoInventarioResponseDTO> findByTipoMovimientoClave(String clave, Pageable pageable);
    Page<MovimientoInventarioResponseDTO> findByProductoIdAndTipoMovimientoClave(
            Long productoId,
            String clave,
            Pageable pageable
    );

    MovimientoInventarioResponseDTO findById(Long id);

    MovimientoInventarioResponseDTO create(MovimientoInventarioRequestDTO request);

    byte[] exportReporteInventarioToExcel(Long productoId, String tipo);
}
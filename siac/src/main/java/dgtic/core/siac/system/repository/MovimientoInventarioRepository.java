package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.MovimientoInventario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoInventarioRepository
        extends JpaRepository<MovimientoInventario, Long> {

    Page<MovimientoInventario> findByProductoId(Long productoId, Pageable pageable);

    Page<MovimientoInventario> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<MovimientoInventario> findByTipoMovimiento_Clave(String clave, Pageable pageable);

    Page<MovimientoInventario> findByProductoIdAndTipoMovimiento_Clave(
            Long productoId, String clave, Pageable pageable);
}
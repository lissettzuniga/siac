package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.model.BitacoraMovimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BitacoraMovimientoRepository
        extends JpaRepository<BitacoraMovimiento,Long> {

    Page<BitacoraMovimiento> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<BitacoraMovimiento> findByEntidad(EntidadEnum entidad, Pageable pageable);

    Page<BitacoraMovimiento> findByUsuarioIdAndEntidad(
            Long usuarioId,
            EntidadEnum entidad,
            Pageable pageable
    );
}

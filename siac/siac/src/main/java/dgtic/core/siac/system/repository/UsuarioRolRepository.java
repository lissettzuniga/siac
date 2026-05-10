package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.UsuarioRol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {

    Page<UsuarioRol> findByActivoTrue(Pageable pageable);

    Page<UsuarioRol> findByActivoFalse(Pageable pageable);

    Page<UsuarioRol> findByUsuarioIdAndActivoTrue(Long usuarioId, Pageable pageable);

    Page<UsuarioRol> findByRolIdAndActivoTrue(Long rolId, Pageable pageable);

    boolean existsByUsuarioIdAndRolId(Long usuarioId, Long rolId);

    boolean existsByUsuarioIdAndRolIdAndIdNot(Long usuarioId, Long rolId, Long id);

    @Query("""
            SELECT ur
            FROM UsuarioRol ur
            JOIN FETCH ur.rol r
            WHERE ur.usuario.id = :usuarioId
            AND ur.activo = true
            AND ur.fechaFin IS NULL
            AND r.activo = true
            """)
    List<UsuarioRol> findRolesActivosByUsuarioId(Long usuarioId);
}
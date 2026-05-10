

package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Page<Usuario> findByActivoTrue(Pageable pageable);

    Page<Usuario> findByActivoFalse(Pageable pageable);

    Optional<Usuario> findByCorreoIgnoreCase(String correo);

    Optional<Usuario> findByCorreoIgnoreCaseAndActivoTrue(String correo);

    boolean existsByCorreoIgnoreCase(String correo);

    boolean existsByCorreoIgnoreCaseAndIdNot(String correo, Long id);
}

package dgtic.core.siac.repository;

import dgtic.core.siac.model.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol,Long> {

    List<UsuarioRol> findByActivoTrue();
}

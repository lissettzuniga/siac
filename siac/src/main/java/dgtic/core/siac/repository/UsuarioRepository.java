package dgtic.core.siac.repository;

import dgtic.core.siac.model.Producto;
import dgtic.core.siac.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    List<Usuario> findByActivoTrue();

}

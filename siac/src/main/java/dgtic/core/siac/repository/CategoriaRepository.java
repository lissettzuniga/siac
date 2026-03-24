package dgtic.core.siac.repository;

import dgtic.core.siac.model.Categoria;
import dgtic.core.siac.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByActivoTrue();
}

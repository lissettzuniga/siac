package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Page<Categoria> findByActivoTrue(Pageable pageable);

    Page<Categoria> findByActivoFalse(Pageable pageable);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}

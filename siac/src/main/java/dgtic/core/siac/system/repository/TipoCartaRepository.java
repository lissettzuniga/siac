package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.TipoCarta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCartaRepository extends JpaRepository<TipoCarta, Long> {

    Page<TipoCarta> findByActivoTrue(Pageable pageable);

    Page<TipoCarta> findByActivoFalse(Pageable pageable);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id);
}
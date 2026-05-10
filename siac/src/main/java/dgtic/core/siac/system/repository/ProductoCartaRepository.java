package dgtic.core.siac.system.repository;

import dgtic.core.siac.system.model.ProductoCarta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoCartaRepository extends JpaRepository<ProductoCarta,Long> {

    Page<ProductoCarta> findByActivoTrue(Pageable pageable);

    Page<ProductoCarta> findByActivoFalse(Pageable pageable);

    Page<ProductoCarta> findByTipoCartaIdAndActivoTrue(Long tipoCartaId, Pageable pageable);

    boolean existsByProductoId(Long id);

    boolean existsByProductoIdAndIdNot(Long productoId, Long id);

}

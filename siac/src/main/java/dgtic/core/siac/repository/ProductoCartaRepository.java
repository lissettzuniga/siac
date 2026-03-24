package dgtic.core.siac.repository;

import dgtic.core.siac.model.ProductoCarta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoCartaRepository extends JpaRepository<ProductoCarta,Long> {

    List<ProductoCarta> findByActivoTrue();
}

package dgtic.core.siac.repository;

import dgtic.core.siac.model.TipoCarta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoCartaRepository extends JpaRepository<TipoCarta,Long> {

    List<TipoCarta> findByActivoTrue();
}

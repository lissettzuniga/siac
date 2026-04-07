package dgtic.core.siac.service.tipoCarta;

import dgtic.core.siac.model.TipoCarta;

import java.util.List;
import java.util.Optional;

public interface TipoCartaInterface {
    List<TipoCarta> findAllActivos();
    List<TipoCarta> findAllInactivos();
    Optional<TipoCarta> findById(Long id);
    TipoCarta save(TipoCarta tipoCarta);
    void activar(Long id);
    void desactivar(Long id);
}

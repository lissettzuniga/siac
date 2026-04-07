package dgtic.core.siac.service.categoria;

import dgtic.core.siac.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    List<Categoria> findAllActivos();
    List<Categoria> findAllInactivos();
    Optional<Categoria> findById(Long id);
    Categoria save(Categoria categoria);
    void activar(Long id);
    void desactivar(Long id);
}

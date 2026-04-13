package dgtic.core.siac.service.categoria;

import dgtic.core.siac.dto.categoria.CategoriaRequestDTO;
import dgtic.core.siac.dto.categoria.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponseDTO> findAllActivos();
    List<CategoriaResponseDTO> findAllInactivos();
    CategoriaResponseDTO findById(Long id);
    CategoriaResponseDTO create(CategoriaRequestDTO request);
    CategoriaResponseDTO update(Long id, CategoriaRequestDTO request);
    void desactivar(Long id);
    void activar(Long id);
}

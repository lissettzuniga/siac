package dgtic.core.siac.system.service.categoria;

import dgtic.core.siac.system.dto.categoria.CategoriaRequestDTO;
import dgtic.core.siac.system.dto.categoria.CategoriaResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CategoriaService {

    Page<CategoriaResponseDTO> findAllActivos(Pageable pageable);
    Page<CategoriaResponseDTO> findAllInactivos(Pageable pageable);

    CategoriaResponseDTO findById(Long id);
    CategoriaResponseDTO create(CategoriaRequestDTO request);
    CategoriaResponseDTO update(Long id, CategoriaRequestDTO request);

    void deactivate(Long id);
    void activate(Long id);
}

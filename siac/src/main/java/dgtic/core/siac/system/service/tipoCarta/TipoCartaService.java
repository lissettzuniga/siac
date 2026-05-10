package dgtic.core.siac.system.service.tipoCarta;

import dgtic.core.siac.system.dto.tipoCarta.TipoCartaRequestDTO;
import dgtic.core.siac.system.dto.tipoCarta.TipoCartaResponseDTO;
import dgtic.core.siac.system.model.TipoCarta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TipoCartaService {

    Page<TipoCartaResponseDTO> findAllActivos(Pageable pageable);

    Page<TipoCartaResponseDTO> findAllInactivos(Pageable pageable);

    TipoCartaResponseDTO findById(Long id);

    TipoCartaResponseDTO create(TipoCartaRequestDTO request);

    TipoCartaResponseDTO update(Long id, TipoCartaRequestDTO request);

    void deactivate(Long id);

    void activate(Long id);
}

package dgtic.core.siac.system.service.busquedaImagen;

import dgtic.core.siac.system.dto.busquedaImagen.BusquedaImagenResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface BusquedaImagenService {
    BusquedaImagenResponseDTO buscarPorImagen(MultipartFile imagen);
}
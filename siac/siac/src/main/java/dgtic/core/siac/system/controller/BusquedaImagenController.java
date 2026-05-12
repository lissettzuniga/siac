package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.busquedaImagen.BusquedaImagenResponseDTO;
import dgtic.core.siac.system.service.busquedaImagen.BusquedaImagenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/busqueda-imagen")
@RequiredArgsConstructor
public class BusquedaImagenController {

    private final BusquedaImagenService busquedaImagenService;

    @PostMapping
    public ResponseEntity<BusquedaImagenResponseDTO> buscarPorImagen(
            @RequestParam("imagen") MultipartFile imagen) {

        return ResponseEntity.ok(busquedaImagenService.buscarPorImagen(imagen));
    }
}
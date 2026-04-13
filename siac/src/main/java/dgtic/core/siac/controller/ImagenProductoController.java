package dgtic.core.siac.controller;

import dgtic.core.siac.dto.imagenProducto.ImagenProductoRequestDTO;
import dgtic.core.siac.dto.imagenProducto.ImagenProductoResponseDTO;
import dgtic.core.siac.service.imagenProducto.ImagenProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imagenes-producto")
public class ImagenProductoController {

    private final ImagenProductoService imagenProductoService;

    public ImagenProductoController(ImagenProductoService imagenProductoService) {
        this.imagenProductoService = imagenProductoService;
    }

    @GetMapping
    public ResponseEntity<List<ImagenProductoResponseDTO>> findAllActivas() {
        return ResponseEntity.ok(imagenProductoService.findAllActivas());
    }

    @GetMapping("/inactivas")
    public ResponseEntity<List<ImagenProductoResponseDTO>> findAllInactivas() {
        return ResponseEntity.ok(imagenProductoService.findAllInactivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenProductoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(imagenProductoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ImagenProductoResponseDTO> create(@Valid @RequestBody ImagenProductoRequestDTO request) {
        ImagenProductoResponseDTO imagenCreada = imagenProductoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(imagenCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImagenProductoResponseDTO> update(@PathVariable Long id,
                                                            @Valid @RequestBody ImagenProductoRequestDTO request) {
        return ResponseEntity.ok(imagenProductoService.update(id, request));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<String> activar(@PathVariable Long id) {
        imagenProductoService.activar(id);
        return ResponseEntity.ok("Imagen activada correctamente.");
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<String> desactivar(@PathVariable Long id) {
        imagenProductoService.desactivar(id);
        return ResponseEntity.ok("Imagen desactivada correctamente.");
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteLogico(@PathVariable Long id) {
//        imagenProductoService.desactivar(id);
//        return ResponseEntity.ok("Imagen desactivada correctamente.");
//    }
}
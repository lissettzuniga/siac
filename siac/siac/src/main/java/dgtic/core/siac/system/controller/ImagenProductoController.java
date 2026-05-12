package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.imagenProducto.ImagenProductoRequestDTO;
import dgtic.core.siac.system.dto.imagenProducto.ImagenProductoResponseDTO;
import dgtic.core.siac.system.service.imagenProducto.ImagenProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imagenes-producto")
@RequiredArgsConstructor
public class ImagenProductoController {

    private final ImagenProductoService imagenProductoService;


    @GetMapping
    public ResponseEntity<Page<ImagenProductoResponseDTO>> findAllActivas(Pageable pageable) {
        Page<ImagenProductoResponseDTO> imagenes = imagenProductoService.findAllActivos(pageable);
        return ResponseEntity.ok(imagenes);
    }

    @GetMapping("/inactivas")
    public ResponseEntity<Page<ImagenProductoResponseDTO>> findAllInactivas(Pageable pageable) {
        Page<ImagenProductoResponseDTO> imagenes = imagenProductoService.findAllInactivos(pageable);
        return ResponseEntity.ok(imagenes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagenProductoResponseDTO> findById(@PathVariable Long id) {
        ImagenProductoResponseDTO imagen = imagenProductoService.findById(id);
        return ResponseEntity.ok(imagen);
    }

    @PostMapping
    public ResponseEntity<ImagenProductoResponseDTO> create(
            @Valid @RequestBody ImagenProductoRequestDTO request) {

        ImagenProductoResponseDTO imagenCreada = imagenProductoService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(imagenCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImagenProductoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ImagenProductoRequestDTO request) {

        ImagenProductoResponseDTO imagenActualizada = imagenProductoService.update(id, request);
        return ResponseEntity.ok(imagenActualizada);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(
            @PathVariable Long id) {

        imagenProductoService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(
            @PathVariable Long id) {

        imagenProductoService.activate(id);
        return ResponseEntity.noContent().build();
    }
}
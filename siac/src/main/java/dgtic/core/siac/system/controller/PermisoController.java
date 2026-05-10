package dgtic.core.siac.system.controller;


import dgtic.core.siac.system.dto.permiso.PermisoRequestDTO;
import dgtic.core.siac.system.dto.permiso.PermisoResponseDTO;
import dgtic.core.siac.system.service.permiso.PermisoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
public class PermisoController {

    private final PermisoService permisoService;

    @GetMapping
    public ResponseEntity<Page<PermisoResponseDTO>> findAllActivos(Pageable pageable) {
        return ResponseEntity.ok(permisoService.findAllActivos(pageable));
    }

    @GetMapping("/inactivos")
    public ResponseEntity<Page<PermisoResponseDTO>> findAllInactivos(Pageable pageable) {
        return ResponseEntity.ok(permisoService.findAllInactivos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermisoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(permisoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PermisoResponseDTO> create(@Valid @RequestBody PermisoRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(permisoService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermisoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PermisoRequestDTO request) {

        return ResponseEntity.ok(permisoService.update(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        permisoService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        permisoService.activate(id);
        return ResponseEntity.noContent().build();
    }
}
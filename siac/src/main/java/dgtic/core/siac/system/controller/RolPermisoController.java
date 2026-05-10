package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.rolPermiso.RolPermisoRequestDTO;
import dgtic.core.siac.system.dto.rolPermiso.RolPermisoResponseDTO;
import dgtic.core.siac.system.service.rolPermiso.RolPermisoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rol-permisos")
@RequiredArgsConstructor
public class RolPermisoController {

    private final RolPermisoService rolPermisoService;

    @GetMapping
    public ResponseEntity<Page<RolPermisoResponseDTO>> findAllActivos(Pageable pageable) {
        return ResponseEntity.ok(rolPermisoService.findAllActivos(pageable));
    }

    @GetMapping("/inactivos")
    public ResponseEntity<Page<RolPermisoResponseDTO>> findAllInactivos(Pageable pageable) {
        return ResponseEntity.ok(rolPermisoService.findAllInactivos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolPermisoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(rolPermisoService.findById(id));
    }

    @GetMapping("/rol/{rolId}")
    public ResponseEntity<Page<RolPermisoResponseDTO>> findByRol(
            @PathVariable Long rolId,
            Pageable pageable) {

        return ResponseEntity.ok(rolPermisoService.findByRol(rolId, pageable));
    }

    @GetMapping("/permiso/{permisoId}")
    public ResponseEntity<Page<RolPermisoResponseDTO>> findByPermiso(
            @PathVariable Long permisoId,
            Pageable pageable) {

        return ResponseEntity.ok(rolPermisoService.findByPermiso(permisoId, pageable));
    }

    @PostMapping
    public ResponseEntity<RolPermisoResponseDTO> create(
            @Valid @RequestBody RolPermisoRequestDTO request) {

        RolPermisoResponseDTO response = rolPermisoService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolPermisoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RolPermisoRequestDTO request) {

        return ResponseEntity.ok(rolPermisoService.update(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        rolPermisoService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        rolPermisoService.activate(id);
        return ResponseEntity.noContent().build();
    }
}

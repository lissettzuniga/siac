package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.rol.RolRequestDTO;
import dgtic.core.siac.system.dto.rol.RolResponseDTO;
import dgtic.core.siac.system.service.rol.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<Page<RolResponseDTO>> findAllActivos(Pageable pageable) {
        return ResponseEntity.ok(rolService.findAllActivos(pageable));
    }

    @GetMapping("/inactivos")
    public ResponseEntity<Page<RolResponseDTO>> findAllInactivos(Pageable pageable) {
        return ResponseEntity.ok(rolService.findAllInactivos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RolResponseDTO> create(
            @Valid @RequestBody RolRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rolService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody RolRequestDTO request) {

        return ResponseEntity.ok(rolService.update(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        rolService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        rolService.activate(id);
        return ResponseEntity.noContent().build();
    }
}

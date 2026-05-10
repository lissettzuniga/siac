package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.tipoMovimiento.TipoMovimientoRequestDTO;
import dgtic.core.siac.system.dto.tipoMovimiento.TipoMovimientoResponseDTO;
import dgtic.core.siac.system.service.tipoMovimiento.TipoMovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipos-movimiento")
@RequiredArgsConstructor
public class TipoMovimientoController {

    private final TipoMovimientoService tipoMovimientoService;

    @GetMapping
    public ResponseEntity<Page<TipoMovimientoResponseDTO>> findAllActivos(Pageable pageable) {
        return ResponseEntity.ok(tipoMovimientoService.findAllActivos(pageable));
    }

    @GetMapping("/inactivos")
    public ResponseEntity<Page<TipoMovimientoResponseDTO>> findAllInactivos(Pageable pageable) {
        return ResponseEntity.ok(tipoMovimientoService.findAllInactivos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoMovimientoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoMovimientoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TipoMovimientoResponseDTO> create(
            @Valid @RequestBody TipoMovimientoRequestDTO request) {

        TipoMovimientoResponseDTO response = tipoMovimientoService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoMovimientoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody TipoMovimientoRequestDTO request) {

        return ResponseEntity.ok(tipoMovimientoService.update(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        tipoMovimientoService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        tipoMovimientoService.activate(id);
        return ResponseEntity.noContent().build();
    }
}

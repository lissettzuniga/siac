package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.tipoCarta.TipoCartaRequestDTO;
import dgtic.core.siac.system.dto.tipoCarta.TipoCartaResponseDTO;
import dgtic.core.siac.system.service.tipoCarta.TipoCartaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tipos-carta")
@RequiredArgsConstructor
public class TipoCartaController {

    private final TipoCartaService tipoCartaService;

    @GetMapping
    public ResponseEntity<Page<TipoCartaResponseDTO>> findAllActivos(Pageable pageable) {
        return ResponseEntity.ok(tipoCartaService.findAllActivos(pageable));
    }

    @GetMapping("/inactivos")
    public ResponseEntity<Page<TipoCartaResponseDTO>> findAllInactivos(Pageable pageable) {
        return ResponseEntity.ok(tipoCartaService.findAllInactivos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCartaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tipoCartaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TipoCartaResponseDTO> create(
            @Valid @RequestBody TipoCartaRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tipoCartaService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCartaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody TipoCartaRequestDTO request) {

        return ResponseEntity.ok(tipoCartaService.update(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        tipoCartaService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        tipoCartaService.activate(id);
        return ResponseEntity.noContent().build();
    }
}
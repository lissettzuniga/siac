package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.productoCarta.ProductoCartaRequestDTO;
import dgtic.core.siac.system.dto.productoCarta.ProductoCartaResponseDTO;
import dgtic.core.siac.system.service.productoCarta.ProductoCartaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos-carta")
@RequiredArgsConstructor
public class ProductoCartaController {

    private final ProductoCartaService productoCartaService;

    @GetMapping
    public ResponseEntity<Page<ProductoCartaResponseDTO>> findAllActivos(Pageable pageable) {
        return ResponseEntity.ok(productoCartaService.findAllActivos(pageable));
    }

    @GetMapping("/inactivos")
    public ResponseEntity<Page<ProductoCartaResponseDTO>> findAllInactivos(Pageable pageable) {
        return ResponseEntity.ok(productoCartaService.findAllInactivos(pageable));
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<ProductoCartaResponseDTO> findById(@PathVariable Long productoId) {
        return ResponseEntity.ok(productoCartaService.findById(productoId));
    }

    @GetMapping("/tipo-carta/{tipoCartaId}")
    public ResponseEntity<Page<ProductoCartaResponseDTO>> findByTipoCarta(
            @PathVariable Long tipoCartaId,
            Pageable pageable) {

        return ResponseEntity.ok(productoCartaService.findByTipoCarta(tipoCartaId, pageable));
    }

    @PostMapping
    public ResponseEntity<ProductoCartaResponseDTO> create(
            @Valid @RequestBody ProductoCartaRequestDTO request) {

        ProductoCartaResponseDTO response = productoCartaService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<ProductoCartaResponseDTO> update(
            @PathVariable Long productoId,
            @Valid @RequestBody ProductoCartaRequestDTO request) {

        return ResponseEntity.ok(productoCartaService.update(productoId, request));
    }

    @PatchMapping("/{productoId}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long productoId) {
        productoCartaService.deactivate(productoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{productoId}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long productoId) {
        productoCartaService.activate(productoId);
        return ResponseEntity.noContent().build();
    }
}
package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.producto.ProductoRequestDTO;
import dgtic.core.siac.system.dto.producto.ProductoResponseDTO;
import dgtic.core.siac.system.service.producto.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<Page<ProductoResponseDTO>> findAllActivos(Pageable pageable) {
        Page<ProductoResponseDTO> productos = productoService.findAllActivos(pageable);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/inactivos")
    public ResponseEntity<Page<ProductoResponseDTO>> findAllInactivos(Pageable pageable) {
        Page<ProductoResponseDTO> productos = productoService.findAllInactivos(pageable);
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(@PathVariable Long id) {
        ProductoResponseDTO producto = productoService.findById(id);
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> create(
            @Valid @RequestBody ProductoRequestDTO request) {

        ProductoResponseDTO productoCreado = productoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO request) {

        ProductoResponseDTO productoActualizado = productoService.update(id, request);
        return ResponseEntity.ok(productoActualizado);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        productoService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        productoService.activate(id);
        return ResponseEntity.noContent().build();
    }
}
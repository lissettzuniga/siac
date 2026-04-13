package dgtic.core.siac.controller;

import dgtic.core.siac.dto.producto.ProductoRequestDTO;
import dgtic.core.siac.dto.producto.ProductoResponseDTO;
import dgtic.core.siac.service.producto.ProductoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> findAllActivos() {
        return ResponseEntity.ok(productoService.findAllActivos());
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<ProductoResponseDTO>> findAllInactivos() {
        return ResponseEntity.ok(productoService.findAllInactivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> create(@Valid @RequestBody ProductoRequestDTO request) {
        ProductoResponseDTO productoCreado = productoService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody ProductoRequestDTO request) {
        return ResponseEntity.ok(productoService.update(id, request));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<String> activar(@PathVariable Long id) {
        productoService.activar(id);
        return ResponseEntity.ok("Producto activado correctamente.");
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<String> desactivar(@PathVariable Long id) {
        productoService.desactivar(id);
        return ResponseEntity.ok("Producto desactivado correctamente.");
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteLogico(@PathVariable Long id) {
//        productoService.desactivar(id);
//        return ResponseEntity.ok("Producto desactivado correctamente.");
//    }
}
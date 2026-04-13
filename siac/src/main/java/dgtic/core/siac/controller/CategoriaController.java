package dgtic.core.siac.controller;

import dgtic.core.siac.dto.categoria.CategoriaRequestDTO;
import dgtic.core.siac.dto.categoria.CategoriaResponseDTO;
import dgtic.core.siac.service.categoria.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> findAllActivos() {
        return ResponseEntity.ok(categoriaService.findAllActivos());
    }

    @GetMapping("/inactivas")
    public ResponseEntity<List<CategoriaResponseDTO>> findAllInactivos() {
        return ResponseEntity.ok(categoriaService.findAllInactivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> create(@Valid @RequestBody CategoriaRequestDTO request) {
        CategoriaResponseDTO categoriaCreada = categoriaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody CategoriaRequestDTO request) {
        return ResponseEntity.ok(categoriaService.update(id, request));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<String> activar(@PathVariable Long id) {
        categoriaService.activar(id);
        return ResponseEntity.ok("Categoría activada correctamente.");
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<String> desactivar(@PathVariable Long id) {
        categoriaService.desactivar(id);
        return ResponseEntity.ok("Categoría desactivada correctamente.");
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteLogico(@PathVariable Long id) {
//        categoriaService.desactivar(id);
//        return ResponseEntity.ok("Categoría desactivada correctamente.");
//    }
}
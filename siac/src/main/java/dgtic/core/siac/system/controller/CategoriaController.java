package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.categoria.CategoriaRequestDTO;
import dgtic.core.siac.system.dto.categoria.CategoriaResponseDTO;
import dgtic.core.siac.system.service.categoria.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDTO>> findAllActivos(Pageable pageable) {
        Page<CategoriaResponseDTO> categorias = categoriaService.findAllActivos(pageable);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/inactivas")
    public ResponseEntity<Page<CategoriaResponseDTO>> findAllInactivos(Pageable pageable) {
        Page<CategoriaResponseDTO> categorias = categoriaService.findAllInactivos(pageable);
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        CategoriaResponseDTO categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> create(
            @Valid @RequestBody CategoriaRequestDTO request) {

        CategoriaResponseDTO categoriaCreada = categoriaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO request) {

        CategoriaResponseDTO categoriaActualizada = categoriaService.update(id, request);
        return ResponseEntity.ok(categoriaActualizada);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        categoriaService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        categoriaService.activate(id);
        return ResponseEntity.noContent().build();
    }
}
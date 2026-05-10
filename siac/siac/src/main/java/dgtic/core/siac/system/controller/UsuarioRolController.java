package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.usuarioRol.UsuarioRolRequestDTO;
import dgtic.core.siac.system.dto.usuarioRol.UsuarioRolResponseDTO;
import dgtic.core.siac.system.service.usuarioRol.UsuarioRolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario-roles")
@RequiredArgsConstructor
public class UsuarioRolController {

    private final UsuarioRolService usuarioRolService;

    @GetMapping
    public ResponseEntity<Page<UsuarioRolResponseDTO>> findAllActivos(Pageable pageable) {
        return ResponseEntity.ok(usuarioRolService.findAllActivos(pageable));
    }

    @GetMapping("/inactivos")
    public ResponseEntity<Page<UsuarioRolResponseDTO>> findAllInactivos(Pageable pageable) {
        return ResponseEntity.ok(usuarioRolService.findAllInactivos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioRolResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioRolService.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<UsuarioRolResponseDTO>> findByUsuario(
            @PathVariable Long usuarioId,
            Pageable pageable) {

        return ResponseEntity.ok(usuarioRolService.findByUsuario(usuarioId, pageable));
    }

    @GetMapping("/rol/{rolId}")
    public ResponseEntity<Page<UsuarioRolResponseDTO>> findByRol(
            @PathVariable Long rolId,
            Pageable pageable) {

        return ResponseEntity.ok(usuarioRolService.findByRol(rolId, pageable));
    }

    @PostMapping
    public ResponseEntity<UsuarioRolResponseDTO> create(
            @Valid @RequestBody UsuarioRolRequestDTO request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioRolService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioRolResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRolRequestDTO request) {

        return ResponseEntity.ok(usuarioRolService.update(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        usuarioRolService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        usuarioRolService.activate(id);
        return ResponseEntity.noContent().build();
    }
}
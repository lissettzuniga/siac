package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.usuario.ChangePasswordRequestDTO;
import dgtic.core.siac.system.dto.usuario.UsuarioRequestDTO;
import dgtic.core.siac.system.dto.usuario.UsuarioResponseDTO;
import dgtic.core.siac.system.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> findAllActivos(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.findAllActivos(pageable));
    }

    @GetMapping("/inactivos")
    public ResponseEntity<Page<UsuarioResponseDTO>> findAllInactivos(Pageable pageable) {
        return ResponseEntity.ok(usuarioService.findAllInactivos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioResponseDTO> findByCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioService.findByCorreo(correo));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(
            @Valid @RequestBody UsuarioRequestDTO request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.create(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioAutenticado() {
        return ResponseEntity.ok(usuarioService.getUsuarioAutenticado());
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequestDTO request
    ) {
        usuarioService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO request) {

        return ResponseEntity.ok(usuarioService.update(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        usuarioService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        usuarioService.activate(id);
        return ResponseEntity.noContent().build();
    }


}
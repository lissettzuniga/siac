package dgtic.core.siac.controller;

import dgtic.core.siac.dto.usuario.UsuarioRequestDTO;
import dgtic.core.siac.dto.usuario.UsuarioResponseDTO;
import dgtic.core.siac.service.usuario.UsuarioService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAllActivos(){
        return ResponseEntity.ok(usuarioService.findAllActivos());
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<UsuarioResponseDTO>> findAllInactivos() {
        return ResponseEntity.ok(usuarioService.findAllInactivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO usuarioCreado = usuarioService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody UsuarioRequestDTO request) {
        return ResponseEntity.ok(usuarioService.update(id, request));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<String> activar(@PathVariable Long id) {
        usuarioService.activar(id);
        return ResponseEntity.ok("Usuario activado correctamente.");
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<String> desactivar(@PathVariable Long id) {
        usuarioService.desactivar(id);
        return ResponseEntity.ok("Usuario desactivado correctamente.");
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteLogico(@PathVariable Long id) {
//        usuarioService.desactivar(id);
//        return ResponseEntity.ok("Usuario desactivado correctamente.");
//    }
}
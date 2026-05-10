package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.bitacoraMovimiento.BitacoraMovimientoResponseDTO;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.service.bitacoraMovimiento.BitacoraMovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bitacora-movimientos")
@RequiredArgsConstructor
public class BitacoraMovimientoController {

    private final BitacoraMovimientoService bitacoraMovimientoService;

    @GetMapping
    public ResponseEntity<Page<BitacoraMovimientoResponseDTO>> findAll(
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(bitacoraMovimientoService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BitacoraMovimientoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bitacoraMovimientoService.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<BitacoraMovimientoResponseDTO>> findByUsuarioId(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(bitacoraMovimientoService.findByUsuarioId(usuarioId, pageable));
    }

    @GetMapping("/entidad/{entidad}")
    public ResponseEntity<Page<BitacoraMovimientoResponseDTO>> findByEntidad(
            @PathVariable EntidadEnum entidad,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(bitacoraMovimientoService.findByEntidad(entidad, pageable));
    }

    @GetMapping("/filtro")
    public ResponseEntity<Page<BitacoraMovimientoResponseDTO>> findByUsuarioIdAndEntidad(
            @RequestParam Long usuarioId,
            @RequestParam EntidadEnum entidad,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                bitacoraMovimientoService.findByUsuarioIdAndEntidad(usuarioId, entidad, pageable)
        );
    }
}
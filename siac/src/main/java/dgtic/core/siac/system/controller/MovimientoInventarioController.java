package dgtic.core.siac.system.controller;

import dgtic.core.siac.system.dto.movimientoInventario.MovimientoInventarioRequestDTO;
import dgtic.core.siac.system.dto.movimientoInventario.MovimientoInventarioResponseDTO;
import dgtic.core.siac.system.service.movimientoInventario.MovimientoInventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movimientos-inventario")
@RequiredArgsConstructor
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoInventarioService;

    @GetMapping
    public ResponseEntity<Page<MovimientoInventarioResponseDTO>> findAll(
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(movimientoInventarioService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoInventarioResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(movimientoInventarioService.findById(id));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<Page<MovimientoInventarioResponseDTO>> findByProductoId(
            @PathVariable Long productoId,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                movimientoInventarioService.findByProductoId(productoId, pageable)
        );
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<MovimientoInventarioResponseDTO>> findByUsuarioId(
            @PathVariable Long usuarioId,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                movimientoInventarioService.findByUsuarioId(usuarioId, pageable)
        );
    }

    @GetMapping("/tipo/{clave}")
    public ResponseEntity<Page<MovimientoInventarioResponseDTO>> findByTipoMovimientoClave(
            @PathVariable String clave,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                movimientoInventarioService.findByTipoMovimientoClave(clave, pageable)
        );
    }

    @GetMapping("/reporte")
    public ResponseEntity<Page<MovimientoInventarioResponseDTO>> findByProductoIdAndTipoMovimientoClave(
            @RequestParam Long productoId,
            @RequestParam String tipo,
            @PageableDefault(size = 10, sort = "fecha", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return ResponseEntity.ok(
                movimientoInventarioService.findByProductoIdAndTipoMovimientoClave(
                        productoId,
                        tipo,
                        pageable
                )
        );
    }

    @PostMapping
    public ResponseEntity<MovimientoInventarioResponseDTO> create(
            @Valid @RequestBody MovimientoInventarioRequestDTO request) {

        MovimientoInventarioResponseDTO response = movimientoInventarioService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(
            value = "/reporte/excel",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    )
    public ResponseEntity<byte[]> exportReporteInventarioToExcel(
            @RequestParam Long productoId,
            @RequestParam String tipo
    ) {
        byte[] excel = movimientoInventarioService.exportReporteInventarioToExcel(productoId, tipo);

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "attachment; filename=reporte_inventario.xlsx"
                )
                .body(excel);
    }
}
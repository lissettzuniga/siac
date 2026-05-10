package dgtic.core.siac.system.service.movimientoInventario;

import dgtic.core.siac.security.SecurityUtils;
import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.movimientoInventario.MovimientoInventarioRequestDTO;
import dgtic.core.siac.system.dto.movimientoInventario.MovimientoInventarioResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.*;
import dgtic.core.siac.system.mapper.MovimientoInventarioMapper;
import dgtic.core.siac.system.model.MovimientoInventario;
import dgtic.core.siac.system.model.Producto;
import dgtic.core.siac.system.model.TipoMovimiento;
import dgtic.core.siac.system.model.Usuario;
import dgtic.core.siac.system.repository.MovimientoInventarioRepository;
import dgtic.core.siac.system.repository.ProductoRepository;
import dgtic.core.siac.system.repository.TipoMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private static final String CLAVE_ENTRADA = "ENTRADA";
    private static final String CLAVE_SALIDA = "SALIDA";

    private final MovimientoInventarioRepository movimientoInventarioRepository;
    private final ProductoRepository productoRepository;
    private final TipoMovimientoRepository tipoMovimientoRepository;
    private final MovimientoInventarioMapper movimientoInventarioMapper;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoInventarioResponseDTO> findAll(Pageable pageable) {
        return movimientoInventarioRepository.findAll(pageable)
                .map(movimientoInventarioMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoInventarioResponseDTO> findByProductoId(Long productoId, Pageable pageable) {
        return movimientoInventarioRepository.findByProductoId(productoId, pageable)
                .map(movimientoInventarioMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoInventarioResponseDTO> findByUsuarioId(Long usuarioId, Pageable pageable) {
        return movimientoInventarioRepository.findByUsuarioId(usuarioId, pageable)
                .map(movimientoInventarioMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoInventarioResponseDTO> findByTipoMovimientoClave(String clave, Pageable pageable) {
        return movimientoInventarioRepository
                .findByTipoMovimiento_Clave(normalizeClave(clave), pageable)
                .map(movimientoInventarioMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MovimientoInventarioResponseDTO> findByProductoIdAndTipoMovimientoClave(
            Long productoId,
            String clave,
            Pageable pageable
    ) {
        return movimientoInventarioRepository
                .findByProductoIdAndTipoMovimiento_Clave(productoId, normalizeClave(clave), pageable)
                .map(movimientoInventarioMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoInventarioResponseDTO findById(Long id) {
        MovimientoInventario movimiento = movimientoInventarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Movimiento de inventario no encontrado con id: " + id
                ));

        return movimientoInventarioMapper.toResponseDTO(movimiento);
    }

    @Auditable(
            entidad = EntidadEnum.MOVIMIENTO_INVENTARIO,
            accion = AccionEnum.CREAR,
            descripcion = "Se creó un movimiento de inventario"
    )
    @Override
    @Transactional
    public MovimientoInventarioResponseDTO create(MovimientoInventarioRequestDTO request) {

        Producto producto = productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new ProductoNotFoundException(request.getProductoId()));

        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(request.getTipoMovimientoId())
                .orElseThrow(() -> new TipoMovimientoNotFoundException(request.getTipoMovimientoId()));

        Usuario usuarioAutenticado = securityUtils.getUsuarioAutenticado();

        validateCantidad(request.getCantidad());

        applyInventoryMovement(producto, tipoMovimiento, request.getCantidad());

        MovimientoInventario movimiento = movimientoInventarioMapper.toEntity(request);
        movimiento.setProducto(producto);
        movimiento.setTipoMovimiento(tipoMovimiento);
        movimiento.setUsuario(usuarioAutenticado);

        MovimientoInventario saved = movimientoInventarioRepository.save(movimiento);
        productoRepository.save(producto);

        return movimientoInventarioMapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] exportReporteInventarioToExcel(Long productoId, String tipo) {

        String clave = normalizeClave(tipo);

        List<MovimientoInventario> movimientos =
                movimientoInventarioRepository.findByProductoIdAndTipoMovimiento_Clave(
                        productoId,
                        clave,
                        Pageable.unpaged()
                ).getContent();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Reporte inventario");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID Movimiento");
            header.createCell(1).setCellValue("Producto");
            header.createCell(2).setCellValue("Categoría");
            header.createCell(3).setCellValue("Tipo Movimiento");
            header.createCell(4).setCellValue("Cantidad");
            header.createCell(5).setCellValue("Precio Unitario");
            header.createCell(6).setCellValue("Valor Total");
            header.createCell(7).setCellValue("Usuario");
            header.createCell(8).setCellValue("Fecha");

            int rowIndex = 1;

            for (MovimientoInventario movimiento : movimientos) {
                Producto producto = movimiento.getProducto();

                BigDecimal valorTotal = producto.getPrecio()
                        .multiply(BigDecimal.valueOf(movimiento.getCantidad()));

                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(movimiento.getId());
                row.createCell(1).setCellValue(producto.getNombre());
                row.createCell(2).setCellValue(producto.getCategoria().getNombre());
                row.createCell(3).setCellValue(movimiento.getTipoMovimiento().getNombre());
                row.createCell(4).setCellValue(movimiento.getCantidad());
                row.createCell(5).setCellValue(producto.getPrecio().doubleValue());
                row.createCell(6).setCellValue(valorTotal.doubleValue());
                row.createCell(7).setCellValue(movimiento.getUsuario().getCorreo());
                row.createCell(8).setCellValue(movimiento.getFecha().toString());
            }

            for (int i = 0; i <= 8; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (IOException e) {
            throw new BusinessException("Error al generar el reporte de inventario en Excel");
        }
    }

    private void validateCantidad(Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new BusinessException("La cantidad debe ser mayor a cero");
        }
    }

    private void applyInventoryMovement(
            Producto producto,
            TipoMovimiento tipoMovimiento,
            Integer cantidad
    ) {
        String clave = normalizeClave(tipoMovimiento.getClave());

        if (CLAVE_ENTRADA.equals(clave)) {
            producto.setCantidadActual(producto.getCantidadActual() + cantidad);
            return;
        }

        if (CLAVE_SALIDA.equals(clave)) {
            if (producto.getCantidadActual() < cantidad) {
                throw new BusinessException("Stock insuficiente para realizar la salida");
            }

            producto.setCantidadActual(producto.getCantidadActual() - cantidad);
            return;
        }

        throw new BusinessException("Tipo de movimiento inválido: " + tipoMovimiento.getClave());
    }

    private String normalizeClave(String clave) {
        if (clave == null || clave.isBlank()) {
            throw new BusinessException("La clave del tipo de movimiento es obligatoria");
        }

        return clave.trim().toUpperCase();
    }
}
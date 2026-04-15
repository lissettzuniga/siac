package dgtic.core.siac.mapper;

import dgtic.core.siac.dto.movimientoInventario.MovimientoInventarioRequestDTO;
import dgtic.core.siac.dto.movimientoInventario.MovimientoInventarioResponseDTO;
import dgtic.core.siac.model.MovimientoInventario;
import org.springframework.stereotype.Component;

@Component
public class MovimientoInventarioMapper {

    public MovimientoInventario toEntity(MovimientoInventarioRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return MovimientoInventario.builder()
                .cantidad(dto.getCantidad())
                .comentario(dto.getComentario())
                .activo(true)
                .build();
    }

    public MovimientoInventarioResponseDTO toResponseDTO(MovimientoInventario entity) {
        if (entity == null) {
            return null;
        }

        var producto = entity.getProducto();
        var tipoMovimiento = entity.getTipoMovimiento();
        var usuario = entity.getUsuario();

        return MovimientoInventarioResponseDTO.builder()
                .id(entity.getId())
                .productoId(producto != null ? producto.getId() : null)
                .productoNombre(producto != null ? producto.getNombre() : null)
                .tipoMovimientoId(tipoMovimiento != null ? tipoMovimiento.getId() : null)
                .tipoMovimientoNombre(tipoMovimiento != null ? tipoMovimiento.getNombre() : null)
                .usuarioId(usuario != null ? usuario.getId() : null)
                .usuarioNombre(usuario != null
                        ? usuario.getNombre() + " " + usuario.getApPaterno()
                        : null)
                .cantidad(entity.getCantidad())
                .comentario(entity.getComentario())
                .fecha(entity.getFecha())
                .activo(entity.getActivo())
                .build();
    }

    public void updateEntityFromDTO(MovimientoInventarioRequestDTO dto, MovimientoInventario entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setCantidad(dto.getCantidad());
        entity.setComentario(dto.getComentario());
    }
}

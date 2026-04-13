package dgtic.core.siac.mapper;

import dgtic.core.siac.dto.producto.ProductoResponseDTO;
import dgtic.core.siac.model.Producto;

public class ProductoMapper {

    public static ProductoResponseDTO toResponseDTO(Producto producto) {
        if (producto == null) {
            return null;
        }

        return ProductoResponseDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .cantidadActual(producto.getCantidadActual())
                .fechaCreacion(producto.getFechaCreacion())
                .activo(producto.getActivo())
                .usuarioId(producto.getUsuario() != null ? producto.getUsuario().getId() : null)
                .usuarioNombre(producto.getUsuario() != null
                        ? producto.getUsuario().getNombre() + " " + producto.getUsuario().getApPaterno()
                        : null)
                .categoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null)
                .categoriaNombre(producto.getCategoria() != null ? producto.getCategoria().getNombre() : null)
                .build();
    }
}
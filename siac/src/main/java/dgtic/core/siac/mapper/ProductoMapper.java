package dgtic.core.siac.mapper;

import dgtic.core.siac.dto.producto.ProductoRequestDTO;
import dgtic.core.siac.dto.producto.ProductoResponseDTO;
import dgtic.core.siac.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto toEntity(ProductoRequestDTO dto){
        if(dto == null){
            return null;
        }

        return Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .cantidadActual(dto.getCantidadActual())
                .activo(true)
                .build();
    }

    public ProductoResponseDTO toResponseDTO(Producto producto) {
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

    public void updateEntityFromDTO(ProductoRequestDTO dto, Producto entity) {
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setCantidadActual(dto.getCantidadActual());
    }
}
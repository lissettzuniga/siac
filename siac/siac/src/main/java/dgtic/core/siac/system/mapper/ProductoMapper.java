package dgtic.core.siac.system.mapper;

import dgtic.core.siac.system.dto.producto.ProductoRequestDTO;
import dgtic.core.siac.system.dto.producto.ProductoResponseDTO;
import dgtic.core.siac.system.model.Producto;
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

        String imagenUrl = null;

        if (producto.getImagenes() != null && !producto.getImagenes().isEmpty()) {
            imagenUrl = producto.getImagenes().stream()
                    .filter(imagen -> Boolean.TRUE.equals(imagen.getActivo()))
                    .map(imagen -> imagen.getRuta())
                    .findFirst()
                    .orElse(null);
        }


        return ProductoResponseDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .cantidadActual(producto.getCantidadActual())
                .fechaCreacion(producto.getFechaCreacion())
                .activo(producto.getActivo())
                .imagenUrl(imagenUrl)
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
package dgtic.core.siac.mapper;

import dgtic.core.siac.dto.imagenProducto.ImagenProductoResponseDTO;
import dgtic.core.siac.model.ImagenProducto;

public class ImagenProductoMapper {

    public static ImagenProductoResponseDTO toResponseDTO(ImagenProducto imagenProducto) {
        if (imagenProducto == null) {
            return null;
        }

        return ImagenProductoResponseDTO.builder()
                .id(imagenProducto.getId())
                .ruta(imagenProducto.getRuta())
                .nombreArchivo(imagenProducto.getNombreArchivo())
                .fechaRegistro(imagenProducto.getFechaRegistro())
                .activo(imagenProducto.getActivo())
                .productoId(imagenProducto.getProducto() != null ? imagenProducto.getProducto().getId() : null)
                .productoNombre(imagenProducto.getProducto() != null ? imagenProducto.getProducto().getNombre() : null)
                .build();
    }
}
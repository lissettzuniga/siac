package dgtic.core.siac.system.mapper;

import dgtic.core.siac.system.dto.imagenProducto.ImagenProductoRequestDTO;
import dgtic.core.siac.system.dto.imagenProducto.ImagenProductoResponseDTO;
import dgtic.core.siac.system.model.ImagenProducto;
import org.springframework.stereotype.Component;

@Component
public class ImagenProductoMapper {

    public ImagenProducto toEntity(ImagenProductoRequestDTO dto){
        if (dto == null){
            return null;
        }

        return ImagenProducto.builder()
                .ruta(dto.getRuta())
                .nombreArchivo(dto.getNombreArchivo())
                .build();

    }

    public ImagenProductoResponseDTO toResponseDTO(ImagenProducto imagenProducto) {
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

    public void updateEntityFromDTO(ImagenProductoRequestDTO dto, ImagenProducto imagenProducto){
        if (dto == null || imagenProducto == null) {
            return;
        }

        imagenProducto.setRuta(dto.getRuta());
        imagenProducto.setNombreArchivo(dto.getNombreArchivo());
    }
}
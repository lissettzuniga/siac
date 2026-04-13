package dgtic.core.siac.mapper;

import dgtic.core.siac.dto.categoria.CategoriaRequestDTO;
import dgtic.core.siac.dto.categoria.CategoriaResponseDTO;
import dgtic.core.siac.model.Categoria;

public class CategoriaMapper {

    // RequestDTO -> Entity
    public static Categoria toEntity(CategoriaRequestDTO request) {
        if (request == null) {
            return null;
        }
        return Categoria.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .activo(true) // activo
                .build();
    }

    // Entity -> ResponseDTO
    public static CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .activo(categoria.getActivo())
                .build();
    }

    // Actualizar Entity
    public static void updateEntityFromDTO(CategoriaRequestDTO request, Categoria categoria) {
        if (request == null || categoria == null) {
            return;
        }

        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());
    }
}
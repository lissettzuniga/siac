package dgtic.core.siac.mapper;

import dgtic.core.siac.dto.rol.RolRequestDTO;
import dgtic.core.siac.dto.rol.RolResponseDTO;
import dgtic.core.siac.model.Rol;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {

    public Rol toEntity(RolRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Rol.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .activo(true) // activo
                .build();
    }

    // Entity -> ResponseDTO
    public RolResponseDTO toResponseDTO(Rol entity) {
        if (entity == null) {
            return null;
        }

        return RolResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .activo(entity.getActivo())
                .build();
    }

    // Actualizar Entity
    public void updateEntityFromDTO(RolRequestDTO dto, Rol entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
    }
}

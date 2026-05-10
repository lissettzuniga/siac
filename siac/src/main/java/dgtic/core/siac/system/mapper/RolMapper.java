package dgtic.core.siac.system.mapper;

import dgtic.core.siac.system.dto.rol.RolRequestDTO;
import dgtic.core.siac.system.dto.rol.RolResponseDTO;
import dgtic.core.siac.system.model.Rol;
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


    public void updateEntityFromDTO(RolRequestDTO dto, Rol entity) {
        if (dto == null || entity == null) {
            return;
        }

        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
    }
}

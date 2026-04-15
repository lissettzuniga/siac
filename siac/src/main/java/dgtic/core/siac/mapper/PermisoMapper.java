package dgtic.core.siac.mapper;

import dgtic.core.siac.dto.permiso.PermisoRequestDTO;
import dgtic.core.siac.dto.permiso.PermisoResponseDTO;
import dgtic.core.siac.model.Permiso;
import org.springframework.stereotype.Component;

@Component
public class PermisoMapper {

    public Permiso toEntity(PermisoRequestDTO dto){
        if (dto == null){
            return null;
        }

        return Permiso.builder()
                .accion(dto.getAccion())
                .recurso(dto.getRecurso())
                .build();
    }

    public PermisoResponseDTO toResponseDTO(Permiso permiso){
        if (permiso == null){
            return null;
        }

        return PermisoResponseDTO.builder()
                .id(permiso.getId())
                .accion(permiso.getAccion())
                .recurso(permiso.getRecurso())
                .activo(permiso.getActivo())
                .build();
    }

    public void updateEntityFromDTO(PermisoRequestDTO dto, Permiso permiso){
        if (dto == null || permiso == null) {
            return;
        }

        permiso.setAccion(dto.getAccion());
        permiso.setRecurso(dto.getRecurso());
    }


}

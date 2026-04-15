package dgtic.core.siac.mapper;

import dgtic.core.siac.dto.rolPermiso.RolPermisoRequestDTO;
import dgtic.core.siac.dto.rolPermiso.RolPermisoResponseDTO;
import dgtic.core.siac.model.RolPermiso;
import org.springframework.stereotype.Component;

@Component
public class RolPermisoMapper {

    public RolPermiso toEntity(RolPermisoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return RolPermiso.builder()
                .activo(true)
                .build();
    }

    public RolPermisoResponseDTO toResponseDTO(RolPermiso entity) {
        if (entity == null) {
            return null;
        }

        var rol = entity.getRol();
        var permiso = entity.getPermiso();

        return RolPermisoResponseDTO.builder()
                .id(entity.getId())
                .rolId(rol != null ? rol.getId() : null)
                .nombreRol(rol != null ? rol.getNombre() : null)
                .permisoId(permiso != null ? permiso.getId() : null)
                .accionPermiso(permiso != null ? permiso.getAccion() : null)
                .activo(entity.getActivo())
                .build();
    }
}

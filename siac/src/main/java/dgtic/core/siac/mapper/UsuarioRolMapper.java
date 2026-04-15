package dgtic.core.siac.mapper;

import dgtic.core.siac.dto.usuarioRol.UsuarioRolRequestDTO;
import dgtic.core.siac.dto.usuarioRol.UsuarioRolResponseDTO;
import dgtic.core.siac.model.UsuarioRol;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRolMapper {

    public UsuarioRol toEntity(UsuarioRolRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        return UsuarioRol.builder()
                .activo(true)
                .build();
    }

    public UsuarioRolResponseDTO toResponseDTO(UsuarioRol entity) {
        if (entity == null) {
            return null;
        }

        var usuario = entity.getUsuario();
        var rol = entity.getRol();

        return UsuarioRolResponseDTO.builder()
                .id(entity.getId())
                .usuarioId(usuario != null ? usuario.getId() : null)
                .nombreUsuario(usuario != null
                        ? usuario.getNombre() + " " + usuario.getApPaterno()
                        : null)
                .rolId(rol != null ? rol.getId() : null)
                .nombreRol(rol != null ? rol.getNombre() : null)
                .activo(entity.getActivo())
                .build();
    }


}

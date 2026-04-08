package dgtic.core.siac.mapping;

import dgtic.core.siac.dto.UsuarioFormDTO;
import dgtic.core.siac.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioFormDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        return UsuarioFormDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apPaterno(usuario.getApPaterno())
                .apMaterno(usuario.getApMaterno())
                .correo(usuario.getCorreo())
                .build();
    }

    public Usuario toEntity(UsuarioFormDTO dto) {
        if (dto == null) return null;

        return Usuario.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .apPaterno(dto.getApPaterno())
                .apMaterno(dto.getApMaterno())
                .correo(dto.getCorreo())
                .build();
    }
}

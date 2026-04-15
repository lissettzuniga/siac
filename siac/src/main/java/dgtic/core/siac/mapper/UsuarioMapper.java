package dgtic.core.siac.mapper;

import dgtic.core.siac.dto.usuario.UsuarioRequestDTO;
import dgtic.core.siac.dto.usuario.UsuarioResponseDTO;
import dgtic.core.siac.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    //  RequestDTO -> Entity
    public Usuario toEntity(UsuarioRequestDTO request) {
        if (request == null) {
            return null;
        }

        return Usuario.builder()
                .nombre(request.getNombre())
                .apPaterno(request.getApPaterno())
                .apMaterno(request.getApMaterno())
                .correo(request.getCorreo())
                .build();
    }

    // Entity -> ResponseDTO
    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        if (usuario == null){
            return null;
        }

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apPaterno(usuario.getApPaterno())
                .apMaterno(usuario.getApMaterno())
                .correo(usuario.getCorreo())
                .fechaRegistro(usuario.getFechaRegistro())
                .fechaDesactivacion(usuario.getFechaDesactivacion())
                .activo(usuario.getActivo())
                .build();
    }

    // Update Entity
    public void updateEntityFromDTO(UsuarioRequestDTO request, Usuario usuario) {
        if (request == null || usuario == null){
            return;
        }

        usuario.setNombre(request.getNombre());
        usuario.setApPaterno(request.getApPaterno());
        usuario.setApMaterno(request.getApMaterno());
        usuario.setCorreo(request.getCorreo());
    }
}
package dgtic.core.siac.system.mapper;

import dgtic.core.siac.system.dto.bitacoraMovimiento.BitacoraMovimientoResponseDTO;
import dgtic.core.siac.system.model.BitacoraMovimiento;
import org.springframework.stereotype.Component;

@Component
public class BitacoraMovimientoMapper {

    public BitacoraMovimientoResponseDTO toResponseDTO(BitacoraMovimiento entity) {
        if (entity == null) {
            return null;
        }

        var usuario = entity.getUsuario();

        return BitacoraMovimientoResponseDTO.builder()
                .id(entity.getId())
                .usuarioId(usuario != null ? usuario.getId() : null)
                .usuarioNombre(usuario != null
                        ? usuario.getNombre() + " " + usuario.getApPaterno()
                        : null)
                .entidad(entity.getEntidad() != null ? entity.getEntidad().name() : null)
                .accion(entity.getAccion() != null ? entity.getAccion().name() : null)
                .descripcion(entity.getDescripcion())
                .fecha(entity.getFecha())
                .build();
    }
}
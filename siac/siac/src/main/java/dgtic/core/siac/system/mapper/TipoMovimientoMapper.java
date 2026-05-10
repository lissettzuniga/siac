package dgtic.core.siac.system.mapper;


import dgtic.core.siac.system.dto.tipoMovimiento.TipoMovimientoRequestDTO;
import dgtic.core.siac.system.dto.tipoMovimiento.TipoMovimientoResponseDTO;
import dgtic.core.siac.system.model.TipoMovimiento;
import org.springframework.stereotype.Component;

@Component
public class TipoMovimientoMapper {

    public TipoMovimiento toEntity(TipoMovimientoRequestDTO dto){
        if(dto == null){
            return null;
        }

        return TipoMovimiento.builder()
                .nombre(dto.getNombre())
                .clave(dto.getClave().trim().toUpperCase())
                .descripcion(dto.getDescripcion())
                .activo(true) // activo
                .build();
    }

    public TipoMovimientoResponseDTO toResponseDTO(TipoMovimiento entity){
        if(entity == null){
            return null;
        }

        return TipoMovimientoResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .clave(entity.getClave())
                .descripcion(entity.getDescripcion())
                .activo(entity.getActivo())
                .build();
    }

    public void updateEntityFromDTO(TipoMovimientoRequestDTO dto, TipoMovimiento entity){
        if (dto == null || entity == null) {
            return;
        }

        entity.setNombre(dto.getNombre());
        entity.setClave(dto.getClave().trim().toUpperCase());
        entity.setDescripcion(dto.getDescripcion());
    }
}

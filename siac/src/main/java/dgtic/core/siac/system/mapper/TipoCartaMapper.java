package dgtic.core.siac.system.mapper;

import dgtic.core.siac.system.dto.tipoCarta.TipoCartaRequestDTO;
import dgtic.core.siac.system.dto.tipoCarta.TipoCartaResponseDTO;
import dgtic.core.siac.system.model.TipoCarta;
import org.springframework.stereotype.Component;

@Component
public class TipoCartaMapper {

    public TipoCarta toEntity(TipoCartaRequestDTO dto){
        if(dto == null){
            return null;
        }

        return TipoCarta.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .activo(true) // activo
                .build();
    }

    public TipoCartaResponseDTO toResponseDTO(TipoCarta entity){
        if(entity == null){
            return null;
        }

        return TipoCartaResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .activo(entity.getActivo())
                .build();
    }

    public void updateEntityFromDTO(TipoCartaRequestDTO dto, TipoCarta entity){
        if (dto == null || entity == null) {
            return;
        }

        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
    }

}

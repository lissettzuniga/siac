package dgtic.core.siac.system.mapper;

import dgtic.core.siac.system.dto.productoCarta.ProductoCartaRequestDTO;
import dgtic.core.siac.system.dto.productoCarta.ProductoCartaResponseDTO;
import dgtic.core.siac.system.model.ProductoCarta;
import org.springframework.stereotype.Component;

@Component
public class ProductoCartaMapper {

    public ProductoCarta toEntity(ProductoCartaRequestDTO dto){
        if (dto == null) {
            return null;
        }

        return ProductoCarta.builder()
                .atributo(dto.getAtributo())
                .ataque(dto.getAtaque())
                .defensa(dto.getDefensa())
                .nivel(dto.getNivel())
                .build();

    }

    public ProductoCartaResponseDTO toResponseDTO(ProductoCarta productoCarta){
        if (productoCarta == null){
            return null;
        }

        var tipoCarta = productoCarta.getTipoCarta();

        return ProductoCartaResponseDTO.builder()
                .id(productoCarta.getId())
                .tipoCartaId(tipoCarta != null ? tipoCarta.getId() : null)
                .tipoCartaNombre(tipoCarta != null ? tipoCarta.getNombre() : null)
                .atributo(productoCarta.getAtributo())
                .ataque(productoCarta.getAtaque())
                .defensa(productoCarta.getDefensa())
                .nivel(productoCarta.getNivel())
                .activo(productoCarta.getActivo())
                .build();

    }

    public void updateEntityFromDTO(ProductoCartaRequestDTO dto, ProductoCarta productoCarta){
        if (dto == null || productoCarta == null) {
            return;
        }

        productoCarta.setAtributo(dto.getAtributo());
        productoCarta.setAtaque(dto.getAtaque());
        productoCarta.setDefensa(dto.getDefensa());
        productoCarta.setNivel(dto.getNivel());
    }


}

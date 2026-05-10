package dgtic.core.siac.system.service.tipoCarta;

import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.tipoCarta.TipoCartaRequestDTO;
import dgtic.core.siac.system.dto.tipoCarta.TipoCartaResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.DuplicateResourceException;
import dgtic.core.siac.system.exception.TipoCartaNotFoundException;
import dgtic.core.siac.system.mapper.TipoCartaMapper;
import dgtic.core.siac.system.model.TipoCarta;
import dgtic.core.siac.system.repository.TipoCartaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TipoCartaServiceImpl implements TipoCartaService {

    private final TipoCartaRepository tipoCartaRepository;
    private final TipoCartaMapper tipoCartaMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<TipoCartaResponseDTO> findAllActivos(Pageable pageable) {
        return tipoCartaRepository.findByActivoTrue(pageable)
                .map(tipoCartaMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoCartaResponseDTO> findAllInactivos(Pageable pageable) {
        return tipoCartaRepository.findByActivoFalse(pageable)
                .map(tipoCartaMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoCartaResponseDTO findById(Long id) {
        TipoCarta tipoCarta = tipoCartaRepository.findById(id)
                .orElseThrow(() -> new TipoCartaNotFoundException(id));

        return tipoCartaMapper.toResponseDTO(tipoCarta);
    }


    @Auditable(
            entidad = EntidadEnum.TIPO_CARTA,
            accion = AccionEnum.CREAR,
            descripcion = "Se creó un tipo de carta"
    )
    @Override
    public TipoCartaResponseDTO create(TipoCartaRequestDTO request) {

        if (tipoCartaRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new DuplicateResourceException(
                    "Ya existe un tipo de carta con el nombre: " + request.getNombre()
            );
        }

        TipoCarta tipoCarta = tipoCartaMapper.toEntity(request);
        tipoCarta.setActivo(true);

        TipoCarta guardado = tipoCartaRepository.save(tipoCarta);

        return tipoCartaMapper.toResponseDTO(guardado);
    }


    @Auditable(
            entidad = EntidadEnum.TIPO_CARTA,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "Se actualizó un tipo de carta"
    )
    @Override
    public TipoCartaResponseDTO update(Long id, TipoCartaRequestDTO request) {

        TipoCarta tipoCarta = tipoCartaRepository.findById(id)
                .orElseThrow(() -> new TipoCartaNotFoundException(id));

        if (tipoCartaRepository.existsByNombreIgnoreCaseAndIdNot(
                request.getNombre(), id)) {

            throw new DuplicateResourceException(
                    "Ya existe otro tipo de carta con el nombre: " + request.getNombre()
            );
        }

        tipoCartaMapper.updateEntityFromDTO(request, tipoCarta);

        TipoCarta actualizado = tipoCartaRepository.save(tipoCarta);

        return tipoCartaMapper.toResponseDTO(actualizado);
    }


    @Auditable(
            entidad = EntidadEnum.TIPO_CARTA,
            accion = AccionEnum.DESACTIVAR,
            descripcion = "Se desactivó un tipo de carta"
    )
    @Override
    public void deactivate(Long id) {

        TipoCarta tipoCarta = tipoCartaRepository.findById(id)
                .orElseThrow(() -> new TipoCartaNotFoundException(id));

        if (!tipoCarta.getActivo()) {
            return;
        }

        tipoCarta.setActivo(false);
        tipoCartaRepository.save(tipoCarta);
    }


    @Auditable(
            entidad = EntidadEnum.TIPO_CARTA,
            accion = AccionEnum.ACTIVAR,
            descripcion = "Se activó un tipo de carta"
    )
    @Override
    public void activate(Long id) {

        TipoCarta tipoCarta = tipoCartaRepository.findById(id)
                .orElseThrow(() -> new TipoCartaNotFoundException(id));

        if (tipoCarta.getActivo()) {
            return;
        }

        tipoCarta.setActivo(true);
        tipoCartaRepository.save(tipoCarta);
    }
}
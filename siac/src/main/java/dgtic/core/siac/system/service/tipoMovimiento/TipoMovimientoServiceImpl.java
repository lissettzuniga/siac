package dgtic.core.siac.system.service.tipoMovimiento;


import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.tipoMovimiento.TipoMovimientoRequestDTO;
import dgtic.core.siac.system.dto.tipoMovimiento.TipoMovimientoResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.DuplicateResourceException;
import dgtic.core.siac.system.exception.TipoMovimientoNotFoundException;
import dgtic.core.siac.system.mapper.TipoMovimientoMapper;
import dgtic.core.siac.system.model.TipoMovimiento;
import dgtic.core.siac.system.repository.TipoMovimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TipoMovimientoServiceImpl implements TipoMovimientoService {

    private final TipoMovimientoRepository tipoMovimientoRepository;
    private final TipoMovimientoMapper tipoMovimientoMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<TipoMovimientoResponseDTO> findAllActivos(Pageable pageable) {
        return tipoMovimientoRepository.findByActivoTrue(pageable)
                .map(tipoMovimientoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TipoMovimientoResponseDTO> findAllInactivos(Pageable pageable) {
        return tipoMovimientoRepository.findByActivoFalse(pageable)
                .map(tipoMovimientoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public TipoMovimientoResponseDTO findById(Long id) {
        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> new TipoMovimientoNotFoundException(id));

        return tipoMovimientoMapper.toResponseDTO(tipoMovimiento);
    }


    @Auditable(
            entidad = EntidadEnum.TIPO_MOVIMIENTO,
            accion = AccionEnum.CREAR,
            descripcion = "Se creó un tipo de movimiento"
    )
    @Override
    public TipoMovimientoResponseDTO create(TipoMovimientoRequestDTO request) {

        if (tipoMovimientoRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new DuplicateResourceException(
                    "Ya existe un tipo de movimiento con el nombre: " + request.getNombre()
            );
        }

        if (tipoMovimientoRepository.existsByClaveIgnoreCase(request.getClave())) {
            throw new DuplicateResourceException(
                    "Ya existe un tipo de movimiento con la clave: " + request.getClave()
            );
        }

        TipoMovimiento tipoMovimiento = tipoMovimientoMapper.toEntity(request);
        tipoMovimiento.setClave(request.getClave().trim().toUpperCase());
        tipoMovimiento.setActivo(true);

        TipoMovimiento guardado = tipoMovimientoRepository.save(tipoMovimiento);

        return tipoMovimientoMapper.toResponseDTO(guardado);
    }

    @Auditable(
            entidad = EntidadEnum.TIPO_MOVIMIENTO,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "Se actualizó un tipo de movimiento"
    )
    @Override
    public TipoMovimientoResponseDTO update(Long id, TipoMovimientoRequestDTO request) {

        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> new TipoMovimientoNotFoundException(id));

        if (tipoMovimientoRepository.existsByNombreIgnoreCaseAndIdNot(request.getNombre(), id)) {
            throw new DuplicateResourceException(
                    "Ya existe otro tipo de movimiento con el nombre: " + request.getNombre()
            );
        }

        if (tipoMovimientoRepository.existsByClaveIgnoreCaseAndIdNot(request.getClave(), id)) {
            throw new DuplicateResourceException(
                    "Ya existe otro tipo de movimiento con la clave: " + request.getClave()
            );
        }

        tipoMovimientoMapper.updateEntityFromDTO(request, tipoMovimiento);
        tipoMovimiento.setClave(request.getClave().trim().toUpperCase());

        TipoMovimiento actualizado = tipoMovimientoRepository.save(tipoMovimiento);

        return tipoMovimientoMapper.toResponseDTO(actualizado);
    }

    @Auditable(
            entidad = EntidadEnum.TIPO_MOVIMIENTO,
            accion = AccionEnum.DESACTIVAR,
            descripcion = "Se desactivó un tipo de movimiento"
    )
    @Override
    public void deactivate(Long id) {

        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> new TipoMovimientoNotFoundException(id));

        if (!tipoMovimiento.getActivo()) {
            return;
        }

        tipoMovimiento.setActivo(false);
        tipoMovimientoRepository.save(tipoMovimiento);
    }

    @Auditable(
            entidad = EntidadEnum.TIPO_MOVIMIENTO,
            accion = AccionEnum.ACTIVAR,
            descripcion = "Se activó un tipo de movimiento"
    )
    @Override
    public void activate(Long id) {

        TipoMovimiento tipoMovimiento = tipoMovimientoRepository.findById(id)
                .orElseThrow(() -> new TipoMovimientoNotFoundException(id));

        if (tipoMovimiento.getActivo()) {
            return;
        }

        tipoMovimiento.setActivo(true);
        tipoMovimientoRepository.save(tipoMovimiento);
    }
}
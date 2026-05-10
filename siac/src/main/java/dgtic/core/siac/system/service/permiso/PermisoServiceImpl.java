package dgtic.core.siac.system.service.permiso;

import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.permiso.PermisoRequestDTO;
import dgtic.core.siac.system.dto.permiso.PermisoResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.DuplicateResourceException;
import dgtic.core.siac.system.exception.PermisoNotFoundException;
import dgtic.core.siac.system.mapper.PermisoMapper;
import dgtic.core.siac.system.model.Permiso;
import dgtic.core.siac.system.repository.PermisoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;
    private final PermisoMapper permisoMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<PermisoResponseDTO> findAllActivos(Pageable pageable) {
        return permisoRepository.findByActivoTrue(pageable)
                .map(permisoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PermisoResponseDTO> findAllInactivos(Pageable pageable) {
        return permisoRepository.findByActivoFalse(pageable)
                .map(permisoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public PermisoResponseDTO findById(Long id) {
        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new PermisoNotFoundException(id));

        return permisoMapper.toResponseDTO(permiso);
    }


    @Auditable(
            entidad = EntidadEnum.PERMISO,
            accion = AccionEnum.CREAR,
            descripcion = "Se creó un permiso"
    )
    @Override
    public PermisoResponseDTO create(PermisoRequestDTO request) {

        if (permisoRepository.existsByAccionIgnoreCaseAndRecursoIgnoreCase(
                request.getAccion(),
                request.getRecurso()
        )) {
            throw new DuplicateResourceException(
                    "Ya existe un permiso con la acción "
                            + request.getAccion()
                            + " sobre el recurso "
                            + request.getRecurso()
            );
        }

        Permiso permiso = permisoMapper.toEntity(request);
        permiso.setActivo(true);

        Permiso guardado = permisoRepository.save(permiso);

        return permisoMapper.toResponseDTO(guardado);
    }

    @Auditable(
            entidad = EntidadEnum.PERMISO,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "Se actualizó un permiso"
    )
    @Override
    public PermisoResponseDTO update(Long id, PermisoRequestDTO request) {

        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new PermisoNotFoundException(id));

        if (permisoRepository.existsByAccionIgnoreCaseAndRecursoIgnoreCaseAndIdNot(
                request.getAccion(),
                request.getRecurso(),
                id
        )) {

            throw new DuplicateResourceException("Ya existe otro permiso con la acción "
                    + request.getAccion()
                    + " sobre el recurso "
                    + request.getRecurso()
            );
        }

        permisoMapper.updateEntityFromDTO(request, permiso);
        Permiso actualizado = permisoRepository.save(permiso);

        return permisoMapper.toResponseDTO(actualizado);
    }

    @Auditable(
            entidad = EntidadEnum.PERMISO,
            accion = AccionEnum.DESACTIVAR,
            descripcion = "Se desactivó un permiso"
    )
    @Override
    public void deactivate(Long id) {

        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new PermisoNotFoundException(id));

        if (!permiso.getActivo()) {
            return;
        }

        permiso.setActivo(false);
        permisoRepository.save(permiso);
    }


    @Auditable(
            entidad = EntidadEnum.PERMISO,
            accion = AccionEnum.ACTIVAR,
            descripcion = "Se activó un permiso"
    )
    @Override
    public void activate(Long id) {

        Permiso permiso = permisoRepository.findById(id)
                .orElseThrow(() -> new PermisoNotFoundException(id));

        if (permiso.getActivo()) {
            return;
        }

        permiso.setActivo(true);
        permisoRepository.save(permiso);
    }

}

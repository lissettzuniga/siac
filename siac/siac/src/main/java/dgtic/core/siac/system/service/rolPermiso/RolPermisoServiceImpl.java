package dgtic.core.siac.system.service.rolPermiso;

import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.rolPermiso.RolPermisoRequestDTO;
import dgtic.core.siac.system.dto.rolPermiso.RolPermisoResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.DuplicateResourceException;
import dgtic.core.siac.system.exception.PermisoNotFoundException;
import dgtic.core.siac.system.exception.RolNotFoundException;
import dgtic.core.siac.system.exception.RolPermisoNotFoundException;
import dgtic.core.siac.system.mapper.RolPermisoMapper;
import dgtic.core.siac.system.model.Permiso;
import dgtic.core.siac.system.model.Rol;
import dgtic.core.siac.system.model.RolPermiso;
import dgtic.core.siac.system.repository.PermisoRepository;
import dgtic.core.siac.system.repository.RolPermisoRepository;
import dgtic.core.siac.system.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RolPermisoServiceImpl implements RolPermisoService {

    private final RolPermisoRepository rolPermisoRepository;
    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;
    private final RolPermisoMapper rolPermisoMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<RolPermisoResponseDTO> findAllActivos(Pageable pageable) {
        return rolPermisoRepository.findByActivoTrue(pageable)
                .map(rolPermisoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RolPermisoResponseDTO> findAllInactivos(Pageable pageable) {
        return rolPermisoRepository.findByActivoFalse(pageable)
                .map(rolPermisoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RolPermisoResponseDTO> findByRol(Long rolId, Pageable pageable) {
        return rolPermisoRepository.findByRolIdAndActivoTrue(rolId, pageable)
                .map(rolPermisoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RolPermisoResponseDTO> findByPermiso(Long permisoId, Pageable pageable) {
        return rolPermisoRepository.findByPermisoIdAndActivoTrue(permisoId, pageable)
                .map(rolPermisoMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public RolPermisoResponseDTO findById(Long id) {
        RolPermiso rolPermiso = rolPermisoRepository.findById(id)
                .orElseThrow(() -> new RolPermisoNotFoundException(id));

        return rolPermisoMapper.toResponseDTO(rolPermiso);
    }


    @Auditable(
            entidad = EntidadEnum.ROL_PERMISO,
            accion = AccionEnum.CREAR,
            descripcion = "Se asignó un permiso a un rol"
    )
    @Override
    public RolPermisoResponseDTO create(RolPermisoRequestDTO request) {

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new RolNotFoundException(request.getRolId()));

        Permiso permiso = permisoRepository.findById(request.getPermisoId())
                .orElseThrow(() -> new PermisoNotFoundException(request.getPermisoId()));

        if (rolPermisoRepository.existsByRolIdAndPermisoId(
                request.getRolId(), request.getPermisoId())) {
            throw new DuplicateResourceException(
                    "El rol ya tiene asignado este permiso"
            );
        }

        RolPermiso rolPermiso = rolPermisoMapper.toEntity(request);
        rolPermiso.setRol(rol);
        rolPermiso.setPermiso(permiso);
        rolPermiso.setActivo(true);

        RolPermiso guardado = rolPermisoRepository.save(rolPermiso);

        return rolPermisoMapper.toResponseDTO(guardado);
    }


    @Auditable(
            entidad = EntidadEnum.ROL_PERMISO,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "Se actualizó la relación rol-permiso"
    )
    @Override
    public RolPermisoResponseDTO update(Long id, RolPermisoRequestDTO request) {

        RolPermiso rolPermiso = rolPermisoRepository.findById(id)
                .orElseThrow(() -> new RolPermisoNotFoundException(id));

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new RolNotFoundException(request.getRolId()));

        Permiso permiso = permisoRepository.findById(request.getPermisoId())
                .orElseThrow(() -> new PermisoNotFoundException(request.getPermisoId()));

        if (rolPermisoRepository.existsByRolIdAndPermisoIdAndIdNot(
                request.getRolId(), request.getPermisoId(), id)) {
            throw new DuplicateResourceException(
                    "Ya existe otra relación con el mismo rol y permiso"
            );
        }

        rolPermiso.setRol(rol);
        rolPermiso.setPermiso(permiso);

        RolPermiso actualizado = rolPermisoRepository.save(rolPermiso);

        return rolPermisoMapper.toResponseDTO(actualizado);
    }


    @Auditable(
            entidad = EntidadEnum.ROL_PERMISO,
            accion = AccionEnum.DESACTIVAR,
            descripcion = "Se desactivó la relación rol-permiso"
    )
    @Override
    public void deactivate(Long id) {

        RolPermiso rolPermiso = rolPermisoRepository.findById(id)
                .orElseThrow(() -> new RolPermisoNotFoundException(id));

        if (!rolPermiso.getActivo()) {
            return;
        }

        rolPermiso.setActivo(false);
        rolPermisoRepository.save(rolPermiso);
    }


    @Auditable(
            entidad = EntidadEnum.ROL_PERMISO,
            accion = AccionEnum.ACTIVAR,
            descripcion = "Se activó la relación rol-permiso"
    )
    @Override
    public void activate(Long id) {

        RolPermiso rolPermiso = rolPermisoRepository.findById(id)
                .orElseThrow(() -> new RolPermisoNotFoundException(id));

        if (rolPermiso.getActivo()) {
            return;
        }

        rolPermiso.setActivo(true);
        rolPermisoRepository.save(rolPermiso);
    }
}

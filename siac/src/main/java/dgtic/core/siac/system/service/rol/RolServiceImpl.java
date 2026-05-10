package dgtic.core.siac.system.service.rol;

import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.rol.RolRequestDTO;
import dgtic.core.siac.system.dto.rol.RolResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.DuplicateResourceException;
import dgtic.core.siac.system.exception.RolNotFoundException;
import dgtic.core.siac.system.mapper.RolMapper;
import dgtic.core.siac.system.model.Rol;
import dgtic.core.siac.system.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RolServiceImpl implements RolService{

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<RolResponseDTO> findAllActivos(Pageable pageable) {
        return rolRepository.findByActivoTrue(pageable)
                .map(rolMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RolResponseDTO> findAllInactivos(Pageable pageable) {
        return rolRepository.findByActivoFalse(pageable)
                .map(rolMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public RolResponseDTO findById(Long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RolNotFoundException(id));

        return rolMapper.toResponseDTO(rol);
    }


    @Auditable(
            entidad = EntidadEnum.ROL,
            accion = AccionEnum.CREAR,
            descripcion = "Se creó un rol"
    )
    @Override
    public RolResponseDTO create(RolRequestDTO request) {

        if (rolRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new DuplicateResourceException(
                    "Ya existe un rol con el nombre: " + request.getNombre()
            );
        }

        Rol rol = rolMapper.toEntity(request);
        rol.setActivo(true);

        Rol guardado = rolRepository.save(rol);

        return rolMapper.toResponseDTO(guardado);
    }


    @Auditable(
            entidad = EntidadEnum.ROL,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "Se actualizó un rol"
    )
    @Override
    public RolResponseDTO update(Long id, RolRequestDTO request) {

        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RolNotFoundException(id));

        if (rolRepository.existsByNombreIgnoreCaseAndIdNot(request.getNombre(), id)) {
            throw new DuplicateResourceException(
                    "Ya existe otro rol con el nombre: " + request.getNombre()
            );
        }

        rolMapper.updateEntityFromDTO(request, rol);

        Rol actualizado = rolRepository.save(rol);

        return rolMapper.toResponseDTO(actualizado);
    }

    @Auditable(
            entidad = EntidadEnum.ROL,
            accion = AccionEnum.DESACTIVAR,
            descripcion = "Se desactivó un rol"
    )
    @Override
    public void deactivate(Long id) {

        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RolNotFoundException(id));

        if (!rol.getActivo()) {
            return;
        }

        rol.setActivo(false);
        rolRepository.save(rol);
    }


    @Auditable(
            entidad = EntidadEnum.ROL,
            accion = AccionEnum.ACTIVAR,
            descripcion = "Se activó un rol"
    )
    @Override
    public void activate(Long id) {

        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RolNotFoundException(id));

        if (rol.getActivo()) {
            return;
        }

        rol.setActivo(true);
        rolRepository.save(rol);
    }
}

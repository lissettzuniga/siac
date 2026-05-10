package dgtic.core.siac.system.service.usuarioRol;

import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.usuarioRol.UsuarioRolRequestDTO;
import dgtic.core.siac.system.dto.usuarioRol.UsuarioRolResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.DuplicateResourceException;
import dgtic.core.siac.system.exception.RolNotFoundException;
import dgtic.core.siac.system.exception.UsuarioNotFoundException;
import dgtic.core.siac.system.exception.UsuarioRolNotFoundException;
import dgtic.core.siac.system.mapper.UsuarioRolMapper;
import dgtic.core.siac.system.model.Rol;
import dgtic.core.siac.system.model.Usuario;
import dgtic.core.siac.system.model.UsuarioRol;
import dgtic.core.siac.system.repository.RolRepository;
import dgtic.core.siac.system.repository.UsuarioRepository;
import dgtic.core.siac.system.repository.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioRolServiceImpl implements UsuarioRolService {

    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolMapper usuarioRolMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioRolResponseDTO> findAllActivos(Pageable pageable) {
        return usuarioRolRepository.findByActivoTrue(pageable)
                .map(usuarioRolMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioRolResponseDTO> findAllInactivos(Pageable pageable) {
        return usuarioRolRepository.findByActivoFalse(pageable)
                .map(usuarioRolMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioRolResponseDTO> findByUsuario(Long usuarioId, Pageable pageable) {
        return usuarioRolRepository.findByUsuarioIdAndActivoTrue(usuarioId, pageable)
                .map(usuarioRolMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioRolResponseDTO> findByRol(Long rolId, Pageable pageable) {
        return usuarioRolRepository.findByRolIdAndActivoTrue(rolId, pageable)
                .map(usuarioRolMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioRolResponseDTO findById(Long id) {
        UsuarioRol usuarioRol = usuarioRolRepository.findById(id)
                .orElseThrow(() -> new UsuarioRolNotFoundException(id));

        return usuarioRolMapper.toResponseDTO(usuarioRol);
    }

    @Auditable(
            entidad = EntidadEnum.USUARIO_ROL,
            accion = AccionEnum.CREAR,
            descripcion = "Se asignó un rol a un usuario"
    )
    @Override
    public UsuarioRolResponseDTO create(UsuarioRolRequestDTO request) {

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new UsuarioNotFoundException(request.getUsuarioId()));

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new RolNotFoundException(request.getRolId()));

        if (usuarioRolRepository.existsByUsuarioIdAndRolId(
                request.getUsuarioId(), request.getRolId())) {

            throw new DuplicateResourceException(
                    "El usuario ya tiene asignado este rol"
            );
        }

        UsuarioRol usuarioRol = usuarioRolMapper.toEntity(request);
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
        usuarioRol.setActivo(true);

        UsuarioRol guardado = usuarioRolRepository.save(usuarioRol);

        return usuarioRolMapper.toResponseDTO(guardado);
    }

    @Auditable(
            entidad = EntidadEnum.USUARIO_ROL,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "Se actualizó la relación usuario-rol"
    )
    @Override
    public UsuarioRolResponseDTO update(Long id, UsuarioRolRequestDTO request) {

        UsuarioRol usuarioRol = usuarioRolRepository.findById(id)
                .orElseThrow(() -> new UsuarioRolNotFoundException(id));

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new UsuarioNotFoundException(request.getUsuarioId()));

        Rol rol = rolRepository.findById(request.getRolId())
                .orElseThrow(() -> new RolNotFoundException(request.getRolId()));

        if (usuarioRolRepository.existsByUsuarioIdAndRolIdAndIdNot(
                request.getUsuarioId(), request.getRolId(), id)) {

            throw new DuplicateResourceException(
                    "Ya existe otra relación con ese usuario y rol"
            );
        }

        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);

        UsuarioRol actualizado = usuarioRolRepository.save(usuarioRol);

        return usuarioRolMapper.toResponseDTO(actualizado);
    }

    @Auditable(
            entidad = EntidadEnum.USUARIO_ROL,
            accion = AccionEnum.DESACTIVAR,
            descripcion = "Se desactivó la relación usuario-rol"
    )
    @Override
    public void deactivate(Long id) {

        UsuarioRol usuarioRol = usuarioRolRepository.findById(id)
                .orElseThrow(() -> new UsuarioRolNotFoundException(id));

        if (!usuarioRol.getActivo()) {
            return;
        }

        usuarioRol.setActivo(false);
        usuarioRolRepository.save(usuarioRol);
    }

    @Auditable(
            entidad = EntidadEnum.USUARIO_ROL,
            accion = AccionEnum.ACTIVAR,
            descripcion = "Se activó la relación usuario-rol"
    )
    @Override
    public void activate(Long id) {

        UsuarioRol usuarioRol = usuarioRolRepository.findById(id)
                .orElseThrow(() -> new UsuarioRolNotFoundException(id));

        if (usuarioRol.getActivo()) {
            return;
        }

        usuarioRol.setActivo(true);
        usuarioRolRepository.save(usuarioRol);
    }
}
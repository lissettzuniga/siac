package dgtic.core.siac.system.service.usuario;

import dgtic.core.siac.security.SecurityUtils;
import dgtic.core.siac.system.audit.annotation.Auditable;
import dgtic.core.siac.system.dto.usuario.ChangePasswordRequestDTO;
import dgtic.core.siac.system.dto.usuario.UsuarioRequestDTO;
import dgtic.core.siac.system.dto.usuario.UsuarioResponseDTO;
import dgtic.core.siac.system.enums.AccionEnum;
import dgtic.core.siac.system.enums.EntidadEnum;
import dgtic.core.siac.system.exception.BusinessException;
import dgtic.core.siac.system.exception.DuplicateResourceException;
import dgtic.core.siac.system.exception.UsuarioNotFoundException;
import dgtic.core.siac.system.mapper.UsuarioMapper;
import dgtic.core.siac.system.model.Usuario;
import dgtic.core.siac.system.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> findAllActivos(Pageable pageable) {
        return usuarioRepository.findByActivoTrue(pageable)
                .map(usuarioMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponseDTO> findAllInactivos(Pageable pageable) {
        return usuarioRepository.findByActivoFalse(pageable)
                .map(usuarioMapper::toResponseDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        return usuarioMapper.toResponseDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO findByCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreoIgnoreCase(correo)
                .orElseThrow(() -> new UsuarioNotFoundException(correo));

        return usuarioMapper.toResponseDTO(usuario);
    }


    @Auditable(
            entidad = EntidadEnum.USUARIO,
            accion = AccionEnum.CREAR,
            descripcion = "Se creó un usuario"
    )
    @Override
    public UsuarioResponseDTO create(UsuarioRequestDTO request) {

        if (usuarioRepository.existsByCorreoIgnoreCase(request.getCorreo())) {
            throw new DuplicateResourceException(
                    "Ya existe un usuario con el correo: " + request.getCorreo()
            );
        }

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setCorreo(request.getCorreo().trim().toLowerCase());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuario.setActivo(true);

        Usuario guardado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponseDTO(guardado);
    }


    @Auditable(
            entidad = EntidadEnum.USUARIO,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "Se actualizó un usuario"
    )
    @Override
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO request) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        if (usuarioRepository.existsByCorreoIgnoreCaseAndIdNot(request.getCorreo(), id)) {
            throw new DuplicateResourceException(
                    "Ya existe otro usuario con el correo: " + request.getCorreo()
            );
        }

        usuarioMapper.updateEntityFromDTO(request, usuario);
        usuario.setCorreo(request.getCorreo().trim().toLowerCase());

        Usuario actualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponseDTO(actualizado);
    }

    @Auditable(
            entidad = EntidadEnum.USUARIO,
            accion = AccionEnum.DESACTIVAR,
            descripcion = "Se desactivó a un usuario"
    )
    @Override
    public void deactivate(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        if (!usuario.getActivo()) {
            return;
        }

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    @Auditable(
            entidad = EntidadEnum.USUARIO,
            accion = AccionEnum.ACTIVAR,
            descripcion = "Se activó a un usuario"
    )
    @Override
    public void activate(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        if (usuario.getActivo()) {
            return;
        }

        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }


    @Auditable(
            entidad = EntidadEnum.USUARIO,
            accion = AccionEnum.ACTUALIZAR,
            descripcion = "El usuario cambió su contraseña"
    )
    @Override
    @Transactional
    public void changePassword(ChangePasswordRequestDTO request) {

        Usuario usuario = securityUtils.getUsuarioAutenticado();

        if (!passwordEncoder.matches(request.getContrasenaActual(), usuario.getContrasena())) {
            throw new BusinessException("La contraseña actual es incorrecta");
        }

        if (!request.getNuevaContrasena().equals(request.getConfirmarContrasena())) {
            throw new BusinessException("La nueva contraseña y la confirmación no coinciden");
        }

        if (passwordEncoder.matches(request.getNuevaContrasena(), usuario.getContrasena())) {
            throw new BusinessException("La nueva contraseña no puede ser igual a la actual");
        }

        usuario.setContrasena(passwordEncoder.encode(request.getNuevaContrasena()));

        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponseDTO getUsuarioAutenticado() {
        Usuario usuario = securityUtils.getUsuarioAutenticado();
        return usuarioMapper.toResponseDTO(usuario);
    }
}
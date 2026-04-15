package dgtic.core.siac.service.usuario;

import dgtic.core.siac.dto.usuario.UsuarioRequestDTO;
import dgtic.core.siac.dto.usuario.UsuarioResponseDTO;
import dgtic.core.siac.exception.ResourceNotFoundException;
import dgtic.core.siac.mapper.UsuarioMapper;
import dgtic.core.siac.model.Usuario;
import dgtic.core.siac.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<UsuarioResponseDTO> findAllActivos() {
//        return usuarioRepository.findByActivoTrue()
//                .stream()
//                .map(UsuarioMapper::toResponseDTO)
//                .toList();
        return null;
    }

    @Override
    public List<UsuarioResponseDTO> findAllInactivos() {
//        return usuarioRepository.findByActivoFalse()
//                .stream()
//                .map(UsuarioMapper::toResponseDTO)
//                .toList();
        return null;
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
//        Usuario usuario = usuarioRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
//
//        return UsuarioMapper.toResponseDTO(usuario);
        return null;
    }

    @Override
    public UsuarioResponseDTO create(UsuarioRequestDTO request) {
//        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
//            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
//        }
//
//        Usuario usuario = UsuarioMapper.toEntity(request);
//        Usuario usuarioGuardado = usuarioRepository.save(usuario);
//
//        return UsuarioMapper.toResponseDTO(usuarioGuardado);
        return null;
    }

    @Override
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO request) {
//        Usuario usuario = usuarioRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
//
//        if (usuarioRepository.existsByCorreoAndIdNot(request.getCorreo(), id)) {
//            throw new IllegalArgumentException("Ya existe otro usuario con ese correo");
//        }
//
//        UsuarioMapper.updateEntityFromDTO(request, usuario);
//
//        Usuario usuarioActualizado = usuarioRepository.save(usuario);
//        return UsuarioMapper.toResponseDTO(usuarioActualizado);
        return null;
    }

    @Override
    public void activar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        usuario.setActivo(true);
        usuario.setFechaDesactivacion(null);

        usuarioRepository.save(usuario);
    }

    @Override
    public void desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));

        usuario.setActivo(false);
        usuario.setFechaDesactivacion(LocalDateTime.now());

        usuarioRepository.save(usuario);
    }
}
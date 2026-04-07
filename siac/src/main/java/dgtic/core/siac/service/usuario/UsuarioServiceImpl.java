package dgtic.core.siac.service.usuario;

import dgtic.core.siac.dto.UsuarioFormDTO;
import dgtic.core.siac.mapping.UsuarioMapper;
import dgtic.core.siac.model.Rol;
import dgtic.core.siac.model.Usuario;
import dgtic.core.siac.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService{


    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllActivos() {
        return usuarioRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllInactivos() {
        return usuarioRepository.findByActivoFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioFormDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public Usuario save(UsuarioFormDTO usuarioFormDTO) {
        if (usuarioRepository.existsByCorreo(usuarioFormDTO.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }
        Usuario usuario = usuarioMapper.toEntity(usuarioFormDTO);
        usuario.setActivo(true);
        usuario.setFechaDesactivacion(null);

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Long id, UsuarioFormDTO usuarioFormDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuarioExistente.getCorreo().equals(usuarioFormDTO.getCorreo())
                && usuarioRepository.existsByCorreo(usuarioFormDTO.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }

        usuarioExistente.setNombre(usuarioFormDTO.getNombre());
        usuarioExistente.setApPaterno(usuarioFormDTO.getApPaterno());
        usuarioExistente.setApMaterno(usuarioFormDTO.getApMaterno());
        usuarioExistente.setCorreo(usuarioFormDTO.getCorreo());

        return usuarioRepository.save(usuarioExistente);
    }

    @Override
    public void activar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }

    @Override
    public void desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(false);
        usuario.setFechaDesactivacion(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }
}

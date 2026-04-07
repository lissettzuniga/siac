package dgtic.core.siac.service.usuarioRol;

import dgtic.core.siac.model.UsuarioRol;
import dgtic.core.siac.repository.UsuarioRolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioRolServiceImpl implements UsuarioRolService{

    private final UsuarioRolRepository usuarioRolRepository;

    public UsuarioRolServiceImpl(UsuarioRolRepository usuarioRolRepository) {
        this.usuarioRolRepository = usuarioRolRepository;
    }

    @Override
    public List<UsuarioRol> findAllActivos() {
        return usuarioRolRepository.findByActivoTrue();
    }

    @Override
    public List<UsuarioRol> findAllInactivos() {
        return usuarioRolRepository.findByActivoFalse();
    }

    @Override
    public Optional<UsuarioRol> findById(Long id) {
        return usuarioRolRepository.findById(id);
    }

    @Override
    public UsuarioRol save(UsuarioRol usuarioRol) {
        return usuarioRolRepository.save(usuarioRol);
    }

    @Override
    public void activar(Long id) {
        UsuarioRol usuarioRol = usuarioRolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UsuarioRol no encontrado"));

        usuarioRol.setActivo(true);
        usuarioRolRepository.save(usuarioRol);
    }

    @Override
    public void desactivar(Long id) {
        UsuarioRol usuarioRol = usuarioRolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UsuarioRol no encontrado"));

        usuarioRol.setActivo(false);
        usuarioRolRepository.save(usuarioRol);
    }
}

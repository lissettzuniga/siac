package dgtic.core.siac.security;

import dgtic.core.siac.system.exception.UsuarioNotFoundException;
import dgtic.core.siac.system.model.Usuario;
import dgtic.core.siac.system.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UsuarioRepository usuarioRepository;

    public Usuario getUsuarioAutenticado() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return usuarioRepository.findByCorreoIgnoreCaseAndActivoTrue(username)
                .orElseThrow(() -> new UsuarioNotFoundException(username));
    }
}
package dgtic.core.siac.security.service;

import dgtic.core.siac.security.model.UserDetailsImpl;
import dgtic.core.siac.system.model.Usuario;
import dgtic.core.siac.system.model.UsuarioRol;
import dgtic.core.siac.system.repository.UsuarioRepository;
import dgtic.core.siac.system.repository.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioRolRepository usuarioRolRepository;

    @Override
    public UserDetails loadUserByUsername(String correo)
            throws UsernameNotFoundException {

        log.info("Security - loadUserByUsername: {}", correo);

        Usuario usuario = usuarioRepository
                .findByCorreoIgnoreCaseAndActivoTrue(correo)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario no encontrado o inactivo"
                        ));

        List<UsuarioRol> usuarioRoles =
                usuarioRolRepository.findRolesActivosByUsuarioId(usuario.getId());

        List<GrantedAuthority> authorities = usuarioRoles.stream()
                .map(usuarioRol ->
                        new SimpleGrantedAuthority(
                                usuarioRol.getRol().getNombre()
                        ))
                .map(authority -> (GrantedAuthority) authority)
                .toList();

        return UserDetailsImpl.build(usuario, authorities);
    }
}
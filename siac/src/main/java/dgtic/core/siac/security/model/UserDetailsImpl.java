package dgtic.core.siac.security.model;

import dgtic.core.siac.system.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(
            Usuario usuario,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.usuario = usuario;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(
            Usuario usuario,
            Collection<? extends GrantedAuthority> authorities
    ) {
        return new UserDetailsImpl(usuario, authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        return usuario.getCorreo();
    }

    public Long getId() {
        return usuario.getId();
    }

    public String getName() {
        return usuario.getNombre() + " " +
                usuario.getApPaterno() + " " +
                usuario.getApMaterno();
    }

    public String getEmail() {
        return usuario.getCorreo();
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(usuario.getActivo());
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE.equals(usuario.getActivo());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
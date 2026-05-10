package dgtic.core.siac.security.controller;

import dgtic.core.siac.security.jwt.JwtTokenProvider;
import dgtic.core.siac.security.model.UserDetailsImpl;
import dgtic.core.siac.security.service.UserDetailsServiceImpl;
import dgtic.core.siac.system.dto.auth.LoginRequestDTO;
import dgtic.core.siac.system.dto.auth.LoginResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import dgtic.core.siac.system.dto.auth.RefreshTokenRequestDTO;
import io.jsonwebtoken.Claims;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {

        Authentication authentication = authenticate(
                request.getCorreo(),
                request.getContrasena()
        );

        UserDetailsImpl usuario = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtTokenProvider.generateJwtToken(usuario);
        String refreshToken = jwtTokenProvider.generateRefreshToken(usuario);

        String rol = usuario.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();

        LoginResponseDTO response = new LoginResponseDTO(
                accessToken,
                refreshToken,
                "Bearer",
                rol
        );

        log.info("Login exitoso para usuario: {}", usuario.getUsername());

        return ResponseEntity.ok(response);
    }


    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshToken(
            @Valid @RequestBody RefreshTokenRequestDTO request
    ) {

        String refreshToken = request.getRefreshToken();

        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Refresh token inválido o expirado");
        }

        Claims claims = jwtTokenProvider.getClaims(refreshToken);

        String correo = claims.getIssuer();

        UserDetailsImpl usuario = (UserDetailsImpl)
                userDetailsService.loadUserByUsername(correo);

        String newAccessToken = jwtTokenProvider.generateJwtToken(usuario);

        String rol = usuario.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();

        LoginResponseDTO response = new LoginResponseDTO(
                newAccessToken,
                refreshToken,
                "Bearer",
                rol
        );

        return ResponseEntity.ok(response);
    }

    private Authentication authenticate(String correo, String contrasena) {
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(correo, contrasena)
            );
        } catch (DisabledException ex) {
            throw new RuntimeException("El usuario está deshabilitado", ex);
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Correo o contraseña incorrectos", ex);
        }
    }
}
package dgtic.core.siac.security;

import dgtic.core.siac.security.jwt.JwtAuthenticationFilter;
import dgtic.core.siac.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // Auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/refresh").permitAll()

                        // Swagger
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // Dashboard
                        .requestMatchers("/api/dashboard/data").authenticated()

                        // Bitácora
                        .requestMatchers(HttpMethod.GET, "/api/bitacora-movimientos/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_AUDITOR")

                        // Usuarios
                        .requestMatchers(HttpMethod.PATCH, "/api/usuarios/change-password")
                        .authenticated()

                        .requestMatchers("/api/usuarios/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers("/api/roles/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers("/api/usuario-roles/**")
                        .hasAuthority("ROLE_ADMIN")


                        // Permisos
                        .requestMatchers(HttpMethod.GET, "/api/permisos/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_AUDITOR")

                        .requestMatchers("/api/permisos/**")
                        .hasAuthority("ROLE_ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/rol-permisos/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_AUDITOR")

                        .requestMatchers("/api/rol-permisos/**")
                        .hasAuthority("ROLE_ADMIN")

                        // Productos
                        .requestMatchers(HttpMethod.GET, "/api/productos/**")
                        .permitAll()

                        .requestMatchers("/api/productos/**")
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_SUPERVISOR",
                                "ROLE_EMPLEADO"
                        )

                        // Productos carta
                        .requestMatchers(HttpMethod.GET, "/api/productos-carta/**")
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_SUPERVISOR",
                                "ROLE_EMPLEADO",
                                "ROLE_CLIENTE",
                                "ROLE_AUDITOR"
                        )

                        .requestMatchers("/api/productos-carta/**")
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_SUPERVISOR",
                                "ROLE_EMPLEADO"
                        )

                        // Categorías
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**")
                        .permitAll()

                        .requestMatchers("/api/categorias/**")
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_SUPERVISOR",
                                "ROLE_EMPLEADO"
                        )

                        // Tipos carta
                        .requestMatchers(HttpMethod.GET, "/api/tipos-carta/**")
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_SUPERVISOR",
                                "ROLE_EMPLEADO",
                                "ROLE_CLIENTE",
                                "ROLE_AUDITOR"
                        )

                        .requestMatchers("/api/tipos-carta/**")
                        .hasAuthority("ROLE_ADMIN")

                        // Tipos movimiento
                        .requestMatchers(HttpMethod.GET, "/api/tipos-movimiento/**")
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_SUPERVISOR",
                                "ROLE_EMPLEADO",
                                "ROLE_AUDITOR"
                        )

                        .requestMatchers("/api/tipos-movimiento/**")
                        .hasAuthority("ROLE_ADMIN")

                        // Movimientos inventario
                        .requestMatchers(HttpMethod.GET, "/api/movimientos-inventario/**")
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_SUPERVISOR",
                                "ROLE_EMPLEADO",
                                "ROLE_AUDITOR"
                        )

                        .requestMatchers("/api/movimientos-inventario/**")
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_SUPERVISOR",
                                "ROLE_EMPLEADO"
                        )

                        // Imágenes
                        .requestMatchers(HttpMethod.GET, "/api/imagenes-producto/**")
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_SUPERVISOR",
                                "ROLE_EMPLEADO",
                                "ROLE_CLIENTE",
                                "ROLE_AUDITOR"
                        )

                        .requestMatchers("/api/imagenes-producto/**")
                        .hasAnyAuthority(
                                "ROLE_ADMIN",
                                "ROLE_SUPERVISOR",
                                "ROLE_EMPLEADO"
                        )

                        .requestMatchers(HttpMethod.POST, "/api/busqueda-imagen")
                        .permitAll()

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        configuration.setAllowedHeaders(
                List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
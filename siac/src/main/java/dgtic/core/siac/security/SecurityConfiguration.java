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

                        // Bitácora: solo lectura para ADMIN y AUDITOR
                        .requestMatchers(HttpMethod.GET, "/api/bitacora-movimientos/**")
                        .hasAnyRole("ADMIN", "AUDITOR")

                        // Usuarios y seguridad: solo ADMIN
                        .requestMatchers("/api/usuarios/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/roles/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/api/usuario-roles/**")
                        .hasRole("ADMIN")

                        // Permisos y rol-permisos: AUDITOR solo lectura, ADMIN todo
                        .requestMatchers(HttpMethod.GET, "/api/permisos/**")
                        .hasAnyRole("ADMIN", "AUDITOR")

                        .requestMatchers("/api/permisos/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/api/rol-permisos/**")
                        .hasAnyRole("ADMIN", "AUDITOR")

                        .requestMatchers("/api/rol-permisos/**")
                        .hasRole("ADMIN")

                        // Productos: CLIENTE y AUDITOR solo lectura
                        .requestMatchers(HttpMethod.GET, "/api/productos/**")
                        .permitAll()

                        .requestMatchers("/api/productos/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "EMPLEADO")

                        // Productos carta: CLIENTE y AUDITOR solo lectura
                        .requestMatchers(HttpMethod.GET, "/api/productos-carta/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "EMPLEADO", "CLIENTE", "AUDITOR")

                        .requestMatchers("/api/productos-carta/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "EMPLEADO")

                        // Categorías: CLIENTE y AUDITOR solo lectura
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**")
                        .permitAll()

                        .requestMatchers("/api/categorias/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "EMPLEADO")

                        // Tipos de carta: ADMIN modifica, demás solo consulta
                        .requestMatchers(HttpMethod.GET, "/api/tipos-carta/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "EMPLEADO", "CLIENTE", "AUDITOR")

                        .requestMatchers("/api/tipos-carta/**")
                        .hasRole("ADMIN")

                        // Tipos de movimiento: CLIENTE no accede
                        .requestMatchers(HttpMethod.GET, "/api/tipos-movimiento/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "EMPLEADO", "AUDITOR")

                        .requestMatchers("/api/tipos-movimiento/**")
                        .hasRole("ADMIN")

                        // Movimientos inventario: AUDITOR solo lectura
                        .requestMatchers(HttpMethod.GET, "/api/movimientos-inventario/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "EMPLEADO", "AUDITOR")

                        .requestMatchers("/api/movimientos-inventario/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "EMPLEADO")

                        // Imágenes producto: CLIENTE y AUDITOR solo lectura
                        .requestMatchers(HttpMethod.GET, "/api/imagenes-producto/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "EMPLEADO", "CLIENTE", "AUDITOR")

                        .requestMatchers("/api/imagenes-producto/**")
                        .hasAnyRole("ADMIN", "SUPERVISOR", "EMPLEADO")

                        // Todo lo demás requiere autenticación
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
package com.turings.backend.config;

import com.turings.backend.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                /*
                 * CSRF se desactiva para esta API REST de demo.
                 *
                 * CSRF protege formularios web tradicionales basados en sesión/cookies.
                 * En una API REST con JWT normalmente se desactiva porque el cliente envía
                 * el token explícitamente en el header Authorization.
                 */
                .csrf(csrf -> csrf.disable())

                /*
                 * Definimos reglas de autorización por ruta.
                 *
                 * /auth/login queda público porque ahí todavía NO existe token.
                 * Todos los demás endpoints requieren autenticación.
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/registro").permitAll()
                        .requestMatchers(HttpMethod.GET,"/categoria").permitAll()
                        .requestMatchers(HttpMethod.GET,"/producto").permitAll()
                        .requestMatchers(HttpMethod.GET,"/review").permitAll()

                        .requestMatchers(HttpMethod.POST,"/categoria").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/categoria").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/categoria").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,"/detalle").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/detalle").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,"/pedido").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/pedido").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/producto").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/producto").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/producto").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,"/review").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/review").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/users").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                /*
                 * JWT es stateless.
                 *
                 * Eso significa que el servidor no guarda una sesión tradicional.
                 * Cada request debe traer su token para demostrar quién es el usuario.
                 */
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                /*
                 * AuthenticationProvider indica cómo se validan usuario y contraseña.
                 */
                .authenticationProvider(authenticationProvider())

                /*
                 * Insertamos nuestro filtro JWT antes del filtro estándar de username/password.
                 * Así Spring puede revisar el Bearer Token antes de decidir si la request entra o no.
                 */
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        /*
         * UserDetailsService indica de dónde salen los usuarios.
         * En esta demo salen de memoria.
         * En un proyecto real podrían salir de MySQL usando UserRepository.
         */
        authProvider.setUserDetailsService(userDetailsService());

        /*
         * PasswordEncoder indica cómo comparar contraseñas.
         * BCrypt nunca guarda ni compara passwords en texto plano.
         */
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        /*
         * Usuario de demo para clase.
         *
         * Credenciales para Postman/Insomnia:
         * username: admin
         * password: admin123
         *
         * Importante:
         * passwordEncoder().encode("admin123") genera un hash BCrypt.
         * Eso significa que Spring NO guarda "admin123" como texto plano.
         */
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}

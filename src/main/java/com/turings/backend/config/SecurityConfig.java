package com.turings.backend.config;

import com.turings.backend.security.JwtAuthenticationFilter;
import com.turings.backend.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserAuthService userAuthService; // Tu servicio de base de datos

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
                        /* PUBLICO  HMTL */

                        .requestMatchers(HttpMethod.GET,"/").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()
                        .requestMatchers(HttpMethod.GET,"/CSS/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/FONT/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/JS/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/Pictures/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/*.html").permitAll()

                        /* PUBLICO APIS */
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/registro").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/categorias").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/productos").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/productos/*").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/reviews").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/detalles-pedidos").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/detalles-pedidos/last").permitAll()

                        /*ADMIN HTML */

                        /*ADMIN API*/
                        .requestMatchers(HttpMethod.POST,"/api/v1/categorias").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/categorias").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/categorias").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,"/api/v1/detalles-pedidos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/detalles-pedidos").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,"/api/v1/pedidos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/pedidos").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/productos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/productos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/productos").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE,"/api/v1/reviews").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/reviews").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST,"/api/v1/usuario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/usuario").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/usuario").hasRole("ADMIN")


                        .anyRequest().hasAnyRole("ADMIN","USER")
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
        authProvider.setUserDetailsService(userAuthService);

        /*
         * PasswordEncoder indica cómo comparar contraseñas.
         * BCrypt nunca guarda ni compara passwords en texto plano.
         */
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
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

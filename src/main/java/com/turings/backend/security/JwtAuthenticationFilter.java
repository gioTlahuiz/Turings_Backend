package com.turings.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        /*
         * Si no hay header Authorization o no empieza con Bearer,
         * dejamos que la petición siga su camino.
         *
         * Ojo: dejarla seguir NO significa que entre al endpoint.
         * Más adelante SecurityConfig decidirá si esa ruta requiere autenticación.
         */
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Quitamos la palabra "Bearer " para quedarnos únicamente con el token.
        jwt = authHeader.substring(7);

        try {
            username = jwtService.extractUsername(jwt);
        } catch (Exception exception) {
            /*
             * Si el token está mal formado, expirado o firmado con otra llave,
             * no autenticamos la petición.
             * Dejamos que Spring Security responda como request no autorizada.
             */
            filterChain.doFilter(request, response);
            return;
        }

        /*
         * Si el username existe y todavía no hay una autenticación registrada
         * en el contexto de seguridad, intentamos validar el token.
         */
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                /*
                 * UsernamePasswordAuthenticationToken representa una autenticación válida
                 * dentro de Spring Security.
                 *
                 * Parámetros:
                 * - principal: datos del usuario
                 * - credentials: null porque ya no necesitamos guardar password aquí
                 * - authorities: roles/permisos del usuario
                 */
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Guardamos la autenticación en el contexto de seguridad.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}

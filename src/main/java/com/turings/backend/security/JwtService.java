package com.turings.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    /*
     * Llave secreta usada para firmar el token.
     *
     * Debe ser suficientemente larga para el algoritmo HS256.
     * Aquí usamos una llave en Base64 para simplificar la demo.
     *
     * Importante:
     * - Si alguien conoce esta llave, podría intentar generar tokens falsos.
     * - Por eso NO debe subirse a repositorios públicos en proyectos reales.
     */
    private static final String SECRET_KEY = "bWktbGxhdmUtc2VjcmV0YS1wYXJhLWRlbW8tamF2YS1zcHJpbmctc2VjdXJpdHktand0LTIwMjY=";

    /*
     * Duración del token.
     * 1000 ms * 60 segundos * 30 minutos = 30 minutos.
     */
    private static final long EXPIRATION_TIME_MS = 1000L * 60 * 30;

    public long getExpirationTimeMs() {
        return EXPIRATION_TIME_MS;
    }

    /*
     * Genera un token para un usuario autenticado.
     *
     * UserDetails viene de Spring Security y contiene información del usuario:
     * - username
     * - password encriptado
     * - roles/permisos
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("authorities", userDetails.getAuthorities());
        return generateToken(extraClaims, userDetails);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Date now = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        extraClaims.put("roles", roles);

        return Jwts.builder()
                // Claims extra: información adicional dentro del token.
                .claims(extraClaims)
                // Subject: normalmente representa el usuario principal del token.
                .subject(userDetails.getUsername())
                // Fecha de creación.
                .issuedAt(now)

                // Fecha de vencimiento. Después de esta fecha, el token ya no sirve.
                .expiration(expirationDate)
                // Firma: garantiza que el token no fue alterado.
                .signWith(getSigningKey())
                .compact();
    }

    /*
     * Extrae el username guardado en el subject del token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /*
     * Método genérico para extraer cualquier claim del token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
     * Valida que:
     * 1. El username del token coincida con el usuario cargado por Spring Security.
     * 2. El token no esté expirado.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*
     * Lee y valida los claims del token.
     *
     * parseSignedClaims verifica la firma.
     * Si el token fue alterado, expiró o tiene una firma inválida, lanzará una excepción.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

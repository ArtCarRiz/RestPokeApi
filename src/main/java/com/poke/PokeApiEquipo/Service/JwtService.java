package com.poke.PokeApiEquipo.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import com.poke.PokeApiEquipo.ML.Usuario;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String SECRET_KEY = "M2Y0ZTVnNmg4ajlrMG0xbnJwOXFyc3R1dnd4eXphYmNkZWZnaGk=";

    private static final long EXPIRATION_TIME = 3600000; // 1 hora login
    private static final long VERIFICATION_EXPIRATION_TIME = 900000; // 15 minutos verificación

    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateVerificationToken(String correo) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("tipo", "VERIFICACION_CORREO");

        return Jwts.builder()
                .claims(extraClaims)
                .subject(correo)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + VERIFICATION_EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isVerificationTokenValid(String token) {
        try {
            String tipo = extractClaim(token, claims -> claims.get("tipo", String.class));

            return "VERIFICACION_CORREO".equals(tipo)
                    && !isTokenExpired(token);

        } catch (Exception e) {
            return false;
        }
    }

    public String extractCorreoFromVerificationToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    public String generateToken(Usuario usuario) {
    Map<String, Object> extraClaims = new HashMap<>();

    extraClaims.put("tipo", "LOGIN");
    extraClaims.put("rol", usuario.getRol().getNombreRol());
    extraClaims.put("idUsuario", usuario.getIdUsuario());

    return Jwts.builder()
            .claims(extraClaims)
            .subject(usuario.getCorreo())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(getSigningKey())
            .compact();
    }
    
    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }
    
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claimsResolver.apply(claims);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
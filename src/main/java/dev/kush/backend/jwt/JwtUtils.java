package dev.kush.backend.jwt;

import dev.kush.backend.constants.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    private String SECRET_KEY = JwtConstants.JWT_SECRET;

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(Map.of("role",userDetails.getAuthorities()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24 * 3600 * 1000))
                .signWith(key)
                .compact();
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserName(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Date extractExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    private Boolean isExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        String username = extractUserName(token);
        return userDetails.getUsername().equals(username) && !isExpired(token);
    }
}

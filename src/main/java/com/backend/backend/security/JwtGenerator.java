package com.backend.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtGenerator {

    private final Key key;

    public JwtGenerator() {
        // Decode Base64 secret to generate HMAC key
        this.key = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(SecurityConstants.JWT_SECRET));
    }

    // Generate JWT with username and roles
    public String generateToken(String username, List<String> roles) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + SecurityConstants.JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Extract username from JWT
    public String extractUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    // Extract roles from JWT
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        Claims claims = getAllClaims(token);
        return (List<String>) claims.get("roles");
    }

    // Validate JWT (signature + expiration)
    public boolean validateToken(String token) {
        try {
            getAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Helper to parse token
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

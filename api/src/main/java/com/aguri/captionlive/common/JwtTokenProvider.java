package com.aguri.captionlive.common;

import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    public String generateToken(String username) {
      
        // Set the token expiration date
        Date expirationDate = new Date(System.currentTimeMillis() + jwtExpiration * 1000);

        // Generate the JWT token
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret) 
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            // Validate the JWT token
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            // Token signature validation failed
            // Handle or log the exception
        } catch (MalformedJwtException ex) {
            // Invalid JWT token
            // Handle or log the exception
        } catch (ExpiredJwtException ex) {
            // Expired JWT token
            // Handle or log the exception
        } catch (UnsupportedJwtException ex) {
            // Unsupported JWT token
            // Handle or log the exception
        } catch (IllegalArgumentException ex) {
            // JWT claims string is empty or null
            // Handle or log the exception
        }

        return false;
    }
}


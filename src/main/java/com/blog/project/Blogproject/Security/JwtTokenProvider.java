package com.blog.project.Blogproject.Security;

import com.blog.project.Blogproject.exception.BolgAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationTime;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();

        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationTime);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key())
                .compact();
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    //get username from JWT token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }

    //Validate Jwt token
    public boolean validateToken(String token) {

        try{
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        }catch (MalformedJwtException e) {
            throw new BolgAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Token..!");
        }catch (ExpiredJwtException e) {
            throw new BolgAPIException(HttpStatus.BAD_REQUEST, "Expired JWT Token..1");
        }catch (UnsupportedJwtException e) {
            throw new BolgAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT Token..!");
        }catch (IllegalArgumentException e) {
            throw new BolgAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty..!");
        }
    }
}

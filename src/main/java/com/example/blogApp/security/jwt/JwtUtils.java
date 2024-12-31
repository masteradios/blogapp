package com.example.blogApp.security.jwt;

import com.example.blogApp.exceptions.InvalidJsonException;
import com.example.blogApp.models.UserModel;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${spring.jwt.secret}")
    private String secret;

    public String getJwtFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring("Bearer ".length());
        }

        return null;
    }

    public String generateJwtFromEmail(UserDetails userDetails, long expirationInMilliSeconds) {
        //String username=userDetails.getUsername();
        String email = ((UserModel) userDetails).getEmail();

        System.out.println("email is " + email);
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream().map((role) -> role.getAuthority()).collect(Collectors.toList());
        claims.put("roles", roles);
        return Jwts.builder().subject(email)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expirationInMilliSeconds))
                .signWith(key())
                .claims(claims)
                .compact();
    }

    public Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String getUsernameFromJwt(String jwtToken) {
        return Jwts
                .parser()
                .verifyWith((SecretKey) key())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload()
                .getSubject();
    }

    public boolean isJwtTokenValid(String jwtToken) throws AuthenticationException {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(jwtToken);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            throw new InvalidJsonException("Invalid Token !!");

        }

    }

}

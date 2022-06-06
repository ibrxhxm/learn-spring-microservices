package com.example.photoappgateway.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
public class JwtTokenUtil implements Serializable {
    @Value("${security.config.token_secret}")
    private String secret;

    public boolean isValidToken(String token) {
        String subject;
        try {
            subject = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception ex) {
            return false;
        }
        return !(Objects.isNull(subject) || subject.isEmpty());
    }
}

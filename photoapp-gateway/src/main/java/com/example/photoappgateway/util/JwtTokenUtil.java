package com.example.photoappgateway.util;

import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;

@Component
public class JwtTokenUtil implements Serializable {

    private final Environment env;

    public JwtTokenUtil(Environment env) {
        this.env = env;
    }

    public boolean isValidToken(String token) {
        String subject;
        try {
            subject = Jwts.parser()
                    .setSigningKey(env.getProperty("security.config.token_secret"))
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception ex) {
            return false;
        }
        return !(Objects.isNull(subject) || subject.isEmpty());
    }
}

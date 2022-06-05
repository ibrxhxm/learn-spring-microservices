package com.example.photoappusers.security;

import com.example.photoappusers.domain.dto.request.LoginRequest;
import com.example.photoappusers.service.UserService;
import com.example.photoappusers.service.impl.UserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserDetailsService userDetailsService;
    private final Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, Environment env) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.env = env;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequest creds = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((UserPrincipal) authResult.getPrincipal()).getUsername();

        UserPrincipal principal = (UserPrincipal) userDetailsService.loadUserByUsername(username);

        String token = Jwts.builder()
                .setSubject(principal.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(Objects.requireNonNull(env.getProperty("security.config.token_lifespan")))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("security.config.token_secret"))
                .compact();

        response.addHeader("token", token);
        response.addHeader("user_id", principal.getUserId());
    }
}

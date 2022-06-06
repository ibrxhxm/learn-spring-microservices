package com.example.photoappgateway.security;

import com.example.photoappgateway.util.JwtTokenUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final JwtTokenUtil jwtTokenUtil;

    public AuthorizationHeaderFilter(JwtTokenUtil jwtTokenUtil) {
        super(Config.class);
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
            }

            Optional<String> authHeader = Optional.ofNullable(request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0));
            if (authHeader.isEmpty())
                return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
            String token = authHeader.get().replace("Bearer", "").trim();

            if (!jwtTokenUtil.isValidToken(token)) {
                return onError(exchange, "You are not authorized to access this resource", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);

        return response.setComplete();
    }
}

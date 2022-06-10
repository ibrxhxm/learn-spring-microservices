package com.example.photoappgateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class GlobalFiltersConfig {

    @Bean
    public GlobalFilter preFilter() {
        return (exchange, chain) -> {
            log.info("Second Global pre-filter executed...");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Second Global post-filter executed...");
            }));
        };
    }
}

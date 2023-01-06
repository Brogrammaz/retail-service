package com.kharim.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity){

        serverHttpSecurity.csrf()
                .disable()
                .authorizeExchange(exchange -> exchange.pathMatchers("/eureka/**")
                .permitAll() // permit all static file calls
                .anyExchange() // otherwise, authenticate the rest.
                .authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);

        return serverHttpSecurity.build();

    }

}

package com.example.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("product-service", r ->
                r.path("/api/product/**")
                 .uri("http://localhost:8081"))
            .route("user-service", r ->
                r.path("/api/user/**")
                 .uri("http://localhost:8082"))
            .route("order-service", r ->
                r.path("/api/order/**", "/api/cart/**")
                 .uri("http://localhost:8083"))
            .build();
    }
}

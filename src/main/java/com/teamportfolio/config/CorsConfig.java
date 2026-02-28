package com.teamportfolio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CorsConfig — Cross-Origin Resource Sharing (CORS) configuration.
 *
 * Allows the frontend (served from index.html opened via file:// or
 * a local dev server on port 5500/3000) to call the Spring Boot API
 * on port 8080 during development.
 *
 * IMPORTANT: In production, restrict allowedOrigins to your actual domain.
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        // Allow file:// origins and common local dev servers
                        .allowedOriginPatterns(
                                "http://localhost:*",
                                "http://127.0.0.1:*",
                                "file://*")
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(false)
                        // Cache preflight response for 1 hour
                        .maxAge(3600);
            }
        };
    }
}

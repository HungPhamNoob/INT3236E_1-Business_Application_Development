package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/profile", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .addLogoutHandler(keycloakLogoutHandler()) // Thêm logout handler
                )
                .build();
    }

    // Logout handler để logout khỏi Keycloak
    private LogoutHandler keycloakLogoutHandler() {
        return (request, response, authentication) -> {
            try {
                // Redirect đến Keycloak logout endpoint
                String keycloakLogoutUrl = "http://localhost:8081/realms/baeldung-sso/protocol/openid-connect/logout";
                response.sendRedirect(keycloakLogoutUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}
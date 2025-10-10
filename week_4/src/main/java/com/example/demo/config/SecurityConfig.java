package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity // bật Spring Security cho web
@EnableMethodSecurity // cho phép dùng @PreAuthorize, @PostAuthorize
public class SecurityConfig {

    // Các endpoint cho phép truy cập mà KHÔNG cần token (anonymous access)
    private final String[] PUBLIC_ENDPOINTS = {
            "/welcome",          // đăng ký user mới
            "/auth/token",     // lấy token
            "/users"
    };

    @Value("${jwt.signerKey}") // key ký JWT lấy từ application.yml
    private String signerKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // Config rule cho request
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll() // cho phép POST vào các endpoint public
                        // User endpoints - ADMIN có toàn quyền, USER chỉ xem thông tin cá nhân
                        .requestMatchers(HttpMethod.POST, "/users").hasAnyRole("ADMIN") // Tạo user
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN") // Xem tất cả users
                        .requestMatchers(HttpMethod.GET, "/users/myInfo").hasAnyRole("ADMIN", "USER") // Xem thông tin cá nhân
                        .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("ADMIN", "USER") // Xem user cụ thể
                        .requestMatchers(HttpMethod.PUT, "/users/{id}").hasRole("ADMIN") // Cập nhật user
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("ADMIN") // CHỈ ADMIN xóa user

                        // Blog endpoints - ADMIN xem tất cả, USER chỉ xem/cập nhật blog của mình
                        .requestMatchers(HttpMethod.POST, "/blogs").hasAnyRole("ADMIN", "USER") // Tạo blog
                        .requestMatchers(HttpMethod.GET, "/blogs").hasAnyRole("ADMIN", "USER") // Xem blogs
                        .requestMatchers(HttpMethod.GET, "/blogs/{id}").hasAnyRole("ADMIN", "USER") // Xem blog cụ thể
                        .requestMatchers(HttpMethod.PUT, "/blogs/{id}").hasAnyRole("ADMIN", "USER") // Cập nhật blog
                        .requestMatchers(HttpMethod.DELETE, "/blogs/{id}").hasAnyRole("ADMIN", "USER") // Xóa blog

                        .anyRequest().authenticated()); // fallback

        // Config để Spring Security hiểu app này là Resource Server (sử dụng JWT để authen)
        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer ->
                        jwtConfigurer.decoder(jwtDecoder()) // validate chữ ký + hạn token
                                // chuyển claims ("user", "admin") thành GrantedAuthorities ("ROLE_user", "ROLE_admin")
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())

                )
        );

        // Disable CSRF (vì API không dùng form submit truyền thống)
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        // Converter để đọc claim "roles" hoặc "scope" từ JWT rồi convert thành GrantedAuthority
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_"); // thêm prefix ROLE_ để Spring Security hiểu
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    JwtDecoder jwtDecoder(){
        // Giải mã + verify JWT dùng thuật toán HMAC SHA512
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        // Dùng BCrypt để hash password với strength = 10
        return new BCryptPasswordEncoder(10);
    }
}
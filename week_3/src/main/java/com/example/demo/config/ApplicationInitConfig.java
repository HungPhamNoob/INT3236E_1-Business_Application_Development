package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;


    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {

            // Tạo sẵn role ADMIN nếu chưa có
            Role adminRole = roleRepository.findByRole("ADMIN")
                    .orElseGet(() -> roleRepository.save(Role.builder().role("ADMIN").build()));

            // Tạo sẵn role USER nếu chưa có
            Role userRole = roleRepository.findByRole("USER")
                    .orElseGet(() -> roleRepository.save(Role.builder().role("USER").build()));


            if (userRepository.findByUsername("admin").isEmpty()){
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(Set.of(adminRole, userRole))
                        .build();


                userRepository.save(user);
                log.warn("admin has been created with default password: admin");
            }

            if (userRepository.findByUsername("user").isEmpty()){
                User user = User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("user"))
                        .roles(Set.of(userRole))
                        .build();


                userRepository.save(user);
                log.warn("user has been created with default password: user");
            }
        };
    }
}

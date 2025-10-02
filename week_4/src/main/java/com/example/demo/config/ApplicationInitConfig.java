package com.example.demo.config;

import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;


    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {



            if (userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();


                userRepository.save(user);
                log.warn("admin has been created with default password: admin");
            }

            if (userRepository.findByUsername("user").isEmpty()){

                var roles = new HashSet<String>();
                roles.add(Role.USER.name());

                User user = User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("user"))
                        .roles(roles)
                        .build();


                userRepository.save(user);
                log.warn("user has been created with default password: user");
            }
        };
    }
}

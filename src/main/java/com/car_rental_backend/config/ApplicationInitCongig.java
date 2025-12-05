package com.car_rental_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.ApplicationRunner;
import com.car_rental_backend.enums.Role;
import com.car_rental_backend.model.User;
import com.car_rental_backend.repository.UserRepository;
import java.util.HashSet;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.context.annotation.Bean;


@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitCongig {
    
    PasswordEncoder passwordEncoder;


    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args ->{
            if(userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());

                User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(roles)
                    .build();
                userRepository.save(admin);
            }
        };
    }

}

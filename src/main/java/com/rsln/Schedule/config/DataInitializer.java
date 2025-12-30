package com.rsln.Schedule.config;

import com.rsln.Schedule.models.Role;
import com.rsln.Schedule.models.User;
import com.rsln.Schedule.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User admin = new User(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        Role.ADMIN
                );
                User user = new User(
                        "user",
                        passwordEncoder.encode("user123"),
                        Role.USER
                );

                userRepository.save(admin);
                userRepository.save(user);

                System.out.println("Test users created: admin / user");
            }
        };
    }
}


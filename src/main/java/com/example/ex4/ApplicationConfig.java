package com.example.ex4;

import com.example.ex4.entity.User;
import com.example.ex4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity

public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults()).csrf(withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers( "/css/**", "/login", "/register","/register-admin").permitAll()
                        .requestMatchers("/user/**", "/cart/**","/api/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/provider-image/**").hasAnyRole("USER","ADMIN", "SUPER_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .expiredUrl("/login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling
                                .accessDeniedPage("/403")
                );

        return http.build();
    }
    @Bean
    public CommandLineRunner initAdmin(PasswordEncoder passwordEncoder,
                                       @Value("${admin.username}") String username,
                                       @Value("${admin.email}") String email,
                                       @Value("${admin.password}") String password, UserRepository userRepository)
    {
        return args -> {
            if (userRepository.findByUserName(username) == null) {
                User admin = new User(username, email, passwordEncoder.encode(password));
                admin.setRole("SUPER_ADMIN");
                userRepository.save(admin);
                System.out.println("✅ Admin user created: " + username);
            } else {
                System.out.println("ℹ️ Admin user already exists: " + username);
            }
        };
    }
}
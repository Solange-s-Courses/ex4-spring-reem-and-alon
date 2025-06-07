package com.example.ex4;

import com.example.ex4.entity.Admin;
import com.example.ex4.entity.AppUser;
import com.example.ex4.repository.AdminRepository;
import com.example.ex4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ApplicationConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults()).csrf(withDefaults())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers( "/css/**", "/login", "/register").permitAll()
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/shared/**").hasAnyRole("USER","ADMIN")
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
    public CommandLineRunner initAdmin(AdminRepository adminRepository,
                                       PasswordEncoder passwordEncoder,
                                       @Value("${admin.username}") String username,
                                       @Value("${admin.email}") String email,
                                       @Value("${admin.password}") String password)
    {
        return args -> {
            if (adminRepository.findByUserName(username).isEmpty()) {
                Admin admin = new Admin();
                admin.setEmail(email);
                admin.setUserName(username);
                admin.setPassword(passwordEncoder.encode(password));
                admin.setRole("ADMIN");
                admin.setProfileUrl("images/profile-picture.jpg");
                adminRepository.save(admin);
                System.out.println("✅ Admin user created: " + username);
            } else {
                System.out.println("ℹ️ Admin user already exists: " + username);
            }
        };
    }
}
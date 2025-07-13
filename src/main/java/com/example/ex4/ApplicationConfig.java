package com.example.ex4;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Application configuration file to apply spring security
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity

public class ApplicationConfig {

    /**
     * Creates a BCrypt password encoder bean for secure password hashing.
     *
     * @return PasswordEncoder instance (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines the security filter chain for the application, including
     * endpoint authorization, CSRF exclusions, login, session, and logout settings.
     *
     * <p>
     * Main customizations:
     * <ul>
     *     <li>CSRF disabled for /chat-websocket/** and /api/chat/**</li>
     *     <li>Custom login and logout pages</li>
     *     <li>Role-based access to specific routes</li>
     *     <li>Session management (single session per user)</li>
     *     <li>API endpoints get 401 (UNAUTHORIZED) on failure</li>
     * </ul>
     *
     * @param http HttpSecurity builder
     * @return SecurityFilterChain configured chain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults()).csrf(csrf ->csrf.ignoringRequestMatchers("/chat-websocket/**", "/api/chat/**"))
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/chat-websocket/**").authenticated()
                        .requestMatchers( "/css/**", "/login", "/register/**").permitAll()
                        .requestMatchers("/user/**", "/cart/**","/api/cart/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/chats/**", "/api/chat/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/provider-image/**").hasAnyRole("USER","ADMIN", "SUPER_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .sessionManagement(
                        session -> session
                        .maximumSessions(1)
                        .expiredUrl("/login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID") // name of Spring cookie
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .exceptionHandling(
                        exceptionHandling -> exceptionHandling
                                .defaultAuthenticationEntryPointFor(
                                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED),
                                        request -> request.getRequestURI().startsWith("/api/")
                                )
                                .accessDeniedPage("/403")
                );

        return http.build();
    }


//    @Bean
//    public CommandLineRunner initAdmin(PasswordEncoder passwordEncoder,
//                                       @Value("${admin.username}") String username,
//                                       @Value("${admin.email}") String email,
//                                       @Value("${admin.password}") String password, UserRepository userRepository)
//    {
//        return args -> {
//            if (userRepository.findByUserName(username) == null) {
//                User admin = User.builder().userName(username).email(email).password(passwordEncoder.encode(password)).role("SUPER_ADMIN").build();
//                userRepository.save(admin);
//                System.out.println("✅ Admin user created: " + username);
//            } else {
//                System.out.println("ℹ️ Admin user already exists: " + username);
//            }
//        };
//    }


}
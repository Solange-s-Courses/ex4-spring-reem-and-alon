package com.example.ex4.service;

import com.example.ex4.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ex4.entity.User;
import com.example.ex4.repository.UserRepository;
import java.math.BigDecimal;

/**
 * Service for user management, registration, authentication and balance management.
 *
 * @see User
 * @see UserRepository
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Deposits money to the user's credit balance.
     *
     * @param user the user
     * @param amount the amount to add
     */
    public void depositToBalance(User user, BigDecimal amount) {
        user.setCreditBalance(user.getCreditBalance().add(amount));
        userRepository.save(user);
    }

    /**
     * Registers a new user (hashes password and saves to DB).
     *
     * @param user the user to add
     */
    public void addNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /**
     * Loads a user by username for Spring Security.
     *
     * @param username the username
     * @return the user principal
     * @throws UsernameNotFoundException if the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}

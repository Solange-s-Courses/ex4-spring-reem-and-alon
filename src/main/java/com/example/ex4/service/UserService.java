package com.example.ex4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ex4.repository.AppUser;
import com.example.ex4.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(AppUser user) {
        userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void deleteUser(AppUser u) {
        userRepository.delete(u);
    }

    public void updateUser(AppUser user) {
        userRepository.save(user);
    }

    public Optional<AppUser> getUser(long id) {
        return userRepository.findById(id);
    }

    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public void registerUser(AppUser user) {
        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}

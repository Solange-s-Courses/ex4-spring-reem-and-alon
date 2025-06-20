package com.example.ex4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ex4.entity.AppUser;
import com.example.ex4.repository.UserRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(AppUser user) {userRepository.save(user);}

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

    public void depositToBalance(AppUser user, int amount) {
        user.setCreditBalance(user.getCreditBalance() + amount);
        userRepository.save(user);
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public void addNewUser(AppUser user,String role) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(role);
        userRepository.save(user);
    }

    public String findUserRole(String username) {
        return userRepository.findByUserName(username).getRole();
    }

    public int findUserBalance(String username) {
        return userRepository.findByUserName(username).getCreditBalance();
    }
}

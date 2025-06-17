package com.example.ex4.service;

import com.example.ex4.dto.AdminRegistrationFormDTO;
import com.example.ex4.entity.ProviderProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ex4.entity.AppUser;
import com.example.ex4.repository.UserRepository;

import java.io.IOException;
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

    public List<AppUser> getUsers() {
        return userRepository.findAll();
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public void registerUser(AppUser user,String Role) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role);
        userRepository.save(user);
    }

    public AppUser buildAdminUser(AdminRegistrationFormDTO form) {
        AppUser user = new AppUser();
        user.setUserName(form.getUserName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setRole("ADMIN");
        return user;
    }
    public void removeUser(AppUser user) {userRepository.deleteById(user.getId());}

}

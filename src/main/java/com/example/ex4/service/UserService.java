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
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(User user) {userRepository.save(user);}

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public void deleteUser(User u) {
        userRepository.delete(u);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    public void depositToBalance(User user, int amount) {
        user.setCreditBalance(user.getCreditBalance() + amount);
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public void addNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }
}

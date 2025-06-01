package com.example.ex4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.ex4.repository.User;
import com.example.ex4.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public void saveUser(User user) {
        repository.save(user);
    }

    public void deleteUser(long id) {
        repository.deleteById(id);
    }

    public void deleteUser(User u) {
        repository.delete(u);
    }

    public void updateUser(User user) {
        repository.save(user);
    }

    public Optional<User> getUser(long id) {
        return repository.findById(id);
    }

    public List<User> getUsers() {
        return repository.findAll();
    }
}

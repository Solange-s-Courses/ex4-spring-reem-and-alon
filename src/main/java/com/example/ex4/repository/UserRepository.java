package com.example.ex4.repository;

import com.example.ex4.entity.User;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);

    boolean existsUserByUserName(@Pattern(regexp = "^[A-Za-z]+$") String userName);
}

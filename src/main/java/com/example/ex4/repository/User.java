package com.example.ex4.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]+$")
    private String username;

    @NotEmpty
    @Column(unique = true)
    @Email(message = "Email is mandatory")
    private String email;

    public User() {}

    public User(String username, String email) {
        this.username =
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}

package com.example.ex4.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serializable;

@Entity
public class AppUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]+$")
    @Column(unique = true)
    private String userName;

    @NotEmpty
    @Email(message = "Email is mandatory")
    private String email;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    @Email(message = "Email is mandatory")
    private String password;

    public AppUser() {this.role = "USER";}

    public AppUser(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = "USER";
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setUserName(String userName) {
        this.userName = userName.trim();
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }

}

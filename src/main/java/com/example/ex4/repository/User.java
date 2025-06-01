package com.example.ex4.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]+$")
    private String firstName;

    @NotEmpty
    @Pattern(regexp = "^[A-Za-z]+$")
    private String lastName;

    @NotEmpty
    @Column(unique = true)
    @Email(message = "Email is mandatory")
    private String email;

    public User() {}

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}

package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "^[A-Za-z]+$", message = "username can contain letters only!")
    @Column(unique = true)
    private String userName;

    @NotEmpty
    @Email(message = "Email is mandatory")
    private String email;

    @Builder.Default
    private String role = "USER";

    @Column(nullable = false)
    private String password;

    @Builder.Default
    private int creditBalance = 0;
}

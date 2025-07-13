package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Entity representing an application user (client or provider admin).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements Serializable {

    /**
     * Unique identifier for the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Username (letters only).
     */
    @Pattern(regexp = "^[A-Za-z]+$", message = "username can contain letters only!")
    @Column(unique = true)
    private String userName;

    /**
     * Email address (must be valid and not empty).
     */
    @NotEmpty
    @Email(message = "Email is mandatory")
    private String email;

    /**
     * User role (default "USER").
     */
    @Builder.Default
    private String role = "USER";

    /**
     * User's hashed password (never empty).
     */
    @Column(nullable = false)
    private String password;

    /**
     * Credit balance for the user (default 0).
     */
    @Builder.Default
    private BigDecimal creditBalance = BigDecimal.valueOf(0);
}

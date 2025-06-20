package com.example.ex4.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DialectOverride;


import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class AppUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "^[A-Za-z]+$")
    @Column(unique = true)
    private String userName;

    @NotEmpty
    @Email(message = "Email is mandatory")
    private String email;

    @ColumnDefault("USER")
    private String role;

    @Column(nullable = false)
    private String password;

    @ColumnDefault("0")
    private BigDecimal creditBalance;

    public AppUser(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}

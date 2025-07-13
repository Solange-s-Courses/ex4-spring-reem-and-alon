package com.example.ex4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

/**
 * Entity representing a provider's category (e.g. "INTERNET", "CELLULAR").
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProviderCategory implements Serializable {
    /**
     * Unique identifier for the category.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    /**
     * Name of the category.
     */
    @Column(nullable = false)
    private String name;

    /**
     * List of providers belonging to this category.
     *
     * @see ProviderProfile
     */
    @OneToMany(mappedBy = "category")
    private List<ProviderProfile> providers;
}


package com.example.ex4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

/**
 * Entity representing a provider's profile.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProviderProfile implements Serializable {

    /**
     * Unique identifier for the provider profile.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Name of the company.
     */
    private String companyName;

    /**
     * The provider's category.
     *
     * @see ProviderCategory
     */
    @ManyToOne
    private ProviderCategory category;

    /**
     * Whether this profile is approved.
     */
    @Builder.Default
    private boolean approved = false;

    /**
     * Profile image as a byte array.
     */
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] profileImage;

    /**
     * Associated user account for this provider.
     *
     * @see User
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    /**
     * List of plans/packages offered by this provider.
     *
     * @see PlanPackage
     */
    @OneToMany(mappedBy = "providerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanPackage> planPackages;
}

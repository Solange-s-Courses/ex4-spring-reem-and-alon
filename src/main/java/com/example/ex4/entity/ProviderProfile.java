package com.example.ex4.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProviderProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String companyName;

    @ManyToOne
    private ProviderCategory category;

    @Builder.Default
    private boolean approved = false;

    @Lob
    private byte[] profileImage;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    @OneToMany(mappedBy = "providerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanPackage> planPackage;
}

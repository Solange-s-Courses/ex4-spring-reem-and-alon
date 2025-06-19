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

    private String providerName;

    private String category;

    private String contactInfo;

    private boolean approved = false;

    @Lob
    private byte[] profileImage;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private AppUser appUser;

    @OneToMany(mappedBy = "providerProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    //   @JoinColumn(name = "app_user_id", unique = true)
    private List<PlanPackage> planPackage;
}

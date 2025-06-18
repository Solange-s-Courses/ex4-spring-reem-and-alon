package com.example.ex4.entity;

import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    public ProviderProfile() {}

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public byte[] getProfileImage() { return profileImage; }
    public void setProfileImage(byte[] profileImage) { this.profileImage = profileImage; }

    public void setAppUser(AppUser appUser) { this.appUser = appUser; }

    public AppUser getAppUser() {return appUser;}

    public String getCategory() { return category; }
    public void setCategory(String category) {this.category = category;}

    public long getId() {return id;}

    public void setContactInfo(String contactInfo) {this.contactInfo = contactInfo;}
    public String getContactInfo() {return contactInfo;}
    public boolean isComplete() {
        return providerName != null && !providerName.isEmpty()
                && profileImage != null && profileImage.length > 0;
    }
    public void setApproved(boolean approved) {this.approved = approved;}
    public boolean isApproved() {return approved;}
}

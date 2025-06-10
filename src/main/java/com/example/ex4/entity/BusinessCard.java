package com.example.ex4.entity;

import jakarta.persistence.*;
@Entity
public class BusinessCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "app_user_id", unique = true)
    private AppUser appUser;

    private String category;

    private String aboutMe;

    private String city;

    @Lob
    private byte[] profileImage;

    public BusinessCard() {}

    public String getAboutMe() { return aboutMe; }
    public void setAboutMe(String aboutMe) { this.aboutMe = aboutMe; }

    public byte[] getProfileImage() { return profileImage; }
    public void setProfileImage(byte[] profileImage) { this.profileImage = profileImage; }

    public void setAppUser(AppUser appUser) { this.appUser = appUser; }

    public String getCategory() { return category; }
    public void setCategory(String category) {this.category = category;}

    public long getId() {return id;}

    public void setCity(String city) {this.city = city;}
    public String getCity() {return city;}
    public boolean isComplete() {
        return aboutMe != null && !aboutMe.isEmpty()
                && profileImage != null && profileImage.length > 0;
    }
}

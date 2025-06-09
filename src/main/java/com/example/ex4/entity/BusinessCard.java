package com.example.ex4.entity;

import com.example.ex4.constants.ProgrammingLanguage;
import jakarta.persistence.*;
import org.hibernate.action.internal.OrphanRemovalAction;

import java.util.Set;

@Entity
public class BusinessCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "app_user_id", unique = true)
    private AppUser appUser;

    @Column(length = 200)
    private String aboutMe;

    @Lob
    private byte[] profileImage;

    public BusinessCard() {}

    public String getAboutMe() { return aboutMe; }
    public void setAboutMe(String aboutMe) { this.aboutMe = aboutMe; }

    public byte[] getProfileImage() { return profileImage; }
    public void setProfileImage(byte[] profileImage) { this.profileImage = profileImage; }

    public void setAppUser(AppUser appUser) { this.appUser = appUser; }

    public long getId() {return id;}

    public boolean isComplete() {
        return aboutMe != null && !aboutMe.isEmpty()
                && profileImage != null && profileImage.length > 0;
    }
}

package com.example.ex4.entity;

import com.example.ex4.constants.ProgrammingLanguage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
public class Admin extends AppUser {

    @Column(length = 200)
    private String aboutMe;

    @Lob
    private byte[] profileImage;

    @ElementCollection(targetClass = ProgrammingLanguage.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "admin_languages", joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "language")
    private Set<ProgrammingLanguage> languages;

    public String getAboutMe() { return aboutMe; }
    public void setAboutMe(String aboutMe) { this.aboutMe = aboutMe; }

    public byte[] getProfileImage() { return profileImage; }
    public void setProfileImage(byte[] profileImage) { this.profileImage = profileImage; }

    public Set<ProgrammingLanguage> getLanguages() { return languages; }
    public void setLanguages(Set<ProgrammingLanguage> languages) { this.languages = languages; }

    public boolean isComplete() {
        return aboutMe != null && !aboutMe.isEmpty()
                && languages != null && !languages.isEmpty()
                && profileImage != null && profileImage.length > 0;
    }
}

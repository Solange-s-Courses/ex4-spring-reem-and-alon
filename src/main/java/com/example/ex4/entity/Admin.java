package com.example.ex4.entity;

import com.example.ex4.constants.ProgrammingLanguage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

@Entity
public class Admin extends AppUser {

    @Column(length = 200)
    @NotEmpty(message = "About Me is required")
    private String aboutMe;

    @Column(length = 200)
    private String profileUrl;

    @ElementCollection(targetClass = ProgrammingLanguage.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "admin_languages", joinColumns = @JoinColumn(name = "admin_id"))
    @Column(name = "language")
    private Set<ProgrammingLanguage> languages;

    public String getAboutMe() { return aboutMe; }
    public void setAboutMe(String aboutMe) { this.aboutMe = aboutMe; }

    public String getProfileUrl() { return profileUrl; }
    public void setProfileUrl(String profileUrl) { this.profileUrl = profileUrl; }

    public Set<ProgrammingLanguage> getLanguages() { return languages; }
    public void setLanguages(Set<ProgrammingLanguage> languages) { this.languages = languages; }
}

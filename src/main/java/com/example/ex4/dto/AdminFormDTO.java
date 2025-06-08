package com.example.ex4.dto;

import com.example.ex4.constants.ProgrammingLanguage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class AdminFormDTO {
    @NotBlank(message = "About Me is required")
    private String aboutMe;
    private MultipartFile imageFile;

    @NotEmpty(message = "Please select at least one language")
    private Set<ProgrammingLanguage> languages;

    public MultipartFile getImageFile() {
        return imageFile;
    }
    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
    public Set<ProgrammingLanguage> getLanguages() {return languages;}
    public void setLanguages(Set<ProgrammingLanguage> languages) { this.languages = languages; }
    public String getAboutMe() { return aboutMe; }
    public void setAboutMe(String aboutMe) { this.aboutMe = aboutMe; }
}


package com.example.ex4.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class BusinessCardFormDTO {



    @NotBlank(message = "About Me is required")
    private String aboutMe;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "City is required")
    private String city;

    private MultipartFile imageFile;


    public MultipartFile getImageFile() {return imageFile;}
    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getAboutMe() { return aboutMe; }
    public void setAboutMe(String aboutMe) { this.aboutMe = aboutMe; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}


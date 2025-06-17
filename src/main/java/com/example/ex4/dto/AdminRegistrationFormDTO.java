package com.example.ex4.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class AdminRegistrationFormDTO {
    private Long id;

    @NotBlank(message = "Provider mail is required")
    private String userName;

    @NotBlank(message = "Provider mail is required")
    private String email;

    @NotBlank(message = "Provider password is required")
    private String password;

    @NotBlank(message = "Provider name is required")
    private String providerName;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "City is required")
    private String contactInfo;

    private MultipartFile imageFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getImageFile() throws IOException {return imageFile;}
    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getProviderName() { return providerName; }
    public void setProviderName(String providerName) { this.providerName = providerName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

}


package com.example.ex4.service;

import com.example.ex4.dto.ProviderProfileDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.ProviderProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ProviderProfileService {
    @Autowired
    private ProviderProfileRepository repository;

    public ProviderProfile findProviderProfile(AppUser admin) {
        return repository.findByAppUser(admin);
    }

    public void saveProviderProfile(AppUser admin, ProviderProfileDTO profile) {
        try {
            ProviderProfile card = new ProviderProfile();
            card.setProfileImage(profile.getImageFile().getBytes());
            card.setCategory(profile.getCategory());
            card.setProviderName(profile.getProviderName());
            card.setContactInfo(profile.getContactInfo());
            card.setAppUser(admin);
            repository.save(card);
        }
        catch (IOException e) {
            throw new RuntimeException("You must provide an image file");
        }
    }

    public byte[] findProfileImage(String username) {
        ProviderProfile adminBusinessCard = repository.findProviderProfileByAppUser_UserName(username).orElseThrow(()-> new RuntimeException("No profile found for username " + username));
        return adminBusinessCard.getProfileImage();
    }
}

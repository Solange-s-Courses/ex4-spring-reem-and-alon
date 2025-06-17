package com.example.ex4.service;

import com.example.ex4.dto.AdminRegistrationFormDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.ProviderProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProviderProfileService {
    @Autowired
    private ProviderProfileRepository repository;

    public ProviderProfile findProviderProfile(AppUser admin) {
        return repository.findByAppUser(admin);
    }
    public ProviderProfile findProviderProfileById(long id) {return repository.findById(id).orElse(null);}

    public void saveProviderProfile(AppUser admin, AdminRegistrationFormDTO profileForm) {
        try {
            ProviderProfile profile = new ProviderProfile();
            profile.setProfileImage(profileForm.getImageFile().getBytes());
            profile.setCategory(profileForm.getCategory());
            profile.setProviderName(profileForm.getProviderName());
            profile.setContactInfo(profileForm.getContactInfo());
            profile.setAppUser(admin);
            profile.setApproved(false);
            repository.save(profile);
        }
        catch (IOException e) {
            throw new RuntimeException("You must provide an image file");
        }
    }

    public byte[] findProfileImage(Long id) {
        ProviderProfile adminBusinessCard = repository.findById(id).orElseThrow(()-> new RuntimeException("No profile found for username "));
        return adminBusinessCard.getProfileImage();
    }

    public List<ProviderProfile> findAllPendingProfiles(){ return repository.findAllByApprovedFalse().orElse(new ArrayList<>()); }
    public void removeProviderProfile(long id) {repository.deleteById(id);}
}

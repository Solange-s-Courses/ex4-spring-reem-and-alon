package com.example.ex4.service;

import com.example.ex4.dto.AdminRegistrationFormDTO;
import com.example.ex4.entity.ProviderCategory;
import com.example.ex4.entity.User;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.repository.ProviderCategoryRepository;
import com.example.ex4.repository.ProviderProfileRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProviderProfileService {
    @Autowired
    private UserService userService;
    @Autowired
    private ProviderProfileRepository providerProfileRepository;
    @Autowired
    private ProviderCategoryRepository providerCategoryRepository;

    public ProviderProfile findProviderProfile(User admin) {
        return providerProfileRepository.findByUser(admin).orElse(null);
    }

    public void activateAdminAccount(long id) {
        ProviderProfile profile = providerProfileRepository.findById(id).orElseThrow(() -> new RuntimeException("Provider profile not found"));
        profile.setApproved(true);
        providerProfileRepository.save(profile);
    }

    @Transactional
    public void registerProviderProfile(AdminRegistrationFormDTO profileForm) throws IOException {


        User admin = User.builder().userName(profileForm.getUserName())
                .email(profileForm.getEmail())
                .password(profileForm.getPassword())
                .role("ADMIN")
                .build();

        userService.addNewUser(admin);

        ProviderCategory category = providerCategoryRepository.findByName(profileForm.getCategory());

        ProviderProfile profile = ProviderProfile.builder()
                .companyName(profileForm.getCompanyName())
                .profileImage(profileForm.getImageFile().getBytes())
                .category(category)
                .user(admin).build();
        providerProfileRepository.save(profile);
    }

    public List<ProviderProfile> findAllProvidersByCategory(ProviderCategory category) {
        return providerProfileRepository.findAllByCategory(category);
    }

    public byte[] findProfileImage(Long id) {
        ProviderProfile profile = providerProfileRepository.findById(id).orElseThrow(()-> new RuntimeException("No profile found"));
        return profile.getProfileImage();
    }

    public List<ProviderProfile> findAllPendingProfiles(){ return providerProfileRepository.findAllByApprovedFalse().orElse(new ArrayList<>()); }
    public void removeProviderProfile(long id) {providerProfileRepository.deleteById(id);}
}

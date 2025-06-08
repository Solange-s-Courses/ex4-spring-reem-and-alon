package com.example.ex4.service;

import com.example.ex4.dto.AdminFormDTO;
import com.example.ex4.entity.Admin;
import com.example.ex4.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public Admin findAdmin(String username) {
        Optional<Admin> admin = adminRepository.findByUserName(username);
        if (admin.isEmpty()) {
            throw new RuntimeException("Admin not found");
        }
        return admin.get();
    }

    public void saveAdminProfile(String username, AdminFormDTO profile) throws IOException {
        Admin admin = adminRepository.findByUserName(username).orElseThrow(() -> new IllegalArgumentException("Admin not found"));
        if (profile.getImageFile() != null && !profile.getImageFile().isEmpty()) {
            admin.setProfileImage(profile.getImageFile().getBytes());
        }
        admin.setAboutMe(profile.getAboutMe());
        admin.setLanguages(profile.getLanguages());
        adminRepository.save(admin);
    }

    public byte[] findProfileImage(long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Admin not found"));
        return admin.getProfileImage();
    }
}

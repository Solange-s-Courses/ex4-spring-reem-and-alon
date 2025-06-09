package com.example.ex4.controller;

import com.example.ex4.constants.ProgrammingLanguage;
import com.example.ex4.dto.AdminFormDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.BusinessCard;
import com.example.ex4.repository.BusinessCardRepository;
import com.example.ex4.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserService adminService;

    @Autowired
    private BusinessCardRepository businessCardRepository;

    @GetMapping
    public String adminIndex(Model model, Principal principal) {
        AppUser admin = adminService.findByUsername(principal.getName());
        model.addAttribute("admin", admin);
        model.addAttribute("businessCard", businessCardRepository.findByAppUser(admin).orElse(null));
        return "admin/index";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {
        BusinessCard businessCard = businessCardRepository.findById(id).orElse(null);
        if (businessCard == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] image = businessCard.getProfileImage();
        if (image == null || image.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/create-profile")
    public String adminProfile(Model model) {
        model.addAttribute("businessInfo", new AdminFormDTO());
        return "admin/adminProfileForm";
    }

    @PostMapping("/create-profile")
    public String createProfile(@Valid @ModelAttribute("businessInfo") AdminFormDTO profile,
                                Principal principal,
                                BindingResult result) throws IOException
    {
        AppUser admin = adminService.findByUsername(principal.getName());
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "admin/adminProfileForm";
        }
        BusinessCard card = new BusinessCard();
        card.setProfileImage(profile.getImageFile().getBytes());
        card.setAboutMe(profile.getAboutMe());
        card.setAppUser(admin);
        businessCardRepository.save(card);
        return "redirect:/admin";
    }
}

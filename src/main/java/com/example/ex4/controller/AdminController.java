package com.example.ex4.controller;

import com.example.ex4.constants.JobType;
import com.example.ex4.dto.BusinessCardFormDTO;
import com.example.ex4.entity.AppUser;
import com.example.ex4.entity.BusinessCard;
import com.example.ex4.service.BusinessCardService;
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
    private BusinessCardService businessCardService;

    @GetMapping
    public String adminIndex(Model model, Principal principal) {
        AppUser admin = adminService.findByUsername(principal.getName());
        BusinessCard adminBusinessCard = businessCardService.findAdminBusinessCard(admin);
        model.addAttribute("admin", admin);
        model.addAttribute("businessCard", adminBusinessCard);
        return "admin/index";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {
        byte[] image = businessCardService.findProfileImage(id);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/create-profile")
    public String adminProfile(Model model) {
        model.addAttribute("businessInfo", new BusinessCardFormDTO());
        model.addAttribute("jobTypes", JobType.values());
        return "admin/business-card-form";
    }

    @PostMapping("/create-profile")
    public String createProfile(@Valid @ModelAttribute("businessInfo") BusinessCardFormDTO profile,
                                BindingResult result, Principal principal, Model model) throws IOException {
        AppUser admin = adminService.findByUsername(principal.getName());
        if (result.hasErrors()) {
            model.addAttribute("jobTypes", JobType.values());
            return "admin/business-card-form";
        }
        businessCardService.saveAdminProfile(admin, profile);
        return "redirect:/admin";
    }
}

package com.example.ex4.controller;

import com.example.ex4.constants.ProgrammingLanguage;
import com.example.ex4.dto.AdminFormDTO;
import com.example.ex4.service.AdminService;
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
    private AdminService adminService;

    @GetMapping
    public String adminIndex(Model model, Principal principal) {
        model.addAttribute("admin", adminService.findAdmin(principal.getName()));
        return "admin/index";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable Long id) {
        byte[] image = adminService.findProfileImage(id);
        if (image == null || image.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @GetMapping("/create-profile")
    public String adminProfile(Model model) {
        model.addAttribute("admin", new AdminFormDTO());
        model.addAttribute("lang", ProgrammingLanguage.values());
        return "admin/adminProfileForm";
    }

    @PostMapping("/create-profile")
    public String createProfile(@Valid @ModelAttribute("admin") AdminFormDTO profile,
                                BindingResult result,
                                Model model,
                                Principal principal) throws IOException
    {
        if (result.hasErrors()) {
            model.addAttribute("lang", ProgrammingLanguage.values());
            return "admin/adminProfileForm";
        }
        adminService.saveAdminProfile(principal.getName(), profile);
        return "redirect:/admin";
    }

}

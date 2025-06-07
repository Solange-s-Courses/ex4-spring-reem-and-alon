package com.example.ex4.controller;

import com.example.ex4.constants.ProgrammingLanguage;
import com.example.ex4.entity.Admin;
import com.example.ex4.entity.AppUser;
import com.example.ex4.repository.AdminRepository;
import com.example.ex4.repository.UserRepository;
import com.example.ex4.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String adminIndex(Model model, Principal principal) {
        model.addAttribute("admin", userService.findAdmin(principal.getName()));
        return "admin/index";
    }

    @GetMapping("/admin/create-profile")
    public String adminProfile(Model model, Principal principal) {
        Admin admin = userService.findAdmin(principal.getName());
        model.addAttribute("admin", admin);
        model.addAttribute("lang", ProgrammingLanguage.values());
        return "admin/adminProfileForm";
    }

    @PostMapping("/admin/create-profile")
    public String createProfile(@Valid @ModelAttribute("admin") Admin admin, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("lang", ProgrammingLanguage.values());
            return "admin/adminProfileForm";
        }
        userService.saveAdminProfile(principal.getName(), admin);
        return "redirect:/admin";
    }


    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}

package com.example.ex4.controller;

import com.example.ex4.constants.ProgrammingLanguage;
import com.example.ex4.entity.Admin;
import com.example.ex4.entity.AppUser;
import com.example.ex4.repository.AdminRepository;
import com.example.ex4.repository.UserRepository;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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


    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}

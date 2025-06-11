package com.example.ex4.controller;

import com.example.ex4.entity.PlanPackage;
import com.example.ex4.service.PlanPackageService;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PlanPackageService packageService;

    @GetMapping("/user")
    public String userIndex(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return "user/index";
    }
    @PostMapping("/user/search-service")
    public String searchService(@RequestParam String providerCategory, @RequestParam String sortByPrice,
            Model model) {
        List<PlanPackage> results = packageService.getAllPackagesByCategory(providerCategory, sortByPrice);
        model.addAttribute("results", results);
        return "user/index";
    }

}

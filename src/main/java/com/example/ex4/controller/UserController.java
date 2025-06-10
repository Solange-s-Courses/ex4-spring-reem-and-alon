package com.example.ex4.controller;

import com.example.ex4.entity.BusinessCard;
import com.example.ex4.service.BusinessCardService;
import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BusinessCardService businessCardService;

    @GetMapping("/user")
    public String userIndex(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return "user/index";
    }
    @PostMapping("/user/search-service")
    public String searchService(
            @RequestParam String sortByPrice,
            @RequestParam String city,
            @RequestParam String serviceType,
            Model model)
    {
        List<BusinessCard> results =businessCardService.getAllBusinessResults(serviceType, city,sortByPrice);
        model.addAttribute("results", results);
        //return html thymleaf;
    }
    @ExceptionHandler({IllegalArgumentException.class, IOException.class})
    public String handleException(Model model, Exception ex) {
        model.addAttribute("error", ex.getMessage());
        return "error-page";
    }

}

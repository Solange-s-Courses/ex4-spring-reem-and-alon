package com.example.ex4.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

   /* @GetMapping("/admin")
    public String adminIndex() {
        return "admin/index";
    }*/

    @GetMapping("/provider_profile")
    public String adminIndex() {
        return "shared/provider_profile";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}

package com.example.ex4.controller;

import com.example.ex4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @ExceptionHandler({IllegalArgumentException.class, IOException.class})
    public String handleException(Model model, Exception ex) {
        model.addAttribute("error", ex.getMessage());
        return "error-page";
    }
}

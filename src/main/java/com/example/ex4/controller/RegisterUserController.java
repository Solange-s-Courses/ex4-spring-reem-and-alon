package com.example.ex4.controller;

import com.example.ex4.entity.User;
import com.example.ex4.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/register/user")
@Controller
public class RegisterUserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String registerUserForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping
    public ModelAndView processRegister(@Valid @ModelAttribute User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return new ModelAndView("register");
        }
        try {
            userService.addNewUser(user);
            model.addAttribute("message", "Registration successful. Please login.");
            return new ModelAndView("redirect:/login", model);
        }
        catch (DataIntegrityViolationException ex) {
            result.rejectValue("userName",null, "username in use. please enter new username");
            return new ModelAndView("register");
        }
    }
}

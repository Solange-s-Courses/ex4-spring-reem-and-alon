package com.example.ex4.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception ex) {
        System.out.println(ex.getMessage());
        model.addAttribute("error", ex.getMessage());
        return "error-page";
    }
}


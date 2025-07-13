package com.example.ex4.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * <p>
 * Catches any unhandled exceptions from controllers and displays a generic error page with the error message.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all exceptions thrown by controllers in the application.
     * <p>
     * Adds the exception's message to the model and returns the error page view.
     *
     * @param model the model for passing attributes to the view
     * @param ex the exception that was thrown
     * @return the logical view name for the error page
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception ex) {
        model.addAttribute("error", ex.getMessage());
        return "error-page";
    }
}



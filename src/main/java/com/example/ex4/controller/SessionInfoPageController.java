package com.example.ex4.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionInfoPageController {

    @GetMapping("/session-info")
    public String showSessionInfo(HttpSession session, Model model) {
        long creationTime = session.getCreationTime();
        long lastAccessedTime = session.getLastAccessedTime();
        int maxInactiveInterval = session.getMaxInactiveInterval(); // בשניות

        long now = System.currentTimeMillis();
        long idleSeconds = (now - lastAccessedTime) / 1000;
        long secondsLeft = maxInactiveInterval - idleSeconds;

        model.addAttribute("sessionId", session.getId());
        model.addAttribute("creationTime", creationTime);
        model.addAttribute("lastAccessedTime", lastAccessedTime);
        model.addAttribute("maxInactiveInterval", maxInactiveInterval);
        model.addAttribute("idleSeconds", idleSeconds);
        model.addAttribute("secondsLeft", secondsLeft);

        return "user/session-info"; // שם ה־HTML/Thymeleaf template
    }
}
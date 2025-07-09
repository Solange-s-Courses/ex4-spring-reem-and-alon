package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.Map;

@ControllerAdvice
public class SharedControllerAdvice {

    @Autowired
    private MessageService messageService;

    @ModelAttribute("userName")
    public String userName(Principal principal) {
        return principal != null ? principal.getName() : "";
    }

    @ModelAttribute("unreadMessages")
    public Map<Long, Long> unreadMessages(@AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        if (userPrincipal == null) {
            return Map.of();
        }
        return messageService.getUnreadMessagesCount(userPrincipal.getUser());
    }
}

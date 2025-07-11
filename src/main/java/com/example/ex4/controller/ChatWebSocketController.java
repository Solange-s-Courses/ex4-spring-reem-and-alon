package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.dto.ChatMessageDTO;
import com.example.ex4.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class ChatWebSocketController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessageDTO sendMessage(@Valid ChatMessageDTO messageDTO,
                                      @AuthenticationPrincipal MyUserPrincipal myUserPrincipal) {
        if (myUserPrincipal == null)
            throw new AccessDeniedException("SESSION_EXPIRED");
        return messageService.saveMessage(messageDTO);
    }

    @MessageExceptionHandler({AccessDeniedException.class, MethodArgumentNotValidException.class})
    @SendToUser("/queue/errors")
    public String handleValidationException(Exception ex) {
        System.out.println(ex.getMessage() + " " + " hiiiiiiiiiiiiii");
        return ex.getMessage();
    }
}

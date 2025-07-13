package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.entity.User;
import com.example.ex4.service.MessageService;
import com.example.ex4.service.ProviderProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST controller for chat-related API operations.
 */
@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    /**
     * Service for handling chat message operations.
     */
    @Autowired
    private MessageService messageService;

    /**
     * Service for handling provider profile operations.
     */
    @Autowired
    private ProviderProfileService providerProfileService;

    /**
     * Marks a specific message as read by the current user or provider.
     *
     * @param messageId the ID of the message to mark as read
     * @param userPrincipal the currently authenticated user principal
     * @return HTTP 200 OK if successful, 401 UNAUTHORIZED if the user is not authenticated
     */
    @PutMapping("/{messageId}/read")
    public ResponseEntity<?> markMessageAsRead(
            @PathVariable Long messageId,
            @AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        if (userPrincipal == null || userPrincipal.getUser() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = userPrincipal.getUser();
        long id = user.getRole().equals("USER") ? user.getId()
                : providerProfileService.findProviderProfile(user).getId();
        messageService.markMessageAsRead(messageId, id);
        return ResponseEntity.ok().build();
    }

    /**
     * Handles ResponseStatusException and returns an appropriate HTTP status and error message.
     *
     * @param ex the ResponseStatusException thrown in controller methods
     * @return ResponseEntity with error status and message
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }
}

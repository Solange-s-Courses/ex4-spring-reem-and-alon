package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.dto.ChatMessageDTO;
import com.example.ex4.service.MessageService;
import jakarta.validation.Valid;
import com.example.ex4.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Controller for handling WebSocket chat messaging.
 * <p>
 * Exposes endpoints for sending chat messages and handling validation/authentication exceptions.
 * Messages sent to "/app/chat" will be processed here.
 */
@Controller
public class ChatWebSocketController {

    /**
     * Service for business logic of {@link Message}.
     */
    @Autowired
    private MessageService messageService;

    /**
     * Handles incoming chat messages sent via WebSocket.
     * <p>
     * Expects destination: <b>/app/chat</b>.<br>
     * On success, broadcasts the saved message to all subscribers of <b>/topic/messages</b>.
     *
     * @param messageDTO       The chat message sent from the client.
     * @param myUserPrincipal  The authenticated user (injected by Spring Security).
     * @return The saved ChatMessageDTO to be broadcast.
     * @throws AccessDeniedException If the user session is not authenticated (null).
     */
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessageDTO sendMessage(@Valid ChatMessageDTO messageDTO,
                                      @AuthenticationPrincipal MyUserPrincipal myUserPrincipal) {
        if (myUserPrincipal == null)
            throw new AccessDeniedException("SESSION_EXPIRED");
        return messageService.saveMessage(messageDTO);
    }

    /**
     * Handles validation and authentication exceptions during WebSocket messaging.
     * <p>
     * On error, sends the error message privately to the user at <b>/user/queue/errors</b>.
     *
     * @param ex The exception thrown (e.g. validation failed, session expired).
     * @return The error message.
     */
    @MessageExceptionHandler({AccessDeniedException.class, MethodArgumentNotValidException.class})
    @SendToUser("/queue/errors")
    public String handleValidationException(Exception ex) {
        return ex.getMessage();
    }
}

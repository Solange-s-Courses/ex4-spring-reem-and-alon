package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.dto.ChatMessageDTO;
import com.example.ex4.dto.ConversationDTO;
import com.example.ex4.service.ConversationService;
import com.example.ex4.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {

    @Autowired
    private final ConversationService conversationService;
    @Autowired
    private final MessageService messageService;

    @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@AuthenticationPrincipal MyUserPrincipal user,
                                            @RequestBody ChatMessageDTO messageDTO) {



        ChatMessageDTO preparedMessage = messageService.sendMessage(messageDTO);

        // send the message to al the listeners on this conversation
        messagingTemplate.convertAndSend("/topic/conversation/" + messageDTO.getConversationId(), preparedMessage);

        return ResponseEntity.ok().build();
    }
}

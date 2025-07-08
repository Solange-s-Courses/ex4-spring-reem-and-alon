package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.dto.ChatMessageDTO;
import com.example.ex4.service.ChatService;
import com.example.ex4.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatRestController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send")
    public ResponseEntity<Void> sendMessage(@AuthenticationPrincipal MyUserPrincipal user,
                                            @RequestBody ChatMessageDTO messageDTO) {



        ChatMessageDTO preparedMessage = messageService.sendMessage(messageDTO, user.getUser().getId());

        // send the message to al the listeners on this conversation
        messagingTemplate.convertAndSend("/topic/conversation/" + messageDTO.getChatId(), preparedMessage);

        return ResponseEntity.ok().build();
    }
}

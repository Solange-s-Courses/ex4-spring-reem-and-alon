package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.dto.ChatMessageDTO;
import com.example.ex4.dto.ConversationDTO;
import com.example.ex4.repository.ProviderProfileRepository;
import com.example.ex4.service.ConversationService;
import com.example.ex4.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ConversationService conversationService;
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    @GetMapping()
    public String list(@AuthenticationPrincipal MyUserPrincipal user, Model model) {
        boolean admin = user.getUser().getRole().equals("ADMIN");
        long id = user.getUser().getId();
        if (admin) {
            id= providerProfileRepository.findByUser(user.getUser()).getId();
        }
        model.addAttribute("conversations",
                conversationService.getConversationSnippets(id, admin));
        return "shared/conversations-page";
    }


    @GetMapping("{conversationId}")
    public String showConversation(@PathVariable Long conversationId,@AuthenticationPrincipal MyUserPrincipal user, Model model) {
        List<ChatMessageDTO> messages = messageService.getAllMesaggeHistory(conversationId);
        model.addAttribute("messages", messages);
        model.addAttribute("userId", user.getUser().getId());
        model.addAttribute("conversationId", conversationId);
        return "shared/conversation-chat-page";
    }
}

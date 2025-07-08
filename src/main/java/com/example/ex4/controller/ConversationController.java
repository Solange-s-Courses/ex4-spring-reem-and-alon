package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.dto.ChatMessageDTO;
import com.example.ex4.entity.Chat;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.User;
import com.example.ex4.repository.ProviderProfileRepository;
import com.example.ex4.service.ChatService;
import com.example.ex4.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
@RequestMapping("/chats")
public class ConversationController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatService chatService;
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    @GetMapping()
    public String list(@AuthenticationPrincipal MyUserPrincipal userPrincipal, Model model) {
        User user = userPrincipal.getUser();
        ProviderProfile providerProfile = providerProfileRepository.findByUser(user).orElse(null);
        List<Chat> chats = providerProfile != null ? chatService.getAllChatsForProvider(providerProfile) : chatService.getAllChatsForClient(user);
        if (!chats.isEmpty()) {
            model.addAttribute("unreadCount",messageService.getUnreadMessagesCount(chats));
        }
        model.addAttribute("chats", chats);
        model.addAttribute("userId", user.getId());
        return "shared/chat";
    }


    @GetMapping("/{chatId}")
    public ModelAndView showConversation(@PathVariable Long chatId, ModelMap model) {
        List<ChatMessageDTO> messages = messageService.getAllMessageHistory(chatId);
        model.addAttribute("messages", messages);
        model.addAttribute("chatId", chatId);
        return new ModelAndView("forward:/chats", model);
    }
}

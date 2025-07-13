package com.example.ex4.controller;

import com.example.ex4.MyUserPrincipal;
import com.example.ex4.components.CheckoutProviders;
import com.example.ex4.dto.ChatMessageDTO;
import com.example.ex4.entity.Chat;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.User;
import com.example.ex4.repository.ProviderProfileRepository;
import com.example.ex4.service.ChatService;
import com.example.ex4.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * ChatController
 * Handles chat-related endpoints for users and providers.
 * Enables chat creation after checkout, listing chats, displaying conversations, and marking messages as read.
 */
@Controller
@RequestMapping("/chat")
public class ChatController {

    /**
     * Service for business logic of {@link MessageService}.
     */
    @Autowired
    private MessageService messageService;

    /**
     * Service for business logic of {@link ChatService}.
     */
    @Autowired
    private ChatService chatService;

    /**
     * Service for business logic of {@link ProviderProfile}.
     */
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    @Autowired CheckoutProviders planOwnerProviders;

    /**
     * Creates chat(s) after a successful checkout and redirects to the checkout page with a success parameter.
     * uses the request scope bean to know which providers need to have chat with the user
     *
     * @param userPrincipal The currently authenticated user
     * @return Redirect URL to the checkout page with success indication
     */
    @PostMapping("/create")
    public String createChatAfterCheckout(@AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        System.out.println("from create: " + planOwnerProviders.getProviders());
        List<ProviderProfile> providers = planOwnerProviders.getProviders();
        chatService.createNewChats(userPrincipal.getUser(), providers);
        return "redirect:/user/checkout?success=true";
    }

    /**
     * Displays the chat list for the current user or provider.
     *
     * @param userPrincipal The currently authenticated user
     * @param model Spring Model for passing data to the view
     * @return The chat list view name
     */
    @GetMapping()
    public String list(@AuthenticationPrincipal MyUserPrincipal userPrincipal, Model model) {
        User user = userPrincipal.getUser();
        ProviderProfile providerProfile = providerProfileRepository.findByUser(user).orElse(null);
        List<Chat> chats = providerProfile != null ?
                chatService.getAllChatsForProvider(providerProfile) :
                chatService.getAllChatsForClient(user);
        model.addAttribute("chats", chats);
        model.addAttribute("userId", user.getId());
        model.addAttribute("unreadMessages", messageService.getUnreadMessagesCount(user));
        return "shared/chat";
    }

    /**
     * Shows a specific conversation and its messages.
     * Marks the chat as read for the current user.
     *
     * @param chatId The ID of the chat to display
     * @param userPrincipal The currently authenticated user
     * @param model Spring Model for passing data to the view
     * @return The chat conversation view name
     */
    @GetMapping("/{chatId}")
    public String showConversation(@PathVariable Long chatId,
                                   @AuthenticationPrincipal MyUserPrincipal userPrincipal,
                                   Model model) {
        User user = userPrincipal.getUser();
        ProviderProfile providerProfile = providerProfileRepository.findByUser(user).orElse(null);
        messageService.markAsRead(chatId, user.getId());

        List<Chat> chats = providerProfile != null ?
                chatService.getAllChatsForProvider(providerProfile) :
                chatService.getAllChatsForClient(user);

        List<ChatMessageDTO> messages = messageService.getAllMessageHistory(chatId);
        model.addAttribute("messages", messages);
        model.addAttribute("chats", chats);
        model.addAttribute("chatId", chatId);
        model.addAttribute("userId", user.getId());
        model.addAttribute("unreadMessages", messageService.getUnreadMessagesCount(user));
        return "shared/chat";
    }

    /**
     * Marks all messages in a specific chat as read by the current user.
     *
     * @param chatId The ID of the chat to mark as read
     * @param userPrincipal The currently authenticated user
     * @return HTTP 200 response if successful
     */
    @PostMapping("/{chatId}/read")
    public ResponseEntity<?> markChatAsRead(@PathVariable Long chatId,
                                            @AuthenticationPrincipal MyUserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        messageService.markAsRead(chatId, user.getId());
        return ResponseEntity.ok().build();
    }
}

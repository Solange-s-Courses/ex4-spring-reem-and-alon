package com.example.ex4.service;

import com.example.ex4.components.CheckoutProviders;
import com.example.ex4.entity.Chat;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.User;
import com.example.ex4.repository.ChatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    private Chat createChat(User client, ProviderProfile provider){
        Chat chat = Chat.builder()
                .client(client).provider(provider)
                .createdAt(LocalDateTime.now())
                .unreadCount(0).build();
        return chatRepository.save(chat);
    }

    public List<Chat> getAllChatsForProvider(ProviderProfile providerProfile) {
        return chatRepository.findByProvider(providerProfile);
    }

    public List<Chat> getAllChatsForClient(User user) {
        return chatRepository.findByClient(user);
    }

    @Transactional
    public void createNewChats(User user, List<ProviderProfile> checkoutProviders) {
        checkoutProviders.forEach(provider -> chatRepository.findByClientAndProvider(user, provider)
                        .orElseGet(() -> createChat(user, provider))
        );
    }
}


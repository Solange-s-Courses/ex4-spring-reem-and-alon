package com.example.ex4.service;

import com.example.ex4.entity.Chat;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.User;
import com.example.ex4.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    public void getOrCreate(User client, ProviderProfile provider){
        chatRepository.findByClientAndProvider(client, provider)
                .orElseGet(() -> {
                    Chat chat = Chat.builder().client(client).provider(provider).createdAt(LocalDateTime.now()).unreadCount(0).build();
                    chatRepository.save(chat);
                    return chat;
                });
    }

    public List<Chat> getAllChatsForProvider(ProviderProfile providerProfile) {
        return chatRepository.findByProvider(providerProfile);
    }

    public List<Chat> getAllChatsForClient(User user) {
        return chatRepository.findByClient(user);
    }
}


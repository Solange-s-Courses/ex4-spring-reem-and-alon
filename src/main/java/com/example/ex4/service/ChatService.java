package com.example.ex4.service;

import com.example.ex4.entity.Chat;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.User;
import com.example.ex4.repository.ChatRepository;
import com.example.ex4.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;


    public void getOrCreate(User client, ProviderProfile provider){
        chatRepository.findByClientAndProvider(client, provider)
                .orElseGet(() -> chatRepository.save(new Chat(null, client, provider, LocalDateTime.now(), 0)));
    }

    public List<Chat> getAllChatsForProvider(ProviderProfile providerProfile) {
        return chatRepository.findByProvider(providerProfile);
    }

    public List<Chat> getAllChatsForClient(User user) {
        return chatRepository.findByClient(user);
    }
}


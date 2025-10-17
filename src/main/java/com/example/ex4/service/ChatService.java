package com.example.ex4.service;

import com.example.ex4.entity.Chat;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.User;
import com.example.ex4.repository.ChatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for chat management between users and providers.
 *
 * @see ChatRepository
 * @see Chat
 */
@Service
public class ChatService {

    /**
     * Repository for chat entities.
     */
    @Autowired
    private ChatRepository chatRepository;

    /**
     * Creates a new chat between a client and provider, if not already exists.
     *
     * @param client the client user
     * @param provider the provider profile
     * @return the created and saved chat entity
     */
    private Chat createChat(User client, ProviderProfile provider) {
        Chat chat = Chat.builder()
                .client(client).provider(provider)
                .createdAt(LocalDateTime.now())
                .unreadCount(0).build();
        return chatRepository.save(chat);
    }

    /**
     * Returns all chats for a given provider.
     *
     * @param providerProfile the provider profile
     * @return list of chats involving the provider
     */
    public List<Chat> getAllChatsForProvider(ProviderProfile providerProfile) {
        return chatRepository.findByProvider(providerProfile);
    }

    /**
     * Returns all chats for a given client/user.
     *
     * @param user the user (client)
     * @return list of chats involving the user as client
     */
    public List<Chat> getAllChatsForClient(User user) {
        return chatRepository.findByClient(user);
    }

    /**
     * For each given provider, creates a chat with the user if not already exists.
     *
     * @param user the client user
     * @param checkoutProviders the list of provider profiles for the checkout
     */
    @Transactional
    public void createNewChats(User user, List<ProviderProfile> checkoutProviders) {
        checkoutProviders.forEach(provider -> chatRepository.findByClientAndProvider(user, provider)
                .orElseGet(() -> createChat(user, provider))
        );
    }

    public Chat getChatByProviderId(User user, Long providerId) {
        return chatRepository.findByClientAndProvider_Id(user, providerId);
    }

    public Chat getChatByPId(Long chatId) {
        return chatRepository.findById(chatId).orElse(null);
    }
}

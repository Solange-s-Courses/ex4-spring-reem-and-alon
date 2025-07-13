package com.example.ex4.repository;

import com.example.ex4.entity.Chat;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing {@link Chat} entities.
 * Provides query methods for chats by user and provider.
 */
public interface ChatRepository extends JpaRepository<Chat, Long> {
    /**
     * Finds all chats for a given client (user).
     *
     * @param user the user acting as client
     * @return list of chats involving the user as client
     */
    List<Chat> findByClient(User user);

    /**
     * Finds all chats for a given provider.
     *
     * @param provider the provider profile
     * @return list of chats involving the provider
     */
    List<Chat> findByProvider(ProviderProfile provider);

    /**
     * Finds a chat between a specific client and provider.
     *
     * @param client the client user
     * @param provider the provider profile
     * @return optional chat if exists between client and provider
     */
    Optional<Chat> findByClientAndProvider(User client, ProviderProfile provider);

    /**
     * Finds all chats for a given client.
     * (Alias for {@link #findByClient(User)})
     *
     * @param client the client user
     * @return list of chats for the client
     */
    List<Chat> findAllByClient(User client);

}

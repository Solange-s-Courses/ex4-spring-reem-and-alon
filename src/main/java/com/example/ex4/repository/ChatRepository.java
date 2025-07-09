package com.example.ex4.repository;

import com.example.ex4.entity.Chat;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByClient(User user);
    List<Chat> findByProvider(ProviderProfile provider);

    Optional<Chat> findByClientAndProvider(User client, ProviderProfile provider);

    List<Chat> findAllByClient(User client);
}

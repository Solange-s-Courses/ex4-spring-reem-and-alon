package com.example.ex4.repository;

import com.example.ex4.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByBuyer_Id(Long buyerId);
    List<Conversation> findByProvider_Id(Long providerId);
    Optional<Conversation> findByBuyer_IdAndProvider_Id(Long buyerId, Long providerId);
}

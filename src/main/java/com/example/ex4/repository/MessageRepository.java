package com.example.ex4.repository;

import com.example.ex4.entity.Message;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByConversation_IdOrderBySentAtAsc(Long conversationId);
}

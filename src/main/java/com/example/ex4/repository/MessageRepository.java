package com.example.ex4.repository;

import com.example.ex4.entity.Chat;
import com.example.ex4.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByChat_IdOrderBySentAtAsc(Long conversationId);

    Long countByChatAndSenderIDNotAndIsReadFalse(Chat chat, long id);

    List<Message> findByChat_IdAndSenderIDNotAndIsReadFalse(Long chatId, long id);
}

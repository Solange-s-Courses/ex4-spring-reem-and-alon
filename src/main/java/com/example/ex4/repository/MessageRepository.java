package com.example.ex4.repository;

import com.example.ex4.entity.Chat;
import com.example.ex4.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Message} entities and performing
 * custom queries related to chat messages.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Finds all messages for a given chat, ordered by sending time ascending.
     *
     * @param conversationId the chat (conversation) ID
     * @return list of messages in the chat, sorted by {@code sentAt} ascending
     */
    List<Message> findByChat_IdOrderBySentAtAsc(Long conversationId);

    /**
     * Counts unread messages in a chat, excluding those sent by a specific user.
     *
     * @param chat the chat entity
     * @param id the sender ID to exclude (messages not sent by this user)
     * @return the number of unread messages not sent by the given user
     */
    Long countByChatAndSenderIDNotAndIsReadFalse(Chat chat, long id);

    /**
     * Finds unread messages in a chat, not sent by a specific user.
     *
     * @param chatId the chat ID
     * @param id the sender ID to exclude
     * @return list of unread messages in the chat not sent by the given user
     */
    List<Message> findByChat_IdAndSenderIDNotAndIsReadFalse(Long chatId, long id);

    /**
     * Finds a message by its ID, only if it was not sent by the specified user.
     *
     * @param messageId the message ID
     * @param id the sender ID to exclude
     * @return an optional containing the message, if found and not sent by the given user
     */
    Optional<Message> findByIdAndSenderIDNot(Long messageId, long id);
}

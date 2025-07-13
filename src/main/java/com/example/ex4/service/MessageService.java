package com.example.ex4.service;

import com.example.ex4.dto.ChatMessageDTO;
import com.example.ex4.entity.Chat;
import com.example.ex4.entity.Message;
import com.example.ex4.entity.User;
import com.example.ex4.repository.ChatRepository;
import com.example.ex4.repository.MessageRepository;
import com.example.ex4.repository.ProviderProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service class for handling chat messages: sending, reading, and counting unread messages.
 *
 * @see MessageRepository
 * @see ChatRepository
 * @see ChatMessageDTO
 */
@Service
public class MessageService {

    /**
     * Repository for message entities.
     */
    @Autowired
    private MessageRepository messageRepository;

    /**
     * Repository for chat entities.
     */
    @Autowired
    private ChatRepository chatRepository;

    /**
     * Repository for provider profiles.
     */
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    /**
     * Saves a new message to a chat and returns the message as a DTO.
     *
     * @param messageDTO message details to save
     * @return the saved message as a DTO
     * @throws RuntimeException if chat does not exist
     */
    @Transactional
    public ChatMessageDTO saveMessage(ChatMessageDTO messageDTO){
        Chat chat = chatRepository.findById(messageDTO.getChatId())
                .orElseThrow(() -> new RuntimeException("chat not found"));

        chat.setUnreadCount(chat.getUnreadCount() + 1);

        Message message = Message.builder().content(messageDTO.getContent())
                .sentAt(LocalDateTime.now())
                .isRead(false)
                .senderID(messageDTO.getSenderId())
                .chat(chat)
                .build();

        messageDTO.setMessageId(message.getId());

        messageRepository.save(message);
        return toDto(message);
    }

    /**
     * Returns all messages in a chat, ordered by sent time ascending.
     *
     * @param chatId the chat (conversation) ID
     * @return list of all chat messages as DTOs
     */
    @Transactional
    public List<ChatMessageDTO> getAllMessageHistory(Long chatId) {
        List<Message> messages = messageRepository.findByChat_IdOrderBySentAtAsc(chatId);
        return messages.stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * Converts a Message entity to a ChatMessageDTO.
     *
     * @param message the message entity
     * @return the DTO representing the message
     */
    private ChatMessageDTO toDto(Message message) {
        return ChatMessageDTO.builder()
                .sentAt(message.getSentAt())
                .content(message.getContent())
                .senderId(message.getSenderID())
                .chatId(message.getChat().getId())
                .messageId(message.getId())
                .isRead(message.isRead())
                .build();
    }

    /**
     * Returns a map of chat IDs to unread message counts for a user.
     *
     * @param user the user (client or provider)
     * @return map: chat ID → number of unread messages for that chat
     */
    public Map<Long, Long> getUnreadMessagesCount(User user) {
        List<Chat> chats = Objects.equals(user.getRole(), "USER") ? chatRepository.findAllByClient(user):
                chatRepository.findByProvider(providerProfileRepository.findByUser(user).orElse(null));

        return chats.stream().collect(Collectors.toMap(Chat::getId, chat -> messageRepository.countByChatAndSenderIDNotAndIsReadFalse(chat, user.getId())));
    }

    /**
     * Marks all messages in a chat as read for a given user (except messages the user sent).
     *
     * @param chatId the chat ID
     * @param id the current user's ID
     */
    @Transactional
    public void markAsRead(Long chatId, long id) {
        List<Message> messages = messageRepository.findByChat_IdAndSenderIDNotAndIsReadFalse(chatId, id);
        System.out.println("messages found to mark as read: " + messages.size());
        messages.forEach(message -> {
            System.out.println("Marking message " + message.getId() + " as read");
            message.setRead(true);
            messageRepository.save(message);
        });
    }

    /**
     * Marks a specific message as read for a user (if the user is a participant).
     *
     * @param messageId the message ID
     * @param id the user ID marking the message as read
     * @throws ResponseStatusException if not allowed or not found
     */
    public void markMessageAsRead(Long messageId, long id) {
        Message message = messageRepository.findByIdAndSenderIDNot(messageId, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "message not found"));

        if (message.getChat().getProvider().getId() != id && message.getChat().getClient().getId() != id)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "read message not allowed");

        message.setRead(true);
        messageRepository.save(message);
    }
}

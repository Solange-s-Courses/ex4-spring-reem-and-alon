package com.example.ex4.service;

import com.example.ex4.dto.ChatMessageDTO;
import com.example.ex4.entity.Chat;
import com.example.ex4.entity.Message;
import com.example.ex4.entity.User;
import com.example.ex4.repository.ChatRepository;
import com.example.ex4.repository.MessageRepository;
import com.example.ex4.repository.ProviderProfileRepository;
import com.example.ex4.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ProviderProfileRepository providerProfileRepository;

    public void saveMessage(ChatMessageDTO messageDTO){
        Chat chat = chatRepository.findById(messageDTO.getChatId())
                .orElseThrow(() -> new RuntimeException("chat not found"));

        chat.setUnreadCount(chat.getUnreadCount() + 1);

        Message message = Message.builder()
                .content(messageDTO.getContent())
                .sentAt(messageDTO.getSentAt())
                .isRead(messageDTO.getIsRead())
                .senderID(messageDTO.getSenderId())
                .chat(chat)
                .build();

        messageRepository.save(message);
    }

    @Transactional
    public List<ChatMessageDTO> getAllMessageHistory(Long chatId) {
        List<Message> messages = messageRepository.findByChat_IdOrderBySentAtAsc(chatId);
        return messages.stream().map(this::toDto).collect(Collectors.toList());
    }

    private ChatMessageDTO toDto(Message message) {
        return ChatMessageDTO.builder()
                .sentAt(message.getSentAt())
                .content(message.getContent())
                .senderId(message.getSenderID())
                .chatId(message.getChat().getId())
                .build();
    }

    public Map<Long, Long> getUnreadMessagesCount(User user) {
        List<Chat> chats = Objects.equals(user.getRole(), "USER") ? chatRepository.findAllByClient(user):
                chatRepository.findByProvider(providerProfileRepository.findByUser(user).orElse(null));

        return chats.stream().collect(Collectors.toMap(Chat::getId, chat -> messageRepository.countByChatAndSenderIDNotAndIsReadFalse(chat, user.getId())));
    }

    @Transactional
    public void markAsRead(Long chatId, long id) {
        List<Message> messages = messageRepository.findByChat_Id(chatId);

        messages.forEach(message -> {
            if (!message.isRead() && !message.getSenderID().equals(id)) {
                message.setRead(true);
                messageRepository.save(message);
            }
        });
    }
}

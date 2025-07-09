package com.example.ex4.service;

import com.example.ex4.dto.ChatMessageDTO;
import com.example.ex4.entity.Chat;
import com.example.ex4.entity.Message;
import com.example.ex4.repository.ChatRepository;
import com.example.ex4.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatRepository conversationRepository;

    public void saveMessage(ChatMessageDTO messageDTO){
        Chat chat = conversationRepository.findById(messageDTO.getChatId())
                .orElseThrow(() -> new RuntimeException("chat not found"));

        Message message = Message.builder()
                .content(messageDTO.getContent())
                .sentAt(messageDTO.getSentAt())
                .isRead(messageDTO.getIsRead())
                .senderID(messageDTO.getSenderId())
                .chat(chat)
                .build();

        messageRepository.save(message);
    }

    public List<ChatMessageDTO> getAllMessageHistory(Long conversationId){
        List<Message> messages = messageRepository.findByChat_IdOrderBySentAtAsc(conversationId);
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

    public Map<Long, Long> getUnreadMessagesCount(List<Chat> chats) {
        return chats.stream().collect(Collectors.
                toMap(Chat::getId, chat -> messageRepository.countByChatAndIsReadFalse(chat)
        ));
    }
}

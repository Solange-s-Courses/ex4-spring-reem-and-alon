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

    public ChatMessageDTO sendMessage(ChatMessageDTO messageDTO, Long currentUserId){
        Chat conversation = conversationRepository.findById(messageDTO.getChatId()).orElseThrow();
        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setSentAt(messageDTO.getSentAt());
        message.setSenderID(currentUserId);
        message.setChat(conversation);
        messageRepository.save(message);
        return toDto(message);
    }

    public List<ChatMessageDTO> getAllMessageHistory(Long conversationId){
        List<Message> messages = messageRepository.findByChat_IdOrderBySentAtAsc(conversationId);
        return messages.stream()
                .map(this::toDto)
                .toList();
    }

    private ChatMessageDTO toDto(Message message) {
        return new ChatMessageDTO(
                message.getChat().getId(),
                message.getContent(),
                message.getSentAt(),
                message.getSenderID()
        );
    }

    public void saveMessage(ChatMessageDTO messageDTO, Long senderId) {
        Chat conversation = conversationRepository.findById(messageDTO.getChatId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
    }

    public Map<Long, Long> getUnreadMessagesCount(List<Chat> chats) {
        return chats.stream().collect(Collectors.
                toMap(Chat::getId, chat -> messageRepository.countByChatAndIsReadFalse(chat)
        ));
    }
}

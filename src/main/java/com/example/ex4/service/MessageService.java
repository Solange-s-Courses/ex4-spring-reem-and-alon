package com.example.ex4.service;

import com.example.ex4.dto.ChatMessageDTO;
import com.example.ex4.entity.Conversation;
import com.example.ex4.entity.Message;
import com.example.ex4.repository.ConversationRepository;
import com.example.ex4.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ConversationRepository conversationRepository;

    @Transactional
    public ChatMessageDTO sendMessage(Long convoId,Long senderID,String text){
        Conversation conversation = conversationRepository.findById(convoId).orElseThrow();

        Message message = new Message(null,senderID,text,LocalDateTime.now(),conversation);
        messageRepository.save(message);
        System.out.println(toDto(message));
        return toDto(message);
    }

    public List<ChatMessageDTO> getAllMesaggeHistory(Long conversationId){
        List<Message> messages = messageRepository.findByConversation_IdOrderBySentAtAsc(conversationId);
        return messages.stream()
                .map(this::toDto)
                .toList();
    }

    private ChatMessageDTO toDto(Message message) {
        return new ChatMessageDTO(
                message.getConversation().getId(),
                message.getSenderID(),
                message.getContent(),
                message.getSentAt()
        );
    }

    public void saveMessage(ChatMessageDTO messageDTO, Long senderId) {
        Conversation conversation = conversationRepository.findById(messageDTO.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        Message message = new Message();
        message.setContent(messageDTO.getContent());
        message.setSentAt(messageDTO.getSentAt());
        message.setConversation(conversation);
        message.setSenderID(senderId);
        messageRepository.save(message);
    }
}

package com.example.ex4.service;

import com.example.ex4.dto.ConversationDTO;
import com.example.ex4.entity.Conversation;
import com.example.ex4.entity.ProviderProfile;
import com.example.ex4.entity.User;
import com.example.ex4.repository.ConversationRepository;
import com.example.ex4.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private MessageRepository messageRepository;


    public void getOrCreate(User buyer, ProviderProfile provider){
        conversationRepository.findByBuyer_IdAndProvider_Id(buyer.getId(), provider.getId())
                .orElseGet(() -> conversationRepository.save(
                        new Conversation(null, buyer, provider,
                                LocalDateTime.now())));
    }

    public List<ConversationDTO> getConversationSnippets(Long userId, boolean admin) {
        List<Conversation> conversations = admin
                ? conversationRepository.findByProvider_Id(userId)
                : conversationRepository.findByBuyer_Id(userId);

        return conversations.stream()
                .map(c -> {
                    String imgUrl = null;
                    if (!admin) { 
                        imgUrl = c.getProvider().getProfileImage() != null
                                ? "/provider-image/" + c.getProvider().getId()
                                : null;
                    }

                    return new ConversationDTO(
                            c.getId(),
                            admin ? c.getBuyer().getUserName() : c.getProvider().getCompanyName(),
                            imgUrl);
                })
                .toList();
    }
}

